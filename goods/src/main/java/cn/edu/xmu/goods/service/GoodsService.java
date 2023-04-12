package cn.edu.xmu.goods.service;

import cn.edu.xmu.core.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.core.utils.RedisUtil;
import cn.edu.xmu.goods.controller.vo.GoodsVo;
import cn.edu.xmu.core.exception.SeckillException;
import cn.edu.xmu.goods.mapper.GoodsMapper;
import cn.edu.xmu.goods.mapper.SeckillGoodsMapper;
import cn.edu.xmu.core.utils.ReturnNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    private final GoodsMapper goodsMapper;
    private final SeckillGoodsMapper seckillGoodsMapper;
    private final RedisUtil redisUtil;
    @Autowired
    public GoodsService(RedisUtil redisUtil, GoodsMapper goodsMapper, SeckillGoodsMapper seckillGoodsMapper) {
        this.seckillGoodsMapper = seckillGoodsMapper;
        this.goodsMapper = goodsMapper;
        this.redisUtil = redisUtil;
    }

    /**
     * 获取第page页，当前处于秒杀活动的商品列表
     * 一页最大商品数量默认10， page从1开始, (暂不实现分页)
     * @param page
     * @return
     */
    public List<SeckillGoodsVo> getOnePageGoodsList(int page) {
        List<SeckillGoodsVo> result = goodsMapper.selectSeckillGoodsVo();
        return result;
    }

    public SeckillGoodsVo getOneSeckillGoods(Long seckillId) {
//        SeckillGoodsVo result = (SeckillGoodsVo) redisUtil.getValueByKey(SeckillGoodsVo.RedisKey(seckillId));
//        if (null == result) {
        SeckillGoodsVo result = goodsMapper.selectSeckillGoodsVoByIdForUpdate(seckillId);
        if (null == result) {
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NON);
        }
//        }
        return result;
    }

    public Boolean updateBySeckillStockAndSeckillId(Long seckillId) {
        Integer result = seckillGoodsMapper.updateBySeckillStockAndSeckillId(seckillId);
        return result == 0 ? true : false;
    }

    public GoodsVo getGoodsVoById(Long goodsId) {
        GoodsVo goodsVo = (GoodsVo) redisUtil.getValueByKey(GoodsVo.redisKey(goodsId));
        if (null != goodsVo) return goodsVo;
        else {
            goodsVo = goodsMapper.selectGoodsVoById(goodsId);
            if (null != goodsVo)
                redisUtil.addAsKeyValue(GoodsVo.redisKey(goodsId), goodsVo, true);
            return goodsVo;
        }
    }
}
