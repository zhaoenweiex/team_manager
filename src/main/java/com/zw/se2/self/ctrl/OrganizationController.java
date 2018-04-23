package com.zw.se2.self.ctrl;

import com.zw.se2.self.model.Organization;
import com.zw.se2.self.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("org")
public class OrganizationController {
    private final OrgService orgService;

    @Autowired
    public OrganizationController(OrgService orgService) {
        this.orgService = orgService;
    }

    @PostMapping
    public Organization create(Organization organization){
        if(orgService.add(organization))
            return organization;
        else
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping
    public boolean delete(Long id){
        if(orgService.delete(id))
            return true;
        else
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
