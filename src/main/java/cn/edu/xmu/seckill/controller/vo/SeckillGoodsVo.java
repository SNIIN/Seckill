package cn.edu.xmu.seckill.controller.vo;

import cn.edu.xmu.seckill.entity.Goods;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SeckillGoodsVo{
    private Long seckillId;

    private Long goodsId;
    private String name;

    private String title;

    private String img;
    private String details;
    private BigDecimal seckillPrice;
    private BigDecimal price;
    private Integer seckillStock;

    private Date beginTime;

    private Date endTime;
}
