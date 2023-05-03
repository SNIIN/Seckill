package cn.edu.xmu.core;

import cn.edu.xmu.core.mapper.UserMapper;
import cn.edu.xmu.core.mapper.entity.User;
import cn.edu.xmu.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@Slf4j
class SeckillApplicationTests {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;


	void generateTestUsersForJmeter() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("..\\jmeter\\data.csv"));
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
}
