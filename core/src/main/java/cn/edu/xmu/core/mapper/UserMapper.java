package cn.edu.xmu.core.mapper;

import cn.edu.xmu.core.mapper.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Select("select u.id as userId, u.* from t_user u")
    List<User> selectAllUsers();
}