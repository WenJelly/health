package com.wenguodong.health.service;

import com.wenguodong.health.entity.PageResult;
import com.wenguodong.health.entity.QueryPageBean;
import com.wenguodong.health.pojo.Setmeal;

import java.util.List;

/*
 *@Time：2023/7/11
 *@Author：Jelly
 */
public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    Setmeal findById(Integer id);

    List<Integer> findCheckGroupBySetmealId(Integer setmealId);

    void edit(Setmeal setmeal, Integer[] checkgroupIds);

    boolean delete(Integer id);
}
