package com.zw.se2.self.mapper;

import com.zw.se2.self.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> findAll();
    @Select("select * from user  where (user_name) = (#{userName} and (password)=(#{password})")
    User findByNameAndPsw(String userName,String password);
}
