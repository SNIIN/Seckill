package cn.edu.xmu.seckill.service.imp;

import cn.edu.xmu.seckill.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.seckill.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.seckill.exception.SeckillException;
import cn.edu.xmu.seckill.mapper.GoodsMapper;
import cn.edu.xmu.seckill.service.IGoodsService;
import cn.edu.xmu.seckill.utils.ReturnNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService implements IGoodsService {
    private final GoodsMapper goodsMapper;
    @Autowired
    public GoodsService(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    /**
     * 获取第page页，当前处于秒杀活动的商品列表
     * 一页最大商品数量默认10， page从1开始
     * @param page
     * @return
     */
    @Override
    public List<SeckillGoodsVo> getOnePageGoodsList(int page) {
        List<SeckillGoodsVo> result = goodsMapper.selectSeckillGoodsVo();
        return result;
    }

    @Override
    public SeckillGoodsVo getOneSeckillGoods(Long seckillId) {
        SeckillGoodsVo result = goodsMapper.selectSeckillGoodsVoById(seckillId);
        if (null == result) {
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NON);
        }
        return result;
    }
}
