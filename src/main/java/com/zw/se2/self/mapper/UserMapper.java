package com.zw.se2.self.mapper;

import com.zw.se2.self.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> findAll();

    @Select("select * from user where name = #{userName} and password=#{password}")
    User findByNameAndPsw(@Param("userName")String userName, @Param("password") String password);
    @Insert("insert into user() values()")
    void insert(User user);
}
