package com.zw.se2.self.mapper;

import com.zw.se2.self.model.Organization;
import com.zw.se2.self.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * @author zhaoenwei
 * @date 2017/7/27
 */
public interface OrganizationMapper extends MyMapper<Organization> {
}
