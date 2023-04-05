package cn.edu.xmu.goods.controller;

import cn.edu.xmu.core.config.annotation.SeckillUser;
import cn.edu.xmu.core.exception.SeckillException;
import cn.edu.xmu.core.mapper.entity.User;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.goods.controller.vo.GoodsVo;
import cn.edu.xmu.goods.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.goods.service.GoodsService;
import cn.edu.xmu.core.utils.ReturnObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController {
    private final GoodsService goodsService;
    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping(value = "/glist", produces = "application/json;charset=UTF-8")
    public ReturnObject getlist(@SeckillUser User user) {
        List<SeckillGoodsVo> lst = goodsService.getOnePageGoodsList(1);
        return new ReturnObject(ReturnNo.SUCCESS,lst);
    }

    @GetMapping(value = "/gdetail", produces = "application/json;charset=UTF-8")
    public ReturnObject getdetail(@RequestParam(value="seckillid") Long seckillId) {
        SeckillGoodsVo vo = goodsService.getOneSeckillGoods(seckillId);
        return new ReturnObject(ReturnNo.SUCCESS,vo);
    }

    @GetMapping("")
    public SeckillGoodsVo getOneSeckillGoodsForUpdate(@RequestParam("seckillid") Long seckillId) {
        SeckillGoodsVo res = null;
        try {
            res = goodsService.getOneSeckillGoods(seckillId);
        }catch (Exception e) {
            log.warn("出现了错误");
            return null;
        }
        return res;
    }

    @GetMapping("/down")
    Boolean updateBySeckillStockAndSeckillId(@RequestParam("seckillid") Long seckillId) {
        try {
            return goodsService.updateBySeckillStockAndSeckillId(seckillId);
        }catch (Exception e) {
            log.warn("出现了错误");
            return true;
        }
    }

    @GetMapping("/list")
    List<SeckillGoodsVo> getOnePageGoodsList(@RequestParam("page") Integer page) {
        try {
            return goodsService.getOnePageGoodsList(1);
        }catch (Exception e) {
            log.warn("出现了错误");
            return null;
        }
    }

    @GetMapping("/one")
    GoodsVo getOneSeckillGoods(@RequestParam("goodsid") Long goodsId) {
        try {
            return goodsService.getGoodsVoById(goodsId);
        }catch (Exception e) {
            log.warn("出现了错误");
            return null;
        }
    }
}
