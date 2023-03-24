package cn.edu.xmu.seckill.entity;

import cn.edu.xmu.seckill.controller.vo.SeckillGoodsVo;
import lombok.*;

@ToString
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RabbitMqMessage {

    private SeckillGoodsVo vo;
    private User user;
}
