package cn.edu.xmu.gateway.globalfilters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
//@Component
public class ResponseHeaderFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            List<String> cookies = response.getHeaders().get("Set-Cookie");
            if (cookies != null) {
                for (String cookie : cookies) {
                    System.out.println(cookie);
                }
            }
        }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
