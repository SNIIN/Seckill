package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.config.annotation.SeckillUser;
import cn.edu.xmu.seckill.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.service.IGoodsService;
import cn.edu.xmu.seckill.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

    @Autowired
    RedisTemplate redisTemplate;
    private final IGoodsService goodsService;
    @Autowired
    public GoodsController(IGoodsService goodsService) {
        this.goodsService = goodsService;
    }
    @GetMapping(value = "/list", produces = "text/html;charset=UTF-8")
    public String goodsList(Model model,
                            @SeckillUser User user) {
        log.info(user.toString());
        List<SeckillGoodsVo> lst = goodsService.getOnePageGoodsList(1);
        model.addAttribute("goodsList", lst);
        model.addAttribute("userName", user.getNickname());
        return "index";
    }

    @GetMapping(value = "/detail", produces = "text/html;charset=UTF-8")
    public String goodsDetail(Model model, @RequestParam(value="seckillid") Long seckillId) {
        SeckillGoodsVo vo = goodsService.getOneSeckillGoods(seckillId);
        log.info(vo.toString());
        model.addAttribute("goods", vo);
        return "goodsDetail";
    }
}
