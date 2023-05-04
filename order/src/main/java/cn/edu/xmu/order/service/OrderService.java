package cn.edu.xmu.order.service;

import cn.edu.xmu.core.controller.vo.UserVo;
import cn.edu.xmu.core.exception.SeckillException;
import cn.edu.xmu.core.utils.RedisUtil;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.order.controller.vo.GoodsVo;
import cn.edu.xmu.core.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.order.controller.vo.SeckillOrderVo;
import cn.edu.xmu.order.mapper.OrderMapper;
import cn.edu.xmu.order.mapper.entity.Order;
import cn.edu.xmu.order.service.feign.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class OrderService {
    private final RedisUtil redisUtil;
    private final RedisTemplate redisTemplate;
    private final OrderMapper orderMapper;
    private final GoodsService goodsService;
    @Autowired
    public OrderService(RedisUtil redisUtil, GoodsService goodsService, OrderMapper orderMapper, RedisTemplate redisTemplate) {
        this.redisUtil = redisUtil;
        this.redisTemplate = redisTemplate;
        this.goodsService = goodsService;
        this.orderMapper = orderMapper;
    }

    public Long createOrder(SeckillGoodsVo goods, UserVo user) {
        Order order = null;
        order = Order.builder().goodsCount(1).goodsId(goods.getGoodsId()).goodsName(goods.getName())
                .userId(user.getUserId()).channel((byte) 0).goodsPrice(goods.getSeckillPrice()).createDate(new Date())
                .status((byte) 0).seckillId(goods.getSeckillId()).build();
        orderMapper.insert(order);
        redisUtil.addToSet(String.format("UOG:%d", user.getUserId()), goods.getSeckillId(), true);
        return order.getOrderId();
    }

    public SeckillOrderVo getSeckillOrder(Long orderId) {
        Order tmp = orderMapper.selectByPrimaryKey(orderId);
        if (null == tmp) {
            throw new SeckillException(ReturnNo.SECKILL_ORDER_NON);
        }
        log.info("订单: {}", tmp.toString());
        GoodsVo goods = goodsService.getOneGoods(tmp.getGoodsId());
        if (null == goods) {
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NON);
        }
        log.info("商品: {}", goods);
        SeckillOrderVo order = SeckillOrderVo.builder().orderNum(orderId).seckillPrice(tmp.getGoodsPrice())
                .createDate(tmp.getCreateDate()).title(goods.getTitle()).img(goods.getImg()).build();
        return order;
    }

    public Long getOrderByUserIdAndSeckillId(Long userId, Long seckillId) {
        Order order = orderMapper.selectBySeckillIdAndUserId(seckillId, userId);
        if (null == order)
            return 0l;
        return order.getOrderId();
    }
}
