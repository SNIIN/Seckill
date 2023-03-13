package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.controller.vo.LoginVo;
import cn.edu.xmu.seckill.service.imp.UserService;
import cn.edu.xmu.seckill.utils.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import cn.edu.xmu.seckill.service.imp.UserService;

@Controller
@RequestMapping("")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("name", "word");
        return "login";
    }

    @PostMapping("/dologin")
    @ResponseBody
    public ReturnObject doLogin(@Valid @RequestBody LoginVo loginVo) {
        logger.info(loginVo.toString());
        return userService.doLogin(loginVo);
    }
}
