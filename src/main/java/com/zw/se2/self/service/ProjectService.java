package com.zw.se2.self.service;

import com.github.pagehelper.PageInfo;
import com.zw.se2.self.model.Project;

public interface ProjectService {
    PageInfo<Project> findALl();
}
