package cn.edu.xmu.seckill.rabbitmq;

import cn.edu.xmu.core.controller.vo.UserVo;
import cn.edu.xmu.seckill.config.RabbitMQConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitSender {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    @Autowired
    public RabbitSender(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(UserVo user, Long seckillId) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.set("user", objectMapper.valueToTree(user));
        jsonObject.put("seckillId", seckillId);
        String msg = jsonObject.toString();
        log.info("seckill生产者发送消息: {}", msg);
        rabbitTemplate.convertAndSend(RabbitMQConfig.SECKILL_QUEUE, msg);
    }
}
