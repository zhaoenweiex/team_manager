package com.zw.se2.self.service.impl;

import com.zw.se2.self.mapper.UserMapper;
import com.zw.se2.self.mapper.WeekReportMapper;
import com.zw.se2.self.model.User;
import com.zw.se2.self.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoenwei on 2017/8/12.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByNameAndPsw(String userName, String password) {
        return userMapper.findByNameAndPsw(userName,password);
    }

    @Override
    public User add(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    public int countByOrgId(String orgId) {
        return userMapper.countByOrgId(Long.parseLong(orgId));
    }
}
