package cn.edu.xmu.seckill;

import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.mapper.UserMapper;
import cn.edu.xmu.seckill.service.IUserService;
import cn.edu.xmu.seckill.service.imp.UserService;
import cn.edu.xmu.seckill.utils.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
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

//    @Test
    void generateTestUsersForJmeter() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(".\\jmeter\\data.csv"));
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

    //@Test
    List<User> addUser(int num){
        List<User> users = UserUtil.creater(num);
        for (User user:users) {
            userMapper.insert(user);
        }
        return users;
    }

   // @Test
    void generateUserText() throws IOException {
        List<User> users =  addUser(10000);
        String path = "C:\\Users\\fly\\IdeaProjects\\Seckill\\user.txt";
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path,true)));
        for (User user:users) {
            String info = user.getUserId()+",09c1a241041b801e2aa25c1b3b615297";
            out.write(info);
            out.write("\n");
        }
        out.close();
    }

    @Test
    void generateTokenText() throws IOException {
        List<User> users = userMapper.selectAllUsers();
        String path = "C:\\Users\\fly\\IdeaProjects\\Seckill\\token.txt";
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path,true)));
        for (User user:users) {
            String info =  userService.loginForJmeter(user.getUserId(), "09c1a241041b801e2aa25c1b3b615297");
            out.write("token,"+info);
            out.write("\n");
        }
        out.close();
    }


}
