package com.wenguodong.health.dao;

import com.wenguodong.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/*
 *@Time：2023/7/12
 *@Author：Jelly
 */
@Mapper
public interface OrderSettingDao {
    /**
     * 从数据库查询此日期是否已经存在设置
     * @param orderDate 日期
     * @return 可预约的数量
     */
    @Select("select * from t_ordersetting where orderDate = #{dete};")
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 更新预约数量
     * @param orderSetting
     */
    @Update("update t_ordersetting set number = #{number} where orderDate = #{orderDate};")
    void editNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 添加预约数量
     * @param orderSetting
     */
    @Insert("insert into t_ordersetting values (null, #{orderDate}, #{number}, #{reservations});")
    void add(OrderSetting orderSetting);

    /**
     * 根据日期查询预约设置数据
     * @param begin 当月开始日期 2023-6-1
     * @param end 当月结束日期 2023-6-31
     * @return 当月的预约设置数据
     */
    @Select("select * from t_ordersetting where orderDate between #{begin} and #{end};")
    List<OrderSetting> getOrderSettingByMonth(Date begin, Date end);
}
