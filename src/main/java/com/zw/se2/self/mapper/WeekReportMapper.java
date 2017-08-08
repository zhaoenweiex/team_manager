package com.zw.se2.self.mapper;

import com.zw.se2.self.model.WeekReport;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
@Mapper
public interface WeekReportMapper {
    @Select( "select done_info,plan_info,problem_info from week_report where 1=1")
    @Results(value = {
            @Result(id = true, property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "doneInfo", column = "done_info", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "planInfo", column = "plan_info", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "problemInfo", column = "problem_info", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    List<WeekReport> findAll();
    @Insert("insert into week_report(create_time,user_id,done_info,plan_info,problem_info,project_info) values(#{createTime},#{userId},#{doneInfo},#{planInfo},#{problemInfo},#{projectInfo})")
    Boolean create(WeekReport weekReport);
    @Select("select * from week_report where user_id = #{userId}")
    List<WeekReport> findAllByUserId(Integer userId);
    @Select("select * from week_report where id in (#{ids})")
    List<WeekReport> findInIds(String[] ids);
}
