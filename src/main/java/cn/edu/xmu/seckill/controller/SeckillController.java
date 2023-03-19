package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.config.annotation.SeckillUser;
import cn.edu.xmu.seckill.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.seckill.entity.Order;
import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.service.IGoodsService;
import cn.edu.xmu.seckill.service.ISeckillService;
import cn.edu.xmu.seckill.utils.ReturnNo;
import cn.edu.xmu.seckill.utils.ReturnObject;
import cn.edu.xmu.seckill.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {

    private final ISeckillService seckillService;
    private final IGoodsService goodsService;
    private static final Logger logger = LoggerFactory.getLogger(SeckillController.class);
    @Autowired
    public SeckillController(ISeckillService seckillService, IGoodsService goodsService) {
        this.seckillService = seckillService;
        this.goodsService = goodsService;
    }
    /**
     * 接收一个秒杀id，如果检查合法，返回订单页面，否则报SeckillException异常
     * @param user
     * @param seckillId
     * @return
     */
    @GetMapping("")
    public String doSeckill(Model model, @SeckillUser User user, @RequestParam(value="seckillid") Long seckillId) {
        logger.info("seckilld为"+seckillId);
        logger.info("user为"+user);
        Order order = seckillService.doSeckill(user, seckillId);
        SeckillGoodsVo vo = goodsService.getOneSeckillGoods(seckillId);
        log.info(order.toString());
        model.addAttribute("goods", vo);
        model.addAttribute("order", order);
        model.addAttribute("userName", user.getNickname());
        model.addAttribute("orderNum", UserUtil.getRandomNickname(12));
        model.addAttribute("payTime",UserUtil.getPayTime());
        return "orderDetail";
    }

}
