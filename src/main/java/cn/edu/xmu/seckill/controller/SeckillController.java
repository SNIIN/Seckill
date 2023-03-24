package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.config.annotation.SeckillUser;
import cn.edu.xmu.seckill.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.seckill.controller.vo.SeckillOrderVo;
import cn.edu.xmu.seckill.entity.Order;
import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.rabbitMq.MQSender;
import cn.edu.xmu.seckill.service.IGoodsService;
import cn.edu.xmu.seckill.service.ISeckillService;
import cn.edu.xmu.seckill.utils.ReturnNo;
import cn.edu.xmu.seckill.utils.ReturnObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping(value = "/{seckillId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ReturnObject doSeckill(@SeckillUser User user, @PathVariable("seckillId") Long seckillId) {
        return seckillService.doSeckill(user, seckillId);
    }
    @GetMapping (value = "/result/{seckillId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ReturnObject getResult( @SeckillUser User user,@PathVariable(value="seckillId") Long seckillId) {
        Long status = seckillService.getOrderStatus(user, seckillId);
        return new ReturnObject(ReturnNo.SUCCESS,status);
    }

    @GetMapping(value = "/order/{orderId}", produces = "text/html;charset=UTF-8")
    public String getOrder(Model model, @SeckillUser User user, @PathVariable("orderId") Long orderId) {
        SeckillOrderVo order = seckillService.getSeckillOrder(orderId);
        log.info(order.toString());
        model.addAttribute("order", order);
        return "orderDetail";
    }
    @GetMapping (value = "/gorder", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ReturnObject getmyOrder( @SeckillUser User user,@RequestParam(value="orderId") Long orderId) {
        SeckillOrderVo order = seckillService.getSeckillOrder(orderId);
        log.info(order.toString());
        return new ReturnObject(ReturnNo.SUCCESS,order);
    }
}
