package cn.edu.xmu.order.service;

import cn.edu.xmu.core.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.core.controller.vo.UserVo;
import cn.edu.xmu.core.utils.RedisUtil;
import cn.edu.xmu.order.controller.vo.GoodsVo;
import cn.edu.xmu.order.controller.vo.SeckillOrderVo;
import cn.edu.xmu.order.service.feign.GoodsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @MockBean
    RedisUtil redisUtil;
    @MockBean
    GoodsService goodsService;
    @Test
    public void testCreateOrder() {
        Mockito.when(goodsService.getOneGoods(3000l)).thenReturn(GoodsVo.builder().goodsId(3000l).img("qaqc").build());
        SeckillGoodsVo seckillGoodsVo = new SeckillGoodsVo();
        seckillGoodsVo.setSeckillId(2000l);
        seckillGoodsVo.setGoodsId(3000l);
        seckillGoodsVo.setImg("qaqc");
        UserVo userVo = UserVo.builder().userId(17777777777L).head("").nickname("杨非凡").build();
        Long orderId = orderService.createOrder(seckillGoodsVo, userVo);
        SeckillOrderVo res = orderService.getSeckillOrder(orderId);
        assertEquals(orderId, res.getOrderNum());
        assertEquals("qaqc", res.getImg());
    }
}
