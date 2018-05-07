package com.zw.se2.self.ctrl;

import com.zw.se2.self.model.ProjectTarget;
import com.zw.se2.self.service.ProjectTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhaoe
 */
@RestController
@RequestMapping("target")
public class ProjectTargetController {
    private final ProjectTargetService service;
    @Autowired
    public ProjectTargetController(ProjectTargetService service) {
        this.service=service;
    }

    @GetMapping("/all")
    public List<ProjectTarget> findAll(){
        return service.findAll();
    }
}
