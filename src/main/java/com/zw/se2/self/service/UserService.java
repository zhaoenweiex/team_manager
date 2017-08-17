package com.zw.se2.self.service;

import com.zw.se2.self.model.User;

/**
 * Created by zhaoenwei on 2017/8/12.
 */
public interface UserService {
    User findByNameAndPsw(String userName, String password);

    User add(User user);
}
