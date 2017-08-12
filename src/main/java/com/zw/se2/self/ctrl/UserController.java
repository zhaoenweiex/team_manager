package com.zw.se2.self.ctrl;

import com.zw.se2.self.model.User;
import com.zw.se2.self.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaoenwei on 2017/8/4.
 */
@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("login")
    public User login(String userName,String password){
        return userService.findByNameAndPsw(userName,password);
    }
}
