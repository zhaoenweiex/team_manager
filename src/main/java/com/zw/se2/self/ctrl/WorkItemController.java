package com.zw.se2.self.ctrl;

import com.github.pagehelper.PageInfo;
import com.zw.se2.self.model.WorkItem;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("workItem")
public class WorkItemController {
    @PostMapping
    public PageInfo<WorkItem> createWorkItem(WorkItem workItem){
        List<WorkItem> resultList=new ArrayList<>();
        //处理增加工作项的情况
        resultList.add(workItem);
        return new PageInfo<>(resultList);
    }
    @PostMapping
    public PageInfo<WorkItem> deleteWorkItem(WorkItem workItem){
        List<WorkItem> resultList=new ArrayList<>();
        //处理减少工作项的情况
        return new PageInfo<>(resultList);
    }
}
