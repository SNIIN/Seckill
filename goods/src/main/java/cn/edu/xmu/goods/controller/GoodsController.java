package cn.edu.xmu.goods.controller;

import cn.edu.xmu.core.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.core.utils.ReturnObject;
import cn.edu.xmu.goods.controller.vo.GoodsVo;
import cn.edu.xmu.goods.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * pageNum = -1的时候不使用分页，返回全部数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/glist", produces = "application/json;charset=UTF-8")
    public ReturnObject getlist(@RequestParam(defaultValue = "-1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("{}， {}", pageNum, pageSize);
        List<SeckillGoodsVo> lst = goodsService.getOnePageGoodsList(pageNum, pageSize);
        return new ReturnObject(ReturnNo.SUCCESS,lst);
    }

    @GetMapping(value = "/gdetail", produces = "application/json;charset=UTF-8")
    public ReturnObject getdetail(@RequestParam(value="seckillid") Long seckillId) {
        SeckillGoodsVo vo = goodsService.getOneSeckillGoods(seckillId);
        return new ReturnObject(ReturnNo.SUCCESS,vo);
    }

    @GetMapping(value="/seckill", produces = "application/json;charset=UTF-8")
    public SeckillGoodsVo getOneSeckillGoods(@RequestParam("seckillid") Long seckillId) {
        SeckillGoodsVo res = null;
        try {
            res = goodsService.getOneSeckillGoods(seckillId);
        }catch (Exception e) {
            log.warn("出现了错误");
            return null;
        }
        return res;
    }

    /**
     * 扣减失败返回true，扣减成功返回false
     * @param seckillId
     * @return
     */
    @GetMapping("/down")
    Boolean downSeckillsCount(@RequestParam("seckillid") Long seckillId) {
//        try {
            return goodsService.updateBySeckillStockAndSeckillId(seckillId);
//        }catch (Exception e) {
//            log.warn("出现了错误");
//            return true;
//        }
    }


    @GetMapping(value = "/list", produces = "application/json;charset=UTF-8")
    public List<SeckillGoodsVo> getlistforseckill(@RequestParam(defaultValue = "-1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return goodsService.getOnePageGoodsList(pageNum, pageSize);
    }

    @GetMapping(value="/goods", produces = "application/json;charset=UTF-8")
    GoodsVo getOneGoods(@RequestParam("goodsid") Long goodsId) {
//        try {
            return goodsService.getGoodsVoById(goodsId);
//        }catch (Exception e) {
//            log.warn("出现了错误");
//            return null;
//        }
    }
}
