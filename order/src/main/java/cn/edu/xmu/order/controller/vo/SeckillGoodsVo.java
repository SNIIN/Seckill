package cn.edu.xmu.order.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SeckillGoodsVo{

    public String RedisSeckillStockKey() {
        return String.format("SG_STOCK:%d", seckillId);
    }
    public String RedisKey() {
        return String.format("SG:%d", seckillId);
    }

    public static String RedisSeckillStockKey(Long seckillId) {
        return String.format("SG_STOCK:%d", seckillId);
    }
    public static String RedisKey(Long seckillId) {
        return String.format("SG:%d", seckillId);
    }
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
