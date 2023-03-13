package cn.edu.xmu.seckill.service;

import cn.edu.xmu.seckill.controller.vo.LoginVo;
import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.utils.ReturnObject;

public interface IUserService {


    /**
     * 登录功能
     * @param loginVo
     * @return
     */
    ReturnObject doLogin(LoginVo loginVo);


    /**
     *
     * @param userTicket
     * @return
     */
    User getUserByCookie(String userTicket);
}
