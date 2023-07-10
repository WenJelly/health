package com.wenguodong.health.dao;

import com.wenguodong.health.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/*
 *@Time：2023/7/8
 *@Author：Jelly
 */
@Mapper  //会使用动态代理生成接口的实现类对象，并把这个实现类对象放到容器中
public interface UserDao {

    //根据用户名查询用户
    @Select("select * from t_user where username = #{username}")
    User findByUsername(String username);
    //根据手机号查询用户
    @Select("select * from t_user where telephone = #{telephone}")
    User findByTelephone(String telephone);
    //添加用户到数据库
    @Insert("insert into t_user(username,telephone,password) values (#{username},#{telephone},#{password})")
    void register(User registerUser);
}
