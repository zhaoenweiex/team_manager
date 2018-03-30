package com.zw.se2.self.service.impl;

import com.zw.se2.self.mapper.OrganizationMapper;
import com.zw.se2.self.model.Organization;
import com.zw.se2.self.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrgServiceImpl implements OrgService {
    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public Map<String, String> generateBasicInfo(String orgId) {
        Organization organization = organizationMapper.selectByPrimaryKey(Long.parseLong(orgId));
        Map result = new HashMap();
        if (!StringUtils.isEmpty(organization.getName()))
            result.put("orgName", organization.getName());
        result.put("orgId", organization.getId());
        return result;
    }
}
