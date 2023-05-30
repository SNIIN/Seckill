package cn.edu.xmu.goods.controller;

import cn.edu.xmu.core.utils.RedisUtil;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.goods.controller.vo.GoodsVo;
import cn.edu.xmu.goods.mapper.SeckillGoodsMapper;
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class GoodsControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    RedisUtil redisutil;
    @Autowired
    SeckillGoodsMapper seckillGoodsMapper;
    public static final String GoodsListUrl = "/goods/glist";
    public static final String GoodsListUUrl = "/goods/list";
    public static final String GoodsDetailUrl = "/goods/gdetail";
    public static final String OneSeckillGoodsUrl = "/goods/seckill";
    public static final String OneGoodsUrl = "/goods/goods";
    public static final String SeckillCountDown = "/goods/down";
    private GoodsVo goodsVo;
    @BeforeEach
    public void setup() {
        goodsVo = GoodsVo.builder().goodsId(2l).img("qaq").title("aca").build();
        Mockito.when(redisutil.getValueByKey("GV:2")).thenReturn(goodsVo);
    }
    @Test
    public void testGetlist1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(GoodsListUrl)
                .param("pageNum", "1")
                .param("pageSize", "3")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SUCCESS.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()", is(3)));
    }
    @Test
    public void testGetlist2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(GoodsListUrl)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SUCCESS.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()", is(12)));
    }
    @Test
    public void testGetlist3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(GoodsListUUrl)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(12)));
    }
    @Test
    public void testGetDetail1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(GoodsDetailUrl)
                        .param("seckillid", "1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.seckillId", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.goodsId", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("商品2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.img", is("O1CN01HEKmuA1CrPsJnghqX_!!0-saturn_solar.jpg_300x300q90.jpg")));
    }
    @Test
    public void testGetDetail2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(GoodsDetailUrl)
                        .param("seckillid", "30")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnNo.code", is(ReturnNo.SECKILL_GOODS_NON.getCode())));
    }
    @Test
    public void testGetOneSeckillGoods1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(OneSeckillGoodsUrl)
                        .param("seckillid", "1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seckillId", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.goodsId", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("商品2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.img", is("O1CN01HEKmuA1CrPsJnghqX_!!0-saturn_solar.jpg_300x300q90.jpg")));
    }
    @Test
    public void testGetOneSeckillGoods2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(OneSeckillGoodsUrl)
                        .param("seckillid", "30")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
    @Test
    public void testGetOneGoods1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(OneGoodsUrl)
                        .param("goodsid", "2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.goodsId", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("aca")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.img", is("qaq")));
    }

    @Test
    public void testGetOneGoods2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(OneGoodsUrl)
                        .param("goodsid", "3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.goodsId", is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("KATO遮瑕膏三色遮暇盘液官方正品旗舰店推荐奶酪笔遮盖脸部黑眼圈")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.img", is("O1CN01V5mTPD1pzRqqH708x_!!0-item_pic.jpg_300x300q90.jpg")));
    }

    @Test
    public void testGetOneGoods3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(OneGoodsUrl)
                        .param("goodsid", "1000")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    public void testDownSeckillsCount1() throws Exception {
        seckillGoodsMapper.forTest1(2l, 1);
        mockMvc.perform(MockMvcRequestBuilders.get(SeckillCountDown)
                        .param("seckillid", "2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(false)));
        assertEquals(0, seckillGoodsMapper.forCheck1(2l));
    }

    @Test
    public void testDownSeckillsCount2() throws Exception {
        seckillGoodsMapper.forTest1(2l, 1);
        seckillGoodsMapper.forTest1(3l, 0);
        mockMvc.perform(MockMvcRequestBuilders.get(SeckillCountDown)
                        .param("seckillid", "3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(true)));
        assertEquals(0, seckillGoodsMapper.forCheck1(3l));
    }

    @Test
    public void testDownSeckillsCount3() throws Exception {
        seckillGoodsMapper.forTest1(2l, 1);
        seckillGoodsMapper.forTest1(3l, 0);
        mockMvc.perform(MockMvcRequestBuilders.get(SeckillCountDown)
                        .param("seckillid", "1000")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(true)));
    }


}
