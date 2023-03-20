package cn.edu.xmu.seckill;

import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.mapper.UserMapper;
import cn.edu.xmu.seckill.service.IUserService;
import cn.edu.xmu.seckill.service.imp.UserService;
import cn.edu.xmu.seckill.utils.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class SeckillApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Test
    void contextLoads() {
    }

    @Test
    void generateTestUsersForJmeter() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv"));
        List<User> lst = userMapper.selectAllUsers();
        writer.write("token");
        writer.newLine();
        // 默认数据库所有user明文密码为123456， 前端加盐方式相同
        lst.forEach(i -> {
            try {
                writer.write(userService.loginForJmeter(i.getUserId(), "90aeaad535749f0a6cb8d685e7493dd0"));
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.close();
    }

    @Test
    void addUser(){
        List<User> users = UserUtil.creater(100);
        for (User user:users) {
            userMapper.insert(user);
        }
    }

}
