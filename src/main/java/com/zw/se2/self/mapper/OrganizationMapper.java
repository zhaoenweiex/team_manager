package com.zw.se2.self.mapper;

import com.zw.se2.self.model.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
@Mapper
public interface OrganizationMapper {
    @Select("select * from organization")
    List<Organization> findAll();
}
