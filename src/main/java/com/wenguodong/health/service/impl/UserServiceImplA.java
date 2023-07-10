package com.wenguodong.health.service.impl;

import com.wenguodong.health.dao.UserDao;
import com.wenguodong.health.entity.Result;
import com.wenguodong.health.pojo.User;
import com.wenguodong.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/*
 *@Time：2023/7/8
 *@Author：Jelly
 */
@Primary //主要的
@Service
public class UserServiceImplA implements UserService {
    //业务层依赖DAO
    @Autowired
    public UserDao userDao;

    @Override
    public Result register(User registerUser) {
        //1. 查看用户名是否被注册
        User dbUser = userDao.findByUsername(registerUser.getUsername());
        if (dbUser != null) {

            return new Result(false, "注册失败，用户名已经存在");
        }
        //2. 查看手机号是否被注册
        dbUser = userDao.findByTelephone(registerUser.getTelephone());
        if (dbUser != null) {

            return new Result(false, "注册失败，手机号已经存在");
        }
        //3. 给密码加密
        String password = registerUser.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String newPassword = encoder.encode(password);
        //将新密码赋值给registerUser
        registerUser.setPassword(newPassword);
        //4. 添加到数据库中
        userDao.register(registerUser);
        return new Result(true,"注册成功!");
    }

    @Override
    public Result login(User loginUser) {
        //1. 调用DAO通过用户名查询用户
        User byUsername = userDao.findByUsername(loginUser.getUsername());
        if(byUsername == null){
            return new Result(false,"登录失败，用户名不存在!");
        }
        //2. 用户名找到用户，对比密码
        String password = loginUser.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(password, byUsername.getPassword());
        if(matches) {
            //密码匹配
            return new Result(true,"登录成功!");
        }else {
            //密码不匹配
            return new Result(false,"登录失败，密码错误!");
        }
    }

    //手机号查询用户
    @Override
    public User findByTelephone(String telephone) {
        User dbUser = userDao.findByTelephone(telephone);
        return dbUser;
    }
}
