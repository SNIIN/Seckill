package cn.edu.xmu.goods.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsVo {
    public static String redisKey(Long goodsId) {
        return String.format("GV:%d", goodsId);
    }
    private Long goodsId;
    private String title;
    private String img;
}
