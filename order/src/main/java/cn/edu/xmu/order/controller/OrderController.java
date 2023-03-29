package cn.edu.xmu.order.controller;

import cn.edu.xmu.core.config.annotation.SeckillUser;
import cn.edu.xmu.core.exception.SeckillException;
import cn.edu.xmu.core.mapper.entity.User;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.core.utils.ReturnObject;
import cn.edu.xmu.order.service.OrderService;
import cn.edu.xmu.order.controller.vo.SeckillOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    
    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping (value = "/gorder", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ReturnObject getmyOrder(@SeckillUser User user, @RequestParam(value="orderId") Long orderId) {
        SeckillOrderVo order = orderService.getSeckillOrder(orderId);
        log.info(order.toString());
        return new ReturnObject(ReturnNo.SUCCESS,order);
    }

    @GetMapping(value="/result/{seckillid}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ReturnObject getResult(@SeckillUser User user, @PathVariable("seckillid") Long seckillId) {
        Long res = orderService.getOrderByUserIdAndSeckillId(user.getUserId(), seckillId);
        return new ReturnObject(ReturnNo.SUCCESS,"",res);
    }
}
