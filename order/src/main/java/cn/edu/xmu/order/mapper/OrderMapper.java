package cn.edu.xmu.order.mapper;

import cn.edu.xmu.order.controller.vo.SeckillOrderVo;
import cn.edu.xmu.order.mapper.entity.Order;
import org.apache.ibatis.annotations.Select;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    @Select("select * from t_order where seckill_id = #{seckillId} and user_id = #{userId}")
    Order selectBySeckillIdAndUserId(Long seckillId, Long userId);


    @Select("select o.id as orderNum, g.img, g.title, o.create_date, o.goods_price as seckillPrice from" +
            " t_order as o left join t_goods as g on o.goods_id = g.id where #{orderId} = o.id")
    SeckillOrderVo selectSeckillOrderByOrderId(Long orderId);
}