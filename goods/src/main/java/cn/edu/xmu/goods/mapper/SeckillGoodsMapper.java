package cn.edu.xmu.goods.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SeckillGoodsMapper {


    @Update("update t_seckill_goods set seckill_stock = #{cnt} where id = #{seckillId}")
    int forTest1(Long seckillId, Integer cnt);

    @Select("select seckill_stock from t_seckill_goods where id = #{seckillId}")
    int forCheck1(Long seckillId);

    @Update("update t_seckill_goods set seckill_stock = seckill_stock-1 where id " +
            "= #{seckilldId} and seckill_stock > 0")
    int updateBySeckillStockAndSeckillId(Long seckillId);

}