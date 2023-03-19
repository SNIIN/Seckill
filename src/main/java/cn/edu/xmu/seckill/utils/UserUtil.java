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
            user.setUserId(1300000000L+i);
            user.setNickname("user"+i);
            user.setSalt("1251552222");
            user.setPassword(MD5Util.backToDb("12300aaa",user.getSalt()));
            users.add(user);
        }
        return users;
    }

    public static String getRandomNickname(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

    public static String getPayTime(){
        LocalDateTime nowDateTime = LocalDateTime.now();
        System.out.println(nowDateTime);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return nowDateTime.format(dateTimeFormatter);
    }

}
