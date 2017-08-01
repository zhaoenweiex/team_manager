package com.zw.se2.self.mapper;

import com.zw.se2.self.model.WeekReport;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
@Mapper
public interface WeekReportMapper {
    @Select("select * from week_report")
    List<WeekReport> findAll();
    @Insert("insert into week_report(user_id,done_info,plan_info,problem_info) values(#{userId},#{doneInfo},#{planInfo},#{problemInfo})")
    Boolean create(WeekReport weekReport);
    @Select("select * from week_report where (user_id) = (#{userId})")
    List<WeekReport> findAllByUserId(Integer userId);
    @Select("select * from week_report where id in (#{ids})")
    List<WeekReport> findInIds(String[] ids);
}
