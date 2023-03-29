package cn.edu.xmu.core.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReturnNo {
    SUCCESS(200, "成功"),
    ERROR(500, "服务端异常"),
    LOGIN_ERROR(901, "手机号或密码不正确"),
    LOGIN_NON(902, "用户未登录"),
    SECKILL_GOODS_NON(701, "秒杀商品不存在"),
    SECKILL_GOODS_NOT_REST(702, "秒杀商品库存不足"),
    SECKILL_GOODS_USER_REPEAT(703, "秒杀商品每人限购一件"),
    SECKILL_GOODS_NOT_EXIST(704, "商品未处于秒杀活动中"),
    SECKILL_GOODS_IN_QUEUE(705, "已抢购，正在排队中..."),
    SECKILL_ORDER_NON(801, "秒杀订单不存在");


    private final Integer code;

    private final String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
