package cn.edu.xmu.gateway.filters;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Component
@Slf4j
public class RateLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<RateLimiterGatewayFilterFactory.Config> {
    private RateLimiter rateLimiter;
    private BlockingQueue<Runnable> queue;
    public RateLimiterGatewayFilterFactory() {
        super(Config.class);
        queue = new LinkedBlockingDeque<>(10000);
         new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                processQueue();
            }}, 0, 1000); // 每隔1秒钟执行一次任务
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
                try {
                    queue.put(() -> chain.filter(exchange));
                    exchange.getResponse().setStatusCode(HttpStatus.ACCEPTED);
                    return exchange.getResponse().setComplete();
                } catch (InterruptedException e) {
                    log.error("Error adding request to queue", e);
                    exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                    return exchange.getResponse().setComplete();
                }
            }
        };
    }
    private void processQueue() {
        // 每秒处理100个请求
        for (int i = 0; i < 100; ++i) {
            Runnable task = queue.poll(); // 从队列中取出一个请求
            if (task == null) break; // 队列为空
            task.run(); // 处理请求
        }
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
