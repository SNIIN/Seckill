package cn.edu.xmu.seckill.controller.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SeckillOrderVo {
    private String img;
    private Long orderNum;
    private Date createDate;
    private String title;
    private BigDecimal seckillPrice;
}
