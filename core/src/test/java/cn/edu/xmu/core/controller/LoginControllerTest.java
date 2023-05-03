package cn.edu.xmu.core.controller;


import cn.edu.xmu.core.controller.vo.LoginVo;
import cn.edu.xmu.core.utils.RedisUtil;
import cn.edu.xmu.core.utils.ReturnNo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private RedisUtil redisUtil;

    private static LoginVo loginVo1, loginVo2, loginVo3;
    private static ObjectMapper objectMapper;
    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        loginVo1 = new LoginVo("17777777777", "90aeaad535749f0a6cb8d685e7493dd0");
        loginVo2 = new LoginVo("17777777777", "90aeaaf535749f0a6cb8d685e7493dd0");
        loginVo3 = new LoginVo("177777777", "90aeaad535749f0a6cb8d685e7493dd0");
    }

    @Test
    public void testDoLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/dologin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(loginVo1))
        ).andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SUCCESS.getCode())));

        mockMvc.perform(MockMvcRequestBuilders.post("/dologin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(loginVo2))
                ).andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.LOGIN_ERROR.getCode())));

        mockMvc.perform(MockMvcRequestBuilders.post("/dologin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(loginVo3))
                ).andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.ERROR.getCode())));
    }
}
