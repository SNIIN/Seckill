package cn.edu.xmu.seckill.service.feign;

import cn.edu.xmu.seckill.controller.vo.SeckillGoodsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(name = "seckill-goods")
public interface GoodsService {
    @GetMapping("/goods/list")
    List<SeckillGoodsVo> getOnePageGoodsList(@RequestParam("page") Integer page);
}
