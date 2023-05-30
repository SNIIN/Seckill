package cn.edu.xmu.seckill;

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
public class BoundaryTest {
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
    private Date beforeNow5, afterNow5, afterNow60;

    @BeforeEach
    public void setUp() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, -5);
        beforeNow5 = calendar.getTime();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, 5);
        afterNow5 = calendar.getTime();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, 60);
        afterNow60 = calendar.getTime();

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
    public void testPeriodBoundary1() throws Exception {
        // 活动开始前5s抢购
        seckillGoodsVo.setBeginTime(afterNow5);
        seckillGoodsVo.setEndTime(afterNow60);
        Mockito.when(redisUtil.incr("SG_STOCK:2")).thenReturn(2l);
        Mockito.when(redisUtil.decr("SG_STOCK:2")).thenReturn(1l);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(SECKILL_URL, 2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_NOT_EXIST.getCode())));
    }

    @Test
    public void testPeriodBoundary2() throws Exception {
        // 活动开始后5s抢购
        seckillGoodsVo.setBeginTime(beforeNow5);
        seckillGoodsVo.setEndTime(afterNow60);
        Mockito.when(redisUtil.incr("SG_STOCK:2")).thenReturn(2l);
        Mockito.when(redisUtil.decr("SG_STOCK:2")).thenReturn(1l);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(SECKILL_URL, 2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_IN_QUEUE.getCode())));
    }

    @Test
    public void testPeriodBoundary3() throws Exception {
        // 活动结束前5s抢购
        seckillGoodsVo.setBeginTime(beforeNow5);
        seckillGoodsVo.setEndTime(afterNow5);
        Mockito.when(redisUtil.incr("SG_STOCK:2")).thenReturn(2l);
        Mockito.when(redisUtil.decr("SG_STOCK:2")).thenReturn(1l);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(SECKILL_URL, 2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_IN_QUEUE.getCode())));
    }

    @Test
    public void testPeriodBoundary4() throws Exception {
        // 活动结束后5s抢购
        seckillGoodsVo.setBeginTime(beforeNow5);
        seckillGoodsVo.setEndTime(beforeNow5);
        Mockito.when(redisUtil.incr("SG_STOCK:2")).thenReturn(2l);
        Mockito.when(redisUtil.decr("SG_STOCK:2")).thenReturn(1l);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(SECKILL_URL, 2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_NOT_EXIST.getCode())));
    }

    @Test
    public void testRestBoundary1() throws Exception {
        // 秒杀库存剩余1件
        seckillGoodsVo.setBeginTime(beforeNow5);
        seckillGoodsVo.setEndTime(afterNow60);
        Mockito.when(redisUtil.incr("SG_STOCK:2")).thenReturn(1l);
        Mockito.when(redisUtil.decr("SG_STOCK:2")).thenReturn(0l);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(SECKILL_URL, 2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_IN_QUEUE.getCode())));
    }

    @Test
    public void testRestBoundary2() throws Exception {
        // 秒杀库存剩余0件
        seckillGoodsVo.setBeginTime(beforeNow5);
        seckillGoodsVo.setEndTime(afterNow60);
        Mockito.when(redisUtil.incr("SG_STOCK:2")).thenReturn(0l);
        Mockito.when(redisUtil.decr("SG_STOCK:2")).thenReturn(-1l);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(SECKILL_URL, 2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_NOT_REST.getCode())));
    }

    @Test
    public void testCntBoundary1() throws Exception {
        // 已购0件
        seckillGoodsVo.setBeginTime(beforeNow5);
        seckillGoodsVo.setEndTime(afterNow60);
        Mockito.when(redisUtil.incr("SG_STOCK:2")).thenReturn(1l);
        Mockito.when(redisUtil.decr("SG_STOCK:2")).thenReturn(0l);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(SECKILL_URL, 2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_IN_QUEUE.getCode())));
    }

    @Test
    public void testCntBoundary2() throws Exception {
        // 已购1件
        Mockito.when(redisUtil.isSetMember("UOG:17777777777", 2l)).thenReturn(true);
        seckillGoodsVo.setBeginTime(beforeNow5);
        seckillGoodsVo.setEndTime(afterNow60);
        Mockito.when(redisUtil.incr("SG_STOCK:2")).thenReturn(0l);
        Mockito.when(redisUtil.decr("SG_STOCK:2")).thenReturn(-1l);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(SECKILL_URL, 2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "abc")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_USER_REPEAT.getCode())));
    }
}
