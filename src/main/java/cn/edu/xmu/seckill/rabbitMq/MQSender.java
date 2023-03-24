package cn.edu.xmu.seckill.rabbitMq;

import cn.edu.xmu.seckill.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendSeckillMsg(String msg){
        log.info("发送秒杀消息"+msg);
        rabbitTemplate.convertAndSend("seckillExchange","seckill.msg",msg);
    }

}
