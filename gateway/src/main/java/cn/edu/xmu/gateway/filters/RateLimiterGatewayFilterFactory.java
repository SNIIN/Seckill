package cn.edu.xmu.gateway.filters;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Component
@Slf4j
public class RateLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<RateLimiterGatewayFilterFactory.Config> {
    private RateLimiter rateLimiter;
    private BlockingQueue<Runnable> queue;
    public RateLimiterGatewayFilterFactory() {
        super(Config.class);
        queue = new LinkedBlockingDeque<>(30000);
    }

    @Override
    public GatewayFilter apply(Config config) {
        if (rateLimiter == null) {
            rateLimiter = RateLimiter.create(config.getPermitsPerSecond());
        }
        return (exchange, chain) -> {
            if (rateLimiter.tryAcquire()) {
                // 充足的令牌
                return chain.filter(exchange);
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.ACCEPTED);
                return exchange.getResponse().setComplete();
            }
        };
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
