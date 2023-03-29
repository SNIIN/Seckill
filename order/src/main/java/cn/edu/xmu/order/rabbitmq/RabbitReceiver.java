package cn.edu.xmu.order.rabbitmq;

import cn.edu.xmu.core.exception.SeckillException;
import cn.edu.xmu.core.mapper.entity.User;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class RabbitReceiver {
    private final ObjectMapper objectMapper;
    private final GoodsService goodsService;
    private final OrderService orderService;
    public RabbitReceiver(ObjectMapper objectMapper, GoodsService goodsService, OrderService orderService) {
        this.objectMapper = objectMapper;
        this.goodsService = goodsService;
        this.orderService = orderService;
    }
    @RabbitListener(queues = RabbitMQConfig.SECKILL_QUEUE)
    public void receive(Message msg) throws Exception {
        byte[] body = msg.getBody();
        String jsonString = new String(body);
        log.info("接受者: msg = {}, jsonstring = {}", msg, jsonString);
        ObjectNode objectNode = (ObjectNode) objectMapper.readTree(jsonString);
        log.info("seckill消费者 接收消息: {}", objectNode.toString());
        User user = objectMapper.treeToValue(objectNode.get("user"), User.class);
        Long seckillId = objectNode.get("seckillId").asLong();
        log.info("seckill消费者 user: {}, seckillId: {}", user, seckillId);
        if (orderService.getOrderByUserIdAndSeckillId(user.getUserId(), seckillId) > 0)
            return;
        // 判断user合法性,略
        if (goodsService.updateBySeckillStockAndSeckillId(seckillId))
            return;
        SeckillGoodsVo goods = goodsService.getOneSeckillGoodsForUpdate(seckillId);
        orderService.createOrder(goods, user);
    }
}
