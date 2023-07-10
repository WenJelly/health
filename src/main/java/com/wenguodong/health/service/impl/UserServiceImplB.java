package com.wenguodong.health.service.impl;

import com.wenguodong.health.dao.UserDao;
import com.wenguodong.health.entity.Result;
import com.wenguodong.health.pojo.User;
import com.wenguodong.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 *@Time：2023/7/8
 *@Author：Jelly
 */
@Service
public class UserServiceImplB implements UserService {

    @Autowired
    public UserDao userDao;

    @Override
    public Result register(User registerUser) {
        return null;
    }

    @Override
    public Result login(User loginUser) {
        return null;
    }

    @Override
    public User findByTelephone(String telephone) {
        return null;
    }
}
