package cn.edu.xmu.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


public class AuthFilter implements GatewayFilter, Ordered {
    private final RedisTemplate redisTemplate;
    public AuthFilter(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }
    @Override
    public int getOrder() {
        return -1;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpCookie tempToken = exchange.getRequest().getCookies().getFirst("token");
        if (tempToken == null) {
            exchange.getResponse().setRawStatusCode(HttpStatus.UNAUTHORIZED.value());
System.out.println("notoken");
            return exchange.getResponse().setComplete();
        }else {
            String token = tempToken.getValue();
System.out.println(token);
            if (null == token || token.isEmpty() || !redisTemplate.hasKey(String.format("U:%s", token))) {
                exchange.getResponse().setRawStatusCode(HttpStatus.UNAUTHORIZED.value());
                return exchange.getResponse().setComplete();
            }

        }
        return chain.filter(exchange);
    }
}
