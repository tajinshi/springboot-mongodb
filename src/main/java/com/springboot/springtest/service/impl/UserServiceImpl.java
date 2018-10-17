package com.springboot.springtest.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.springboot.springtest.bean.User;
import com.springboot.springtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public int insertUser(User user) {
        mongoTemplate.insert(user);
        return 0;
    }

    @Override
    public int updateUser(User user) {
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("address",user.getAddress()).set("age",user.getAge()).set("name",user.getName());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);

        return 0;
    }

    @Override
    public int delUser(Long id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,User.class);
        return 0;
    }

    @Override
    public User getUserList(String name) {
        Query query=new Query(Criteria.where("name").is(name));
        User user = mongoTemplate.findOne(query, User.class);
        return user;
    }
}
