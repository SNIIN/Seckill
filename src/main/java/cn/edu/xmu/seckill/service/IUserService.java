package cn.edu.xmu.seckill.service;

import cn.edu.xmu.seckill.controller.vo.LoginVo;
import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.utils.ReturnObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService {


    /**
     * 登录功能
     *
     * @param loginVo
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    ReturnObject doLogin(LoginVo loginVo, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);


    /**
     *
     * @param userTicket
     * @return
     */
    User getUserByCookie(String userTicket);
}
