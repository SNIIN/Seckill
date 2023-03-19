package cn.edu.xmu.seckill.mapper;

import cn.edu.xmu.seckill.entity.Order;
import org.apache.ibatis.annotations.Select;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);


    @Select("select * from t_order where goods_id = #{goodsId} and user_id = #{userId}")
    Order selectByGoodsIdAndUserId(Long goodsId, Long userId);
}