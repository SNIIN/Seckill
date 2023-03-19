package cn.edu.xmu.seckill.mapper;

import cn.edu.xmu.seckill.controller.vo.SeckillOrderVo;
import cn.edu.xmu.seckill.entity.Order;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);


    @Select("select * from t_order where goods_id = #{goodsId} and user_id = #{userId}")
    Order selectByGoodsIdAndUserId(Long goodsId, Long userId);

    @Select("select o.id as orderNum, g.img, g.title, o.create_date, o.goods_price as seckillPrice from" +
            " t_order as o left join t_goods as g on o.goods_id = g.id where #{orderId} = o.id")
    SeckillOrderVo selectSeckillOrderByOrderId(Long orderId);
}