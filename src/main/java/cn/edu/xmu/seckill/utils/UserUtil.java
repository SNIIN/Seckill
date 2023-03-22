package cn.edu.xmu.seckill.utils;

import cn.edu.xmu.seckill.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserUtil {
    public static List<User> creater(int count){
        List<User> users = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setUserId(13000000000L+i);
            user.setNickname("user"+i);
            user.setSalt("1251552222");
            user.setPassword("fa71e2d73df73b4c18f85d3c6d302a71");//密码为12300aaa的md5加密
            users.add(user);
        }
        return users;
    }


}
