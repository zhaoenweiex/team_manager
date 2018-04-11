package com.zw.se2.self.service.impl;

import com.zw.se2.self.mapper.ProjectMapper;
import com.zw.se2.self.mapper.ProjectTargetMapper;
import com.zw.se2.self.model.ProjectTarget;
import com.zw.se2.self.service.ProjectTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTargetServiceImpl implements ProjectTargetService {
    private final ProjectTargetMapper targetMapper;

    @Autowired
    public ProjectTargetServiceImpl(ProjectTargetMapper targetMapper) {
        this.targetMapper = targetMapper;
    }

    @Override
    public List<ProjectTarget> findAll() {
        return targetMapper.selectAll();
    }
}
