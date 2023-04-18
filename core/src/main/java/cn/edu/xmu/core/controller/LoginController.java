package cn.edu.xmu.core.controller;

import cn.edu.xmu.core.controller.vo.LoginVo;
import cn.edu.xmu.core.service.UserService;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.core.utils.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/dologin", produces = "application/json;charset=UTF-8")
    public ReturnObject doLogin(@Valid @RequestBody LoginVo loginVo,
                                HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse) {
        logger.info(loginVo.toString());
        return userService.doLogin(loginVo, httpServletRequest, httpServletResponse);
    }

    @Value("${testValue}")
    private String testValue;
    @GetMapping(value = "/dotest", produces = "application/json;charset=UTF-8")
    public ReturnObject test() {
        return new ReturnObject(ReturnNo.SUCCESS, "", testValue);
    }
}
