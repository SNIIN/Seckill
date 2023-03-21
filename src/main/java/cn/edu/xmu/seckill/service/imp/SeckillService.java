package cn.edu.xmu.seckill.service.imp;

import cn.edu.xmu.seckill.config.annotation.SeckillUser;
import cn.edu.xmu.seckill.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.seckill.controller.vo.SeckillOrderVo;
import cn.edu.xmu.seckill.entity.Goods;
import cn.edu.xmu.seckill.entity.Order;
import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.exception.SeckillException;
import cn.edu.xmu.seckill.mapper.GoodsMapper;
import cn.edu.xmu.seckill.mapper.OrderMapper;
import cn.edu.xmu.seckill.mapper.SeckillGoodsMapper;
import cn.edu.xmu.seckill.service.IGoodsService;
import cn.edu.xmu.seckill.service.ISeckillService;
import cn.edu.xmu.seckill.utils.ReturnNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SeckillService implements ISeckillService {

    private final IGoodsService goodsService;
    private final OrderMapper orderMapper;
    private final SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    public SeckillService(IGoodsService goodsService, OrderMapper orderMapper, SeckillGoodsMapper seckillGoodsMapper) {
        this.goodsService = goodsService;
        this.orderMapper = orderMapper;
        this.seckillGoodsMapper = seckillGoodsMapper;
    }

    @Override
    public Order doSeckill(User user, Long seckillId) {
        SeckillGoodsVo goods = goodsService.getOneSeckillGoods(seckillId);
        if (0 >= goods.getSeckillStock()) {
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_REST);
        }
        if (null != orderMapper.selectByGoodsIdAndUserId(goods.getGoodsId(), user.getUserId())) {
            throw new SeckillException(ReturnNo.SECKILL_GOODS_USER_REPEAT);
        }
        Date now = new Date();
        if (!(now.after(goods.getBeginTime()) && now.before(goods.getEndTime()))) {
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_EXIST);
        }
        Order order = Order.builder().goodsCount(1).goodsId(goods.getGoodsId()).goodsName(goods.getName())
                .userId(user.getUserId()).channel((byte) 0).goodsPrice(goods.getSeckillPrice()).createDate(new Date())
                .status((byte) 0).build();
        orderMapper.insert(order);
        seckillGoodsMapper.updateBySeckillStockAndSeckillId(goods.getSeckillStock()-1, seckillId);
        return order;
    }

    @Override
    public SeckillOrderVo getSeckillOrder(Long orderId) {
        SeckillOrderVo order = orderMapper.selectSeckillOrderByOrderId(orderId);
        if (null == order) {
            throw new SeckillException(ReturnNo.SECKILL_ORDER_NON);
        }
        return order;
    }
}
