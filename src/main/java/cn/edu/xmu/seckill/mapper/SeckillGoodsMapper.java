package cn.edu.xmu.seckill.mapper;

import cn.edu.xmu.seckill.entity.SeckillGoods;
import org.apache.ibatis.annotations.Update;

public interface SeckillGoodsMapper {
    int deleteByPrimaryKey(Long seckillId);

    int insert(SeckillGoods record);

    int insertSelective(SeckillGoods record);

    SeckillGoods selectByPrimaryKey(Long seckillId);

    int updateByPrimaryKeySelective(SeckillGoods record);

    int updateByPrimaryKey(SeckillGoods record);

    @Update("update t_seckill_goods set seckill_stock = #{seckillStock} where id = #{seckillId}")
    int updateBySeckillStockAndSeckillId(Integer seckillStock, Long seckillId);

}