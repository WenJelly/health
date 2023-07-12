package com.wenguodong.health.service;

import com.wenguodong.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

/*
 *@Time：2023/7/12
 *@Author：Jelly
 */
public interface OrderSettingService {
    void add(List<OrderSetting> orderSettings);

    List<OrderSetting> getOrderSettingByMonth(Date month);

    void editNumberByOrderDate(OrderSetting orderSetting);
}
