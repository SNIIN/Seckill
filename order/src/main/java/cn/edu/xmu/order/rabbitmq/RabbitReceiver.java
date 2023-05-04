package cn.edu.xmu.order.rabbitmq;

import cn.edu.xmu.core.controller.vo.UserVo;
import cn.edu.xmu.core.exception.SeckillException;
import cn.edu.xmu.core.utils.RedisUtil;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.order.config.RabbitMQConfig;
import cn.edu.xmu.core.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.order.service.OrderService;
import cn.edu.xmu.order.service.feign.GoodsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class RabbitReceiver {
    private final ObjectMapper objectMapper;
    private final GoodsService goodsService;
    private final OrderService orderService;
    private final RedisTemplate redisTemplate;
    private final RedisUtil redisUtil;

    @Autowired
    public RabbitReceiver(RedisUtil redisUtil, ObjectMapper objectMapper, GoodsService goodsService, OrderService orderService, RedisTemplate redisTemplate) {
        this.objectMapper = objectMapper;
        this.redisUtil = redisUtil;
        this.goodsService = goodsService;
        this.redisTemplate = redisTemplate;
        this.orderService = orderService;
    }
    @RabbitListener(queues = RabbitMQConfig.SECKILL_QUEUE)
    @Transactional
    public void receive(Message msg) throws Exception {
        byte[] body = msg.getBody();
        String jsonString = new String(body);
        ObjectNode objectNode = (ObjectNode) objectMapper.readTree(jsonString);
        UserVo user = objectMapper.treeToValue(objectNode.get("user"), UserVo.class);
        Long seckillId = objectNode.get("seckillId").asLong();
        Boolean unlocked = redisTemplate.opsForValue().setIfAbsent(String.format("LOCK_US:%d_%d", user.getUserId(), seckillId), "1", 10, TimeUnit.SECONDS);
        if (unlocked != null && unlocked) {
            try {
                Long result = orderService.getOrderByUserIdAndSeckillId(user.getUserId(), seckillId);
                if (result > 0)
                    return;
                // 判断user合法性,略
                SeckillGoodsVo goods = (SeckillGoodsVo) redisTemplate.opsForValue().get(SeckillGoodsVo.RedisKey(seckillId));
                if (null == result) {
                    goods = goodsService.getOneSeckillGoods(seckillId);
                    redisUtil.addAsKeyValue(SeckillGoodsVo.RedisKey(seckillId), goods, true);
                }
                if (null == goods)
                    return;
                if (goodsService.downSeckillsCount(seckillId))
                    return;
                orderService.createOrder(goods, user);
            }finally {
                redisTemplate.delete(String.format("LOCK_US:%d_%d", user.getUserId(), seckillId));
            }
        }else {
            throw new SeckillException(ReturnNo.SECKILL_ORDER_NON);
        }
    }
}
