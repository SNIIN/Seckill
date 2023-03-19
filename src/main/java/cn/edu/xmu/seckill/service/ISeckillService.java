package cn.edu.xmu.seckill.service;

import cn.edu.xmu.seckill.controller.vo.SeckillOrderVo;
import cn.edu.xmu.seckill.entity.Order;
import cn.edu.xmu.seckill.entity.User;

public interface ISeckillService {
    Order doSeckill(User ser, Long seckillId);

    SeckillOrderVo getSeckillOrder(Long orderId);
}
