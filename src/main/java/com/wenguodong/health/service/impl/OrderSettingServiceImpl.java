package com.wenguodong.health.service.impl;

import com.wenguodong.health.dao.OrderSettingDao;
import com.wenguodong.health.pojo.OrderSetting;
import com.wenguodong.health.service.OrderSettingService;
import com.wenguodong.health.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/*
 *@Time：2023/7/12
 *@Author：Jelly
 */

// 预约设置服务
@Slf4j
@Service
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 添加预约设置对象
     * @param list 预约设置对象
     */
    @Override
    public void add(List<OrderSetting> list) {
        // 1.遍历得到每一行数据
        for (OrderSetting orderSetting :list) {
            // 2.从数据库查询此日期是否已经有设置可预约数量
            OrderSetting dbOrderSetting = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());

            if(dbOrderSetting != null) {
                //如果此日期有设置可预约数量就更新
                log.info("此日期有设置可预约数量就更新: {}", orderSetting.getNumber());
                orderSettingDao.editNumberByOrderDate(orderSetting);
            }else {
                // 4.如果此日期没有设置可预约数量就添加
                log.info("此日期没有设置可预约数量就添加: {}", orderSetting.getNumber());
                orderSettingDao.add(orderSetting);
            }
        }

    }

    /**
     * 根据日期查询预约设置数据(获取指定日期所在月份的预约设置数据)
     * @param month 月份，格式为：2023-6
     * @return 当月的预约设置数据
     */
    @Override
    public List<OrderSetting> getOrderSettingByMonth(Date month) {
        // 1.通过Calendar对象得到本月第一天的日期
        Date begin = DateUtils.getFirstDay4Date(month);
        // 2.通过Calendar对象得到本月最后一天的日期
        Date end = DateUtils.getLastDay4Date(month);

        System.out.println("begin = " + begin);
        System.out.println("end = " + end);

        // 3.调用DAO根据日期查询预约设置数据
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(begin,end);

        // 4.返回查询预约设置数据
        return list;

    }

    /**
     * 根据指定日期修改可预约人数
     * @param orderSetting 新的可预约人数, 要修改的日期
     */
    @Override
    public void editNumberByOrderDate(OrderSetting orderSetting) {
        // 1.从数据库查询此日期是否已经有设置可预约数量
        OrderSetting dbOrderSetting = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());

        // 2.判断此日期是否有设置可预约数量
        if (dbOrderSetting != null) {
            // 3.如果此日期有设置可预约数量就更新
            orderSettingDao.editNumberByOrderDate(orderSetting);
        } else {
            // 4.如果此日期没有设置可预约数量就添加
            orderSettingDao.add(orderSetting);
        }
    }
}
