package cn.edu.xmu.goods.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GoodsVo {
    private Long goodsId;
    private String title;
    private String img;
}
