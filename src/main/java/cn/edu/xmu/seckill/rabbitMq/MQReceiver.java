package cn.edu.xmu.seckill.rabbitMq;

import cn.edu.xmu.seckill.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.seckill.entity.Goods;
import cn.edu.xmu.seckill.entity.Order;
import cn.edu.xmu.seckill.entity.RabbitMqMessage;
import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.mapper.OrderMapper;
import cn.edu.xmu.seckill.mapper.SeckillGoodsMapper;
import cn.edu.xmu.seckill.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @RabbitListener(queues = "seckillQueue")
    public void receive(String msg) {
        log.info("接收消息：" + msg);
        RabbitMqMessage mqMsg = JsonUtil.jsonStr2Object(msg, RabbitMqMessage.class);//TODO:后期应该还要继续查数据库来保障数据一致性
        SeckillGoodsVo goods = mqMsg.getVo();
        User user = mqMsg.getUser();
        Order order = Order.builder().goodsCount(1).goodsId(goods.getGoodsId()).goodsName(goods.getName())
                .userId(user.getUserId()).channel((byte) 0).goodsPrice(goods.getSeckillPrice()).createDate(new Date())
                .status((byte) 0).build();
        seckillGoodsMapper.updateBySeckillStockAndSeckillId(goods.getSeckillStock()-1, goods.getSeckillId());
        orderMapper.insert(order);
        redisTemplate.opsForValue().set("order:" + user.getUserId() + ":" + goods.getGoodsId(), order);
    }
}
