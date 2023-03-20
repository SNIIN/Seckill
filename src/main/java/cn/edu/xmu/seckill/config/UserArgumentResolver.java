package cn.edu.xmu.seckill.config;

import cn.edu.xmu.seckill.config.annotation.SeckillUser;
import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.exception.SeckillException;
import cn.edu.xmu.seckill.service.IUserService;
import cn.edu.xmu.seckill.utils.CookieUtil;
import cn.edu.xmu.seckill.utils.ReturnNo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final IUserService userService;

    @Autowired
    public UserArgumentResolver(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SeckillUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = CookieUtil.getCookieValue(httpServletRequest, "token");
        if (StringUtils.isEmpty(token)) {
            throw new SeckillException(ReturnNo.LOGIN_NON);
        }
        User user = userService.getUserByCookie(token);
        if (null == user) {
            throw new SeckillException(ReturnNo.LOGIN_NON);
        }
        return user;
    }
}
