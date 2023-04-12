package cn.edu.xmu.order.config;




import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class RabbitMQConfig {

    public static final String SECKILL_QUEUE = "SECKILL_QUEUE";
}
