package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.config.annotation.SeckillUser;
import cn.edu.xmu.seckill.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.service.IGoodsService;
import cn.edu.xmu.seckill.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.Thymeleaf;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;
    private final IGoodsService goodsService;
    @Autowired
    public GoodsController(IGoodsService goodsService) {
        this.goodsService = goodsService;
    }
    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    public String goodsList(Model model, @SeckillUser User user, HttpServletRequest request,
                            HttpServletResponse response) {
        log.info(user.toString());
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String)valueOperations.get("index");
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        List<SeckillGoodsVo> lst = goodsService.getOnePageGoodsList(1);
        model.addAttribute("goodsList", lst);
        model.addAttribute("userName", user.getNickname());
        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("index", webContext);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("index", html, 60, TimeUnit.SECONDS);//TODO:存在缓存击穿问题
       }else {
            return "index";
        }
        return html;
    }

    @GetMapping(value = "/detail", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String goodsDetail(Model model, @RequestParam(value="seckillid") Long seckillId,
                              HttpServletRequest request, HttpServletResponse response) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String)valueOperations.get("goodsDetail"+seckillId);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        SeckillGoodsVo vo = goodsService.getOneSeckillGoods(seckillId);
        log.info(vo.toString());
        model.addAttribute("goods", vo);
        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsDetail"+seckillId, html, 60, TimeUnit.SECONDS);//TODO:存在缓存击穿问题
        }else {
            return "goodsDetail";
        }
        return html;
    }
}
