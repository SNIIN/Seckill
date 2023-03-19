package cn.edu.xmu.seckill;

import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.mapper.UserMapper;
import cn.edu.xmu.seckill.service.imp.UserService;
import cn.edu.xmu.seckill.utils.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SeckillApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
    }

    @Test
    void addUser(){
        List<User> users = UserUtil.creater(100);
        for (User user:users) {
            userMapper.insert(user);
        }
    }

}
