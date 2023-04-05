package cn.edu.xmu.goods.mapper;

import cn.edu.xmu.goods.mapper.entity.SeckillGoods;
import org.apache.ibatis.annotations.Update;

public interface SeckillGoodsMapper {
    int deleteByPrimaryKey(Long seckillId);

    int insert(SeckillGoods record);

    int insertSelective(SeckillGoods record);

    SeckillGoods selectByPrimaryKey(Long seckillId);

    int updateByPrimaryKeySelective(SeckillGoods record);

    int updateByPrimaryKey(SeckillGoods record);

    @Update("update t_seckill_goods set seckill_stock = seckill_stock-1 where id " +
            "= #{seckilldId} and seckill_stock > 0")
    int updateBySeckillStockAndSeckillId(Long seckillId);

}