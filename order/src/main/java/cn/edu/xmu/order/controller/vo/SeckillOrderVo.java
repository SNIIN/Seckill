package cn.edu.xmu.order.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeckillOrderVo {
    private String img;
    private Long orderNum;
    private Date createDate;
    private String title;
    private BigDecimal seckillPrice;
}
