package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.core.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.core.controller.vo.UserVo;
import cn.edu.xmu.core.utils.RedisUtil;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.seckill.rabbitmq.RabbitSender;
import cn.edu.xmu.seckill.service.feign.GoodsService;
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

import javax.servlet.http.Cookie;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
@SpringBootTest
@AutoConfigureMockMvc
public class SeckillControllerTest {
    private static final String SECKILL_URL = "/seckill/%d";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    RedisUtil redisUtil;
    @MockBean
    GoodsService goodsService;
    @MockBean
    RabbitSender rabbitSender;
    private UserVo userVo;
    private SeckillGoodsVo seckillGoodsVo;
    private Date date1, date2;
    @BeforeEach
    public void setUp() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        date1 = calendar.getTime();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date2 = calendar.getTime();

        seckillGoodsVo = new SeckillGoodsVo();
        seckillGoodsVo.setSeckillId(2l);
        seckillGoodsVo.setGoodsId(3l);

        userVo = UserVo.builder().userId(17777777777l).nickname("杨非凡").head("").build();
        Mockito.when(redisUtil.getValueByKey("U:abc")).thenReturn(userVo);
        Mockito.when(redisUtil.isSetMember("UOG:17777777777", 3l)).thenReturn(true);
        Mockito.when(redisUtil.getValueByKey("SG:2")).thenReturn(seckillGoodsVo);
        Mockito.when(redisUtil.getValueByKey("SG:3")).thenReturn(seckillGoodsVo);
    }

    @Test
    public void testDoSeckill1() throws Exception {
        seckillGoodsVo.setBeginTime(date1);
        seckillGoodsVo.setEndTime(date2);
        Mockito.when(redisUtil.incr("SG_STOCK:2")).thenReturn(2l);
        Mockito.when(redisUtil.decr("SG_STOCK:2")).thenReturn(1l);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(SECKILL_URL, 2))
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_IN_QUEUE.getCode())));
    }

    @Test
    public void testDoSeckill2() throws Exception {
        seckillGoodsVo.setBeginTime(date2);
        seckillGoodsVo.setEndTime(date2);
        Mockito.when(redisUtil.incr("SG_STOCK:2")).thenReturn(2l);
        Mockito.when(redisUtil.decr("SG_STOCK:2")).thenReturn(1l);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(SECKILL_URL, 2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_NOT_EXIST.getCode())));

        seckillGoodsVo.setBeginTime(date1);
        seckillGoodsVo.setEndTime(date1);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(SECKILL_URL, 2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_NOT_EXIST.getCode())));

        seckillGoodsVo.setBeginTime(date2);
        seckillGoodsVo.setEndTime(date1);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(SECKILL_URL, 2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_NOT_EXIST.getCode())));
    }

    @Test
    public void testDoSeckill3() throws Exception {
        seckillGoodsVo.setBeginTime(date1);
        seckillGoodsVo.setEndTime(date2);
        Mockito.when(redisUtil.incr("SG_STOCK:3")).thenReturn(2l);
        Mockito.when(redisUtil.decr("SG_STOCK:3")).thenReturn(1l);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(SECKILL_URL, 3))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_USER_REPEAT.getCode())));

        Mockito.when(redisUtil.incr("SG_STOCK:2")).thenReturn(0l);
        Mockito.when(redisUtil.decr("SG_STOCK:2")).thenReturn(-1l);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(SECKILL_URL, 2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_NOT_REST.getCode())));

    }
}
