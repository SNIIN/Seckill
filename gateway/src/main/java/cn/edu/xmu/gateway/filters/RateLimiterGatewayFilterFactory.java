package cn.edu.xmu.gateway.filters;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RateLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<RateLimiterGatewayFilterFactory.Config> implements Ordered {
    private RateLimiter rateLimiter;
    private BlockingQueue<RequestWrapper> queue;
    private ExecutorService executor;

    public RateLimiterGatewayFilterFactory() {
        super(Config.class);
        queue = new LinkedBlockingQueue<>(150000);
        processQueue();
    }

    @Override
    public GatewayFilter apply(Config config) {
        if (rateLimiter == null) {
            rateLimiter = RateLimiter.create(config.getPermitsPerSecond());
        }
        return (exchange, chain) -> {
            if (rateLimiter.tryAcquire()) {
                // 令牌充足直接被处理
                return chain.filter(exchange);
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.ACCEPTED);
                try {
                    if (!queue.offer(new RequestWrapper(chain, exchange), 3, TimeUnit.MILLISECONDS)) {
                        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                        return exchange.getResponse().setComplete();
                    }
                } catch (InterruptedException e) {
                    log.warn("插入错误");
                    exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                    return exchange.getResponse().setComplete();
                }
                return exchange.getResponse().setComplete();
            }
        };
    }

    @Override
    public int getOrder() {
        return -1;
    }

    class RequestWrapper {
        GatewayFilterChain chain;
        ServerWebExchange exchange;

        public RequestWrapper(GatewayFilterChain chain, ServerWebExchange exchange) {
            this.chain = chain;
            this.exchange = exchange;
        }
    }
    public void processQueue() {
        Flux.interval(Duration.ofSeconds(1))
                .flatMap(i -> {
                    List<RequestWrapper> requests = new ArrayList<>();
                    int qaq = queue.drainTo(requests, 1100);
                    log.info("after: {}, {}", queue.size(), qaq);
                    return Flux.fromIterable(requests);
                })
                .filter(request -> request != null)
                .flatMap(request -> {
                    ServerWebExchange exchange = request.exchange;
                    GatewayFilterChain chain = request.chain;
                    return Mono.fromCallable(() -> chain.filter(exchange))
                            .subscribeOn(Schedulers.boundedElastic())
                            .then(Mono.just(request));
                })
                .onErrorResume(e -> Mono.empty())
                .subscribe();
    }

    public static class Config {
        private double permitsPerSecond;

        public double getPermitsPerSecond() {
            return permitsPerSecond;
        }

        public void setPermitsPerSecond(double permitsPerSecond) {
            this.permitsPerSecond = permitsPerSecond;
        }
    }
}