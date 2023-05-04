package cn.edu.xmu.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    private static final int BASETIMEOUT = 3000;
    private static final Random random = new Random();
    private final RedisTemplate redisTemplate;
    @Autowired
    public RedisUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取随机过期时间，为3000~3600s之间
     * @return
     */
    private static int getRandomTimeOut() {
        return BASETIMEOUT + random.nextInt(600);
    }

    /**
     * 在reids中添加键值对 key - value
     * @param key
     * @param object
     */
    public void addAsKeyValue(String key, Object object, boolean timeOut) {
        if (timeOut)
            redisTemplate.opsForValue().set(key, object, getRandomTimeOut(), TimeUnit.SECONDS);
        else
            redisTemplate.opsForValue().set(key, object);
    }

    /**
     * 在redis中的名为key的set中添加元素value，并充值该set的过期时间
     * @param key
     * @param value
     */
    public void addToSet(String key, Object value, boolean timeOut) {
        redisTemplate.opsForSet().add(key, value);
        if (timeOut)
            redisTemplate.expire(key, getRandomTimeOut(), TimeUnit.SECONDS);
    }

    /**
     * 通过key获取value
     * @param key
     * @return
     */
    public Object getValueByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 判断redis中是否存在名为key的set，且set中存在元素value
     * @param key
     * @param value
     * @return
     */
    public Boolean isSetMember(String key, Object value) {
        Boolean result = redisTemplate.opsForSet().isMember(key, value);
        if (null == result || !result) return false;
        return true;
    }

    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Long decr(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    public Set<String> keys(String key) {
        return redisTemplate.keys(key);
    }
    public void deleteKeys(Set<String> keys) {
        redisTemplate.delete(keys);
    }
}
