package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.rabbitMq.MQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MQSender mqSender;

//  @GetMapping("/mq")
//  @ResponseBody
//  public String mqtest(){
//      mqSender.send("这是mqsend的测试");
//      return "success";
//  }
}
