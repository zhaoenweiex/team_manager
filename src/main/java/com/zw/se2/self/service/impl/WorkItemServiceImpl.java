package com.zw.se2.self.service.impl;

import com.zw.se2.self.mapper.WorkItemMapper;
import com.zw.se2.self.model.WorkItem;
import com.zw.se2.self.service.WorkItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkItemServiceImpl implements WorkItemService {
    private final WorkItemMapper mapper;

    @Autowired
    public WorkItemServiceImpl(WorkItemMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void add(WorkItem workItem) {
        mapper.insert(workItem);
    }
}
