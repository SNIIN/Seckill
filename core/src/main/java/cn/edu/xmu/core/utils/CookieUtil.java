package cn.edu.xmu.core.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {
    private static final String DOMAIN = "localhost";
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
        cookie.setDomain(DOMAIN);
        log.info("cookie: Name = {}, Value = {}", cookieName, cookieValue);
        httpServletResponse.addCookie(cookie);
    }
}
