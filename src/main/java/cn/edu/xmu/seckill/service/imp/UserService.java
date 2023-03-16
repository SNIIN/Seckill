package cn.edu.xmu.seckill.service.imp;

import cn.edu.xmu.seckill.controller.vo.LoginVo;
import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.exception.SeckillException;
import cn.edu.xmu.seckill.mapper.UserMapper;
import cn.edu.xmu.seckill.service.IUserService;
import cn.edu.xmu.seckill.utils.CookieUtil;
import cn.edu.xmu.seckill.utils.MD5Util;
import cn.edu.xmu.seckill.utils.ReturnNo;
import cn.edu.xmu.seckill.utils.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserMapper userMapper;
    private final RedisTemplate redisTemplate;
    @Autowired
    UserService(UserMapper userMapper, RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
    }

    @Override
    public ReturnObject doLogin(LoginVo loginVo, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Long userId = Long.parseLong(loginVo.getMobile());
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user) {
            throw new SeckillException(ReturnNo.LOGIN_ERROR);
        }
        logger.info(user.toString());
        logger.info(String.format("MD5Util.backToDb(loginVo.getPassword(), user.getSalt()): %s", MD5Util.backToDb(loginVo.getPassword(), user.getSalt())));
        if (!MD5Util.backToDb(loginVo.getPassword(), user.getSalt()).equals(user.getPassword())) {
            throw new SeckillException(ReturnNo.LOGIN_ERROR);
        }
        // 生成登录凭证，存入cookie中
        String token = generateLoginToken();
        redisTemplate.opsForValue().set(user.getRedisKey(token), user);
        CookieUtil.setCookieValue(httpServletRequest, httpServletResponse, "token", token);
        return new ReturnObject(ReturnNo.SUCCESS);
    }

    @Override
    public User getUserByCookie(String token) {
        User user = (User) redisTemplate.opsForValue().get(User.getRedisKey(token));
        return user;
    }

    private static final int TOKEN_LENGTH = 32; // 登录凭证长度为32个字符

    // 生成一个随机的登录凭证
    private static String generateLoginToken() {
        // 生成32字节的随机数据
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        random.nextBytes(tokenBytes);
        String token;
        try {
            // 对随机数据进行SHA-256哈希
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(tokenBytes);

            // 对哈希结果进行Base64编码，得到登录凭证
            token = Base64.getEncoder().encodeToString(hashBytes);
        }catch(NoSuchAlgorithmException e) {
            token = tokenBytes.toString();
        }
        return token;
    }
}
