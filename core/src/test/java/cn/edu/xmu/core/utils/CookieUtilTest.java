package cn.edu.xmu.core.utils;


import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class CookieUtilTest {
    @Test
    public void testGetCookieValue() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("testCookie1", "testValue1");
        cookies[1] = new Cookie("tesCookie2", "test2");
        when(request.getCookies()).thenReturn(cookies);

        String result = CookieUtil.getCookieValue(request, "tesCookie2");
        assertEquals("test2", result);
    }

    @Test
    public void testSetCookieValue() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        CookieUtil.setCookieValue(response, "token", "123456");
        verify(response).addCookie(argThat(new ArgumentMatcher<Cookie>() {
            @Override
            public boolean matches(Cookie cookie) {
                return cookie.getName().equals("token") &&
                        cookie.getValue().equals("123456") &&
                        cookie.getDomain().equals(CookieUtil.DOMAIN) &&
                        cookie.getPath().equals("/") &&
                        (cookie.getMaxAge() == 3600);
            }
        }));
    }

}
