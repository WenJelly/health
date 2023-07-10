package com.wenguodong.health;

import com.wenguodong.health.utils.SMSUtils;
import org.junit.jupiter.api.Test;

/*
 *@Time：2023/7/9
 *@Author：Jelly
 */
public class TestSMS {
    @Test
    public void testSMS() {
        SMSUtils.send("17620487265","6666");
    }
}
