package cn.edu.xmu.seckill.service;

import cn.edu.xmu.seckill.controller.vo.SeckillGoodsVo;

import java.util.List;

public interface IGoodsService {
    public List<SeckillGoodsVo> getOnePageGoodsList(int page);
}
