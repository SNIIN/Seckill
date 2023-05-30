package cn.edu.xmu.order.controller;

import cn.edu.xmu.core.controller.vo.UserVo;
import cn.edu.xmu.core.utils.RedisUtil;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.order.controller.vo.GoodsVo;
import cn.edu.xmu.order.service.feign.GoodsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest {
    private static final String ORDER_URL = "/order/gorder";
    private static final String RESULT_URL = "/order/result/%d";
    @Autowired
    MockMvc mockMvc;
    @MockBean
    RedisUtil redisUtil;
    @MockBean
    GoodsService goodsService;
    private UserVo userVo;
    private GoodsVo goodsVo;
    @BeforeEach
    public void setUp() {
        userVo = UserVo.builder().userId(17777777777l).nickname("杨非凡").head("").build();
        Mockito.when(redisUtil.getValueByKey("U:abc")).thenReturn(userVo);
        goodsVo = GoodsVo.builder().goodsId(2l).title("qaq").img("caca").build();
        Mockito.when(goodsService.getOneGoods(2l)).thenReturn(goodsVo);
    }
    @Test
    public void testGetOrder1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ORDER_URL)
                .param("orderId", "131994")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SUCCESS.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.img", is("caca")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderNum", is(131994)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title", is("qaq")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.seckillPrice", is(1500.0)));
    }

    @Test
    public void testGetOrder2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ORDER_URL)
                        .param("orderId", "131995")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_ORDER_NON.getCode())));
    }

    @Test
    public void testGetOrder3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ORDER_URL)
                        .param("orderId", "131998")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_NON.getCode())));
  }

    @Test
    public void testGetOrder4() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ORDER_URL)
                        .param("orderId", "131995")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.LOGIN_NON.getCode())));
    }

    @Test
    public void testGetResult1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(String.format(RESULT_URL, 1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SUCCESS.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", is(131994)));
    }

    @Test
    public void testGetResult2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(String.format(RESULT_URL, 1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.LOGIN_NON.getCode())));
    }
    @Test
    public void testGetResult3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(String.format(RESULT_URL, 3))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SUCCESS.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", is(0)));
    }
}
