package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.controller.vo.LoginVo;
import cn.edu.xmu.seckill.service.IUserService;
import cn.edu.xmu.seckill.service.imp.UserService;
import cn.edu.xmu.seckill.utils.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import cn.edu.xmu.seckill.service.imp.UserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final IUserService userService;

    @Autowired
    public LoginController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", produces = "text/html;charset=UTF-8")
    @ResponseStatus(HttpStatus.CREATED)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/test")
    public String test(Model model) {
        return "index";
    }

    @PostMapping(value = "/dologin", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ReturnObject doLogin(@Valid @RequestBody LoginVo loginVo,
                                HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse) {
        logger.info(loginVo.toString());
        return userService.doLogin(loginVo, httpServletRequest, httpServletResponse);
    }


}
