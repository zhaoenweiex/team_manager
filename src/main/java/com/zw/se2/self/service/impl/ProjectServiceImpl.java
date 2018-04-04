package com.zw.se2.self.service.impl;

import com.zw.se2.self.mapper.ProjectMapper;
import com.zw.se2.self.model.Project;
import com.zw.se2.self.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Override
    public List<Project> findALl() {
        return projectMapper.selectAll();
    }
}
