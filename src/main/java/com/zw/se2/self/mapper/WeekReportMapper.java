package com.zw.se2.self.mapper;

import com.zw.se2.self.model.WeekReport;
import com.zw.se2.self.utils.MyMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
public interface WeekReportMapper  extends MyMapper<WeekReport> {
    @Insert("insert into week_report(create_time,user_id,done_info,plan_info,problem_info,project_info) values(#{createTime},#{userId},#{doneInfo},#{planInfo},#{problemInfo},#{projectInfo})")
    Boolean create(WeekReport weekReport);
    @Select("select * from week_report where id in (${ids})")
    @Results(value = {
            @Result(id = true, property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "doneInfo", column = "done_info", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "planInfo", column = "plan_info", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "problemInfo", column = "problem_info", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "userId", column = "user_id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "projectInfo", column = "project_info", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "offWorkInfo", column = "off_work_info", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "bussinessOutInfo", column = "bussiness_out_info", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "overtimeInfo", column = "overtime_info", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "userName", column = "user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
    })
    List<WeekReport> findInId(@Param("ids") String ids);
    @Select( "select done_info,plan_info,problem_info from week_report where 1=1 and org_id =#{orgId}")
    @Results(value = {
            @Result(id = true, property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "doneInfo", column = "done_info", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "planInfo", column = "plan_info", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "problemInfo", column = "problem_info", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "userName", column = "user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
    })
    List<WeekReport> findByOrgId(WeekReport weekReport);
    @Select( "select * from week_report where 1=1 and user_id=#{userId} sort by create_time desc")
    List<WeekReport> findByUserId(String userId);
}
