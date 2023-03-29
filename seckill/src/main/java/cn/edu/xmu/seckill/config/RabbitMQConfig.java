package cn.edu.xmu.seckill.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RabbitMQConfig {
    public static final String SECKILL_QUEUE = "SECKILL_QUEUE";
    public static final String SECKILL_EXCHANGE = "SECKILL_SWITCH";
    public static final String SECKILL_KEY = "seckill.goods";
    @Bean
    public Queue queue() {
        return new Queue(SECKILL_QUEUE, true);
    }
    @Bean
    public TopicExchange exchange() {return new TopicExchange(SECKILL_EXCHANGE);}
    @Bean
    public Binding binding() {return BindingBuilder.bind(queue()).to(exchange()).with(SECKILL_KEY);}
}
