package cn.edu.xmu.seckill.service;

import cn.edu.xmu.seckill.controller.vo.SeckillOrderVo;
import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.utils.ReturnObject;

public interface ISeckillService {
    ReturnObject doSeckill(User ser, Long seckillId);

    SeckillOrderVo getSeckillOrder(Long orderId);

    Long getOrderStatus(User uer,Long goodsId);
}
