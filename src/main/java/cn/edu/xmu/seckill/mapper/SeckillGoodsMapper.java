package cn.edu.xmu.seckill.mapper;

import cn.edu.xmu.seckill.entity.SeckillGoods;

public interface SeckillGoodsMapper {
    int deleteByPrimaryKey(Long seckillId);

    int insert(SeckillGoods record);

    int insertSelective(SeckillGoods record);

    SeckillGoods selectByPrimaryKey(Long seckillId);

    int updateByPrimaryKeySelective(SeckillGoods record);

    int updateByPrimaryKey(SeckillGoods record);

}