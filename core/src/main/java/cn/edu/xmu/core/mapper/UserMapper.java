package cn.edu.xmu.core.mapper;

import cn.edu.xmu.core.mapper.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    int insert(User record);

    User selectByPrimaryKey(Long userId);

    @Select("select u.id as userId, u.* from t_user u")
    List<User> selectAllUsers();
}