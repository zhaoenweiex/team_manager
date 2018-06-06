package com.zw.se2.self.ctrl;

import com.github.pagehelper.PageInfo;
import com.zw.se2.self.model.WorkItem;
import com.zw.se2.self.service.WorkItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoe
 */
@RestController
@RequestMapping("workItem")
public class WorkItemController {
    private final WorkItemService service;

    @Autowired
    public WorkItemController(WorkItemService service) {
        this.service = service;
    }

    @PostMapping
    public PageInfo<WorkItem> createWorkItem(WorkItem workItem){
        List<WorkItem> resultList=new ArrayList<>();
        workItem.setCreateTime(System.currentTimeMillis());
        service.add(workItem);
        //处理增加工作项的情况
        resultList.add(workItem);
        return new PageInfo<>(resultList);
    }
    @DeleteMapping
    public PageInfo<WorkItem> deleteWorkItem(WorkItem workItem){
        List<WorkItem> resultList=new ArrayList<>();
        //处理减少工作项的情况
        return new PageInfo<>(resultList);
    }
}
