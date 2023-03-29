package cn.edu.xmu.core.config;

import cn.edu.xmu.core.config.annotation.SeckillUser;
import cn.edu.xmu.core.mapper.entity.User;
import cn.edu.xmu.core.exception.SeckillException;
import cn.edu.xmu.core.utils.CookieUtil;
import cn.edu.xmu.core.utils.ReturnNo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final RedisTemplate redisTemplate;

    @Autowired
    public UserArgumentResolver(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SeckillUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Long begin = System.currentTimeMillis();
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (null == httpServletRequest)
            throw new SeckillException(ReturnNo.ERROR);
        String token = CookieUtil.getCookieValue(httpServletRequest, "token");
        log.info("token = {}", token);
        if (null == token || token.isEmpty()) {
            throw new SeckillException(ReturnNo.LOGIN_NON);
        }
        User user = (User) redisTemplate.opsForValue().get(User.RedisKey(token));
        if (null == user) {
            throw new SeckillException(ReturnNo.LOGIN_NON);
        }
        Long end = System.currentTimeMillis();
        log.info("解析登录(成功)耗时：{}ms", end-begin);
        return user;
    }
}
