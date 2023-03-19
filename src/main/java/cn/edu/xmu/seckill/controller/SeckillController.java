package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.config.annotation.SeckillUser;
import cn.edu.xmu.seckill.entity.Order;
import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.service.ISeckillService;
import cn.edu.xmu.seckill.service.imp.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {

    private final ISeckillService seckillService;
    @Autowired
    public SeckillController(ISeckillService seckillService) {
        this.seckillService = seckillService;
    }
    /**
     * 接收一个秒杀id，如果检查合法，返回订单页面，否则报SeckillException异常
     * @param user
     * @param seckillId
     * @return
     */
    @PostMapping(value = "/{seckillId}", produces = "text/html;charset=UTF-8")
    public String doSeckill(Model model, @SeckillUser User user, @PathVariable Long seckillId) {
        Order order = seckillService.doSeckill(user, seckillId);
        log.info(order.toString());
        model.addAttribute("order", order);
        model.addAttribute("userName", user.getNickname());
        return "orderDetail";
    }
}
