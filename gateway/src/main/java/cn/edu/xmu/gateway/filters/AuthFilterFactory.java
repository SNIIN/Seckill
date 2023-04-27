package cn.edu.xmu.gateway.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuthFilterFactory implements GatewayFilterFactory<AuthFilterFactory.Config> {
    private final RedisTemplate redisTemplate;
    @Autowired
    public AuthFilterFactory(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    public GatewayFilter apply(Config config) {
        return new AuthFilter(redisTemplate);
    }

    @Override
    public Config newConfig() {
         return new Config();
    }
    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }
    public static class Config{

    }

}
