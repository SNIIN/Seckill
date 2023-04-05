package cn.edu.xmu.order.rabbitmq;

import cn.edu.xmu.core.exception.SeckillException;
import cn.edu.xmu.core.mapper.entity.User;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.order.config.RabbitMQConfig;
import cn.edu.xmu.order.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.order.service.OrderService;
import cn.edu.xmu.order.service.feign.GoodsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rabbitmq.client.Channel;
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
    @Autowired
    public RabbitReceiver(ObjectMapper objectMapper, GoodsService goodsService, OrderService orderService, RedisTemplate redisTemplate) {
        this.objectMapper = objectMapper;
        this.goodsService = goodsService;
        this.redisTemplate = redisTemplate;
        this.orderService = orderService;
    }
    @RabbitListener(queues = RabbitMQConfig.SECKILL_QUEUE)
    @Transactional
    public void receive(Message msg) throws Exception {
        Long begin = System.currentTimeMillis();
        byte[] body = msg.getBody();
        String jsonString = new String(body);
        log.info("接受者: msg = {}, jsonstring = {}", msg, jsonString);
        ObjectNode objectNode = (ObjectNode) objectMapper.readTree(jsonString);
        log.info("seckill消费者 接收消息: {}", objectNode.toString());
        User user = objectMapper.treeToValue(objectNode.get("user"), User.class);
        Long seckillId = objectNode.get("seckillId").asLong();
        log.info("seckill消费者 user: {}, seckillId: {}", user, seckillId);
        Boolean unlocked = redisTemplate.opsForValue().setIfAbsent(String.format("LOCK_US:%d_%d", user.getUserId(), seckillId), "1", 10, TimeUnit.SECONDS);
        if (unlocked != null && unlocked) {
            try {
                Long result = orderService.getOrderByUserIdAndSeckillId(user.getUserId(), seckillId);
                Long end = System.currentTimeMillis();
                log.info("查得订单： {}, {}ms", result, end - begin);
                if (result > 0)
                    return;
                // 判断user合法性,略
                if (goodsService.updateBySeckillStockAndSeckillId(seckillId))
                    return;
                Long end2 = System.currentTimeMillis();
                log.info("扣库存： {}ms", end2 - end);
                SeckillGoodsVo goods = goodsService.getOneSeckillGoodsForUpdate(seckillId);
                orderService.createOrder(goods, user);
                Long end3 = System.currentTimeMillis();
                log.info("总耗时: {}ms", end3 - begin);
            }finally {
                redisTemplate.delete(String.format("LOCK_US:%d_%d", user.getUserId(), seckillId));
            }
        }else {
            throw new SeckillException(ReturnNo.SECKILL_ORDER_NON);
        }
    }
}
