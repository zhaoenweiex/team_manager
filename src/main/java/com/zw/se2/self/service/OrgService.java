package com.zw.se2.self.service;

import com.zw.se2.self.model.Organization;

import java.util.Map;

public interface OrgService {
    Map<String,String> generateBasicInfo(String orgId);

    boolean add(Organization organization);

    boolean delete(Long id);
}
