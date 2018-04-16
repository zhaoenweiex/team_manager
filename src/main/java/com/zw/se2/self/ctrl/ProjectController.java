package com.zw.se2.self.ctrl;

import com.github.pagehelper.PageInfo;
import com.zw.se2.self.model.Project;
import com.zw.se2.self.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController {
    private final ProjectService service;

    @Autowired
    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public PageInfo findAll(){
        List<Project> projectList=service.findALl();
        return new PageInfo<>(projectList);
    }
}
