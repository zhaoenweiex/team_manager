package com.zw.se2.self.mapper;

import com.zw.se2.self.model.WeekReport;
import com.zw.se2.self.model.WorkItem;
import com.zw.se2.self.utils.MyMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
public interface WorkItemMapper extends MyMapper<WorkItem> {
}
