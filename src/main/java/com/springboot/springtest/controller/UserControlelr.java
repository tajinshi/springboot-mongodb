package com.springboot.springtest.controller;

import com.springboot.springtest.bean.User;
import com.springboot.springtest.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "api/user")
@Api(description = "用户测试mongodb")
public class UserControlelr {
    @Autowired
    private UserService userService;

    @GetMapping("getUserList")
    public User getUserList(String name){
        return  userService.getUserList(name);
    }

    @GetMapping("addUser")
    public Map<String,Object> insertUser(){
        User user = new User();
        user.setId(1l);
        user.setName("小明");
        user.setAddress("陕西西安");
        user.setAge(23);
        userService.insertUser(user);
        return  null;
    }

    @GetMapping("updateUser")
    public Map<String,Object> updateUser(){
        User user = new User();
        user.setId(1l);
        user.setName("小明2");
        user.setAddress("陕西西安2");
        user.setAge(232);
        userService.updateUser(user);
        return  null;
    }

    @GetMapping("delUser")
    public Map<String,Object> delUser(){
        userService.delUser(1l);
        return  null;
    }
}
