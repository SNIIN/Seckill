package cn.edu.xmu.core.service;


import cn.edu.xmu.core.CoreApplication;
import cn.edu.xmu.core.controller.vo.LoginVo;
import cn.edu.xmu.core.controller.vo.UserVo;
import cn.edu.xmu.core.exception.SeckillException;
import cn.edu.xmu.core.utils.RedisUtil;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.core.utils.ReturnObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@SpringBootTest(classes = CoreApplication.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private RedisUtil redisUtil;

    private static UserVo user1;
    private static LoginVo loginVo1, loginVo2, loginVo3;
    @BeforeAll
    public static void setUp() {
        loginVo1 = new LoginVo("17777777777", "90aeaad535749f0a6cb8d685e7493dd0");
        loginVo2 = new LoginVo("17777777777", "asfaf");
        loginVo3 = new LoginVo("17777777779", "90aeaad535749f0a6cb8d685e7493dd0");
        user1 = UserVo.builder().userId(17777777777L).head("").nickname("杨非凡").build();
    }
    @BeforeEach
    public void setUp2() {
        Mockito.when(redisUtil.getValueByKey("U:token")).thenReturn(user1);
    }
    @Test
    public void testDoLogin() {
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        // 登录成功
        ReturnObject returnObject1 = userService.doLogin(loginVo1, httpServletResponse);
        assertEquals(ReturnNo.SUCCESS, returnObject1.getReturnNo());
        System.out.println(redisUtil);
        // 用户不存在 | 密码错误
        assertThrows(SeckillException.class, () -> {
            userService.doLogin(loginVo2, httpServletResponse);
        });
        assertThrows(SeckillException.class, () -> {
            userService.doLogin(loginVo3, httpServletResponse);
        });
    }

    @Test
    public void testGetUserByCookie() {
        assertEquals(user1, userService.getUserByCookie("token"));
    }
}
