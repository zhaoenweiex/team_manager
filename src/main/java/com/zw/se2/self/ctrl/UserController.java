package com.zw.se2.self.ctrl;

import com.zw.se2.self.model.User;
import com.zw.se2.self.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaoenwei on 2017/8/4.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody User user){
        String userName=user.getName();
        String password=user.getPassword();
        User userDB=userService.findByNameAndPsw(userName,password);
        return userDB;
    }
    @PostMapping
    public  User create(User user){
        return userService.add(user);
    }
}
