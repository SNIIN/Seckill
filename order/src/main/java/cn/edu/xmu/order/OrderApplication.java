package cn.edu.xmu.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.order", "cn.edu.xmu.core.utils", "cn.edu.xmu.core.config"})
@EnableFeignClients
@MapperScan("cn.edu.xmu.order.mapper")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
