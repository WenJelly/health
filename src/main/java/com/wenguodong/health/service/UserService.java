package com.wenguodong.health.service;

/*A
 *@Time：2023/7/8
 *@Author：Jelly
 */

import com.wenguodong.health.entity.Result;
import com.wenguodong.health.pojo.User;

//业务层接口
public interface UserService {
    Result register(User registerUser);

    Result login(User loginUser);

    User findByTelephone(String telephone);
}
