package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

    @Autowired
    RedisTemplate redisTemplate;
    @GetMapping("/list")
    public String goodsList(Model model,
                            HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse) {
        String token = CookieUtil.getCookieValue(httpServletRequest,"token");
        User user = (User) redisTemplate.opsForValue().get(User.getRedisKey(token));
        log.info(user.toString());
        model.addAttribute("userName", user.getNickname());
        return "goodsList";
    }
}
