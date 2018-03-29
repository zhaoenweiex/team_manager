package com.zw.se2.self.mapper;

import com.zw.se2.self.model.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
public interface OrganizationMapper {
    @Select("select * from organization")
    List<Organization> findAll();
    @Select("select * from organization where id = #{id}")
    Organization findById(@Param("id") Long id);
}
