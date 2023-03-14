package cn.edu.xmu.seckill.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {
    public static String getCookieValue(HttpServletRequest httpServletRequest, String cookieName) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null || cookieName == null) return null;
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void setCookieValue(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        String host = httpServletRequest.getHeader("Host");
        if (host != null) {
            String domain = host.split(":")[0];
            cookie.setDomain(domain);
        }
        log.info("cookie: Name = {}, Value = {}", cookieName, cookieValue);
        httpServletResponse.addCookie(cookie);
    }
}
