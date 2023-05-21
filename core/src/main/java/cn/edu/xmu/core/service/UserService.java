package cn.edu.xmu.core.service;

import cn.edu.xmu.core.controller.vo.LoginVo;
import cn.edu.xmu.core.controller.vo.UserVo;
import cn.edu.xmu.core.exception.SeckillException;
import cn.edu.xmu.core.mapper.UserMapper;
import cn.edu.xmu.core.mapper.entity.User;
import cn.edu.xmu.core.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
@Service
@Slf4j
public class UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserMapper userMapper;
    private final RedisUtil redisUtil;
    @Autowired
    UserService(UserMapper userMapper, RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
        this.userMapper = userMapper;
    }


    public ReturnObject doLogin(LoginVo loginVo, HttpServletResponse httpServletResponse) {
        Long userId = Long.parseLong(loginVo.getMobile());
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user) {
            throw new SeckillException(ReturnNo.LOGIN_ERROR);
        }
        if (!MD5Util.backToDb(loginVo.getPassword(), user.getSalt()).equals(user.getPassword())) {
            throw new SeckillException(ReturnNo.LOGIN_ERROR);
        }
        // 生成登录凭证，存入cookie中
        String token = generateLoginToken();
        UserVo userVo = UserVo.builder().userId(user.getUserId()).head(user.getHead()).nickname(user.getNickname()).build();
        redisUtil.addAsKeyValue(userVo.RedisKey(token), userVo, true);
        CookieUtil.setCookieValue(httpServletResponse, "token", token);
        return new ReturnObject(ReturnNo.SUCCESS);
    }

    public UserVo getUserByCookie(String token) {
        UserVo user = (UserVo) redisUtil.getValueByKey(UserVo.RedisKey(token));
        return user;
    }

    /**
     * 仅用于jmter测试的登录，传入账号密码，登录返回token
     * @param userId
     * @param password
     * @return
     */
    public String loginForJmeter(Long userId, String password) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user) {
            throw new SeckillException(ReturnNo.LOGIN_ERROR);
        }
        if (!MD5Util.backToDb(password, user.getSalt()).equals(user.getPassword())) {
            throw new SeckillException(ReturnNo.LOGIN_ERROR);
        }
        String token = generateLoginToken();
        UserVo userVo = UserVo.builder().userId(user.getUserId()).head(user.getHead()).nickname(user.getNickname()).build();
        redisUtil.addAsKeyValue(userVo.RedisKey(token), userVo, true);
        return token;
    }

    public void loginsForJmeter()  {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/jmeter/data.csv"))) {
            List<User> lst = userMapper.selectAllUsers();
            writer.write("token");
            writer.newLine();
            // 默认数据库所有user明文密码为123456， 前端加盐方式相同
            lst.forEach(i -> {
                try {
                    writer.write(loginForJmeter(i.getUserId(), "90aeaad535749f0a6cb8d685e7493dd0"));
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
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
