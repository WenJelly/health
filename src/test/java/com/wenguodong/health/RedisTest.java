package com.wenguodong.health;

import com.wenguodong.health.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/*
 *@Time：2023/7/9
 *@Author：Jelly
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        //创建操作类对象
        ValueOperations valueOperations = redisTemplate.opsForValue();

        //添加到redis：set key value
        valueOperations.set("test","test");

        //获取数据
        Object wenguodong = valueOperations.get("wenguodong");
        System.out.println("wenguodong = " + wenguodong);

        //添加数据到redis并设计过期时间：set key seconds value
        valueOperations.set("wgd","过期时间50s",50, TimeUnit.SECONDS);

        //设置存储对象
        User user = new User();
        user.setUsername("wenguodong");
        user.setPassword("1445607419");
        user.setTelephone("17620487265");

        valueOperations.set("user",user);

        //删除键
        Boolean test = redisTemplate.delete("test");
        System.out.println("test = " + test);

    }
}
