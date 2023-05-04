package cn.edu.xmu.order.service.feign;

import cn.edu.xmu.order.controller.vo.GoodsVo;
import cn.edu.xmu.core.controller.vo.SeckillGoodsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "seckill-goods")
public interface GoodsService {
    @GetMapping("/goods/goods")
    GoodsVo getOneGoods(@RequestParam("goodsid") Long goodsId);
    @GetMapping("/goods/seckill")
    SeckillGoodsVo getOneSeckillGoods(@RequestParam("seckillid") Long seckillId);

    /**
     * 当库存充足时候，扣减库存，并返回false，否则返回true
     * @param seckillId
     * @return
     */
    @GetMapping("/goods/down")
    @Transactional
    Boolean downSeckillsCount(@RequestParam("seckillid") Long seckillId);
}
