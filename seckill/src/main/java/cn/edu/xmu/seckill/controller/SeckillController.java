package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.core.config.annotation.SeckillUser;
import cn.edu.xmu.core.controller.vo.UserVo;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.core.utils.ReturnObject;
import cn.edu.xmu.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {
    private final SeckillService seckillService;
    @Autowired
    public SeckillController(SeckillService seckillService) {
        this.seckillService = seckillService;
    }
    /**
     * 接收一个秒杀id，如果检查合法，返回订单页面，否则报SeckillException异常
     * @param user
     * @param seckillId
     * @return
     */
    @PostMapping(value = "/{seckillid}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ReturnObject doSeckill(@SeckillUser UserVo user, @PathVariable("seckillid") Long seckillId, HttpServletRequest httpServletRequest) {
        log.info("秒杀请求: {}，{}",  user, seckillId);
        seckillService.doSeckill(user, seckillId);
        return new ReturnObject(ReturnNo.SECKILL_GOODS_IN_QUEUE, "", "排队中...");
    }

//    @GetMapping(value = "/so", produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public ReturnObject doSeckill() {
//        return new ReturnObject(ReturnNo.SECKILL_GOODS_IN_QUEUE, "", "排队中...");
//    }

}
