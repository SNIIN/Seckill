package cn.edu.xmu.seckill.service.imp;

import cn.edu.xmu.seckill.controller.vo.LoginVo;
import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.exception.SeckillException;
import cn.edu.xmu.seckill.mapper.UserMapper;
import cn.edu.xmu.seckill.service.IUserService;
import cn.edu.xmu.seckill.utils.MD5Util;
import cn.edu.xmu.seckill.utils.ReturnNo;
import cn.edu.xmu.seckill.utils.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserMapper userMapper;

    @Autowired
    UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public ReturnObject doLogin(LoginVo loginVo) {
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
        return new ReturnObject(ReturnNo.SUCCESS);
    }

    @Override
    public User getUserByCookie(String userTicket) {
        return null;
    }
}
