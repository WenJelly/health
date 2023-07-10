package com.wenguodong.health;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 *@Time：2023/7/8
 *@Author：Jelly
 */
public class test {
   @Test
   //BCrypt加密
   public void test101(){
       BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
       String encode = bCryptPasswordEncoder.encode("123456");
       System.out.println("encode = " + encode);
   }

   @Test
   //BCrypt校验
    public void test02(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches("123456", "$2a$10$9e4kjAAu9WV4LhqKP1Tgj.5GQ0n.vortsH67LGqTxIERdZX/QGF3K");
        System.out.println(matches);
    }
}
