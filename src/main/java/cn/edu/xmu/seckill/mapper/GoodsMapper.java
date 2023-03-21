package cn.edu.xmu.seckill.mapper;

import cn.edu.xmu.seckill.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.seckill.entity.Goods;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    @Select("select sg.id as seckillId, g.*, sg.*" +
            " from t_goods g right join t_seckill_goods sg on g.id = sg.goods_id where sg.begin_time <= now() and sg.end_time >= now()")
    List<SeckillGoodsVo> selectSeckillGoodsVo();

    @Select("select sg.id as seckillId, g.*, sg.*" +
            " from t_goods g right join t_seckill_goods sg on g.id = sg.goods_id where sg.id = #{seckillId} for update")
    SeckillGoodsVo selectSeckillGoodsVoByIdForUpdate(Long seckillId);
}