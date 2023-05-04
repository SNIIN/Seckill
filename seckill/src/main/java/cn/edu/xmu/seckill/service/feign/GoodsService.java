package cn.edu.xmu.seckill.service.feign;

import cn.edu.xmu.core.controller.vo.SeckillGoodsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@FeignClient(name = "seckill-goods")
public interface GoodsService {
    @GetMapping("/goods/glist")
    List<SeckillGoodsVo> getOnePageGoodsList();
}
