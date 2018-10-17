package com.springboot.springtest.service;

import com.springboot.springtest.bean.User;

import java.util.List;

public interface UserService {
    int insertUser(User user);
    int updateUser(User user);
    int delUser(Long id);
    User getUserList(String name);
}
