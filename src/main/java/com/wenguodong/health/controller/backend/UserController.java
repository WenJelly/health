package com.wenguodong.health.controller.backend;

import com.wenguodong.health.constant.MessageConstant;
import com.wenguodong.health.constant.RedisMessageConstant;
import com.wenguodong.health.entity.Result;
import com.wenguodong.health.pojo.User;
import com.wenguodong.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
 *@Time：2023/7/8
 *@Author：Jelly
 */
//控制器：处理浏览器的请求，和给浏览器响应
//UserController：表示操作user表
@RestController
@RequestMapping("/user")
public class UserController {
    //控制器需要依赖业务层
    //写接口是为了方便切换不同实现类，提高扩展性
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户注册
     *
     * @RequestBody: 1.取出请求体的数据、2。 吧JSON字符串转成JAVA对象
     */
    @RequestMapping("/register.do")
    public Result Register(@RequestBody User registerUser) {
        //1.调用业务层进行注册
        //IDEA自动生成方法:Create method xxx in yyy
        Result result = userService.register(registerUser);

        //2.返回结果
        return result;
    }

    /**
     * 用户登录
     * 路径：http://localhost:80/user/login.do
     */
    @RequestMapping("/login.do")
    public Result login(@RequestBody User loginUser) {
        //1. 调用业务层登录
        Result result = userService.login(loginUser);
        //2. 返回结果
        return result;
    }

    /**
     * 后台手机号登录
     * POST
     */
    @RequestMapping("/telephoneLogin.do")
    public Result telephoneLogin(@RequestBody Map<String, String> map) {
        //1. 获取用户输入的手机号和验证码
        String telephone = map.get("telephone");
        String smsCode = map.get("smsCode");
        //2. 调用DAO根据手机号查询User
        User dbUser = userService.findByTelephone(telephone);
        //3. 如果手机号找不到User,表示手机号未注册，登录失败
        if (dbUser == null) {
            return new Result(false, "手机号未注册");
        }
        //4. 从Redis中获取缓存的验证码
        String authCode = (String) redisTemplate.opsForValue().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        //5. 如果验证码已经过期
        if(authCode == null) {
            return new Result(false, MessageConstant.SEND_VALIDATECODE_OUTTIME);
        }
        //6. 如果验证码不正确，提示用户验证码不正确
        if (!authCode.equals(smsCode)) {
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        //7. 将USER信息存入Redis中，手机号作为key，保存时长30分组
        redisTemplate.opsForValue().set(telephone,dbUser,30, TimeUnit.MINUTES);

        //8.将Redis里面的验证码删除
        redisTemplate.delete(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}