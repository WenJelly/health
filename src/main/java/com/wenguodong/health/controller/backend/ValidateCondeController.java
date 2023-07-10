package com.wenguodong.health.controller.backend;

import com.wenguodong.health.constant.MessageConstant;
import com.wenguodong.health.constant.RedisMessageConstant;
import com.wenguodong.health.entity.Result;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/*
 *@Time：2023/7/9
 *@Author：Jelly
 */
@RestController
public class ValidateCondeController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 后台手机号登录-发送验证码
     *
     * @param telephone
     * @return
     */
    @RequestMapping("/validatecode/backendSend4Login.do")
    public Result backendSend4Login(String telephone) {

        //1. 生成4位数字验证码
        String authCode = RandomStringUtils.randomNumeric(4);  //会生成4位
        //2. 发送短信
//        String isOk = SMSUtils.send(telephone, authCode);
        System.out.println("authCode = " + authCode);
        String isOk = "OK";
        //3. 判断短信是否发送成功，是否为OK
        if("OK".equals(isOk)) {
            //3.1. 短信发送成功，把验证码存在Redis中，存活时间五分钟
            redisTemplate.opsForValue().set(telephone + RedisMessageConstant.SENDTYPE_LOGIN,authCode,5, TimeUnit.MINUTES);
            //3.2. 返回统一响应结果，Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }else {
            //3.2. 短信发送失败，返回统一响应结果，Result（false，MessageConstant.SEND_VALIDATECODE_FAIL）
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

}
