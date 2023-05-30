package cn.edu.xmu.core.controller;

import cn.edu.xmu.core.controller.vo.LoginVo;
import cn.edu.xmu.core.service.UserService;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.core.utils.ReturnObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                                HttpServletResponse httpServletResponse) throws JsonProcessingException {
        logger.info(loginVo.toString());
        ReturnObject returnObject = userService.doLogin(loginVo, httpServletResponse);
        return returnObject;
    }

    @PostMapping(value = "/dotest", produces = "application/json;charset=UTF-8")
    public ReturnObject test() {
        userService.loginsForJmeter();
        return new ReturnObject(ReturnNo.SUCCESS, "", "");
    }
}
