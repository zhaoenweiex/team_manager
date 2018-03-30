package com.zw.se2.self.mapper;

import com.zw.se2.self.model.Organization;
import com.zw.se2.self.model.User;
import com.zw.se2.self.utils.MyMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
public interface UserMapper extends MyMapper<User> {
    @Select("select * from user where name = #{userName} and password=#{password}")
    User findByNameAndPsw(@Param("userName")String userName, @Param("password") String password);
    @Select("count user where orgId = #{orgId}")
    int countByOrgId(@Param("orgId")long orgId);
}
