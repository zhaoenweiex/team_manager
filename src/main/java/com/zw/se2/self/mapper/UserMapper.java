package com.zw.se2.self.mapper;

import com.zw.se2.self.model.User;
import com.zw.se2.self.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
public interface UserMapper extends MyMapper<User> {
    @Select("count user where orgId = #{orgId}")
    int countByOrgId(@Param("orgId")long orgId);
}
