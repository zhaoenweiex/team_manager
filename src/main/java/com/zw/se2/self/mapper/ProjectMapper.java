package com.zw.se2.self.mapper;

import com.zw.se2.self.model.Project;
import com.zw.se2.self.model.WorkItem;
import com.zw.se2.self.utils.MyMapper;

import java.util.List;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
public interface ProjectMapper extends MyMapper<Project> {
    List<Project> findAll();

}
