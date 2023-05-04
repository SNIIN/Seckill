package cn.edu.xmu.goods.mapper;

import cn.edu.xmu.core.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.goods.controller.vo.GoodsVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsMapper {

    @Select("select sg.id as seckillId, g.*, sg.*" +
            " from t_goods g right join t_seckill_goods sg on g.id = sg.goods_id where sg.begin_time <= now() and sg.end_time >= now()")
    List<SeckillGoodsVo> selectSeckillGoodsVo();

    @Select("select sg.id as seckillId, g.*, sg.*" +
            " from t_goods g right join t_seckill_goods sg on g.id = sg.goods_id where sg.id = #{seckillId}")
    SeckillGoodsVo selectSeckillGoodsVoByIdForUpdate(Long seckillId);
    @Select("select sg.id as seckillId, g.*, sg.*" +
            " from t_goods g right join t_seckill_goods sg on g.id = sg.goods_id where sg.id = #{seckillId}")
    SeckillGoodsVo selectSeckillGoodsVoById(Long seckillId);
    @Select("select id as goodsId, img, title" +
            " from t_goods where id = #{goodsId}")
    GoodsVo selectGoodsVoById(Long goodsId);
}