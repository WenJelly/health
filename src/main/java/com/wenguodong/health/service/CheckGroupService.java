package com.wenguodong.health.service;

import com.wenguodong.health.entity.PageResult;
import com.wenguodong.health.entity.QueryPageBean;
import com.wenguodong.health.pojo.CheckGroup;

import java.util.List;

/*
 *@Time：2023/7/10
 *@Author：Jelly
 */
public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkItemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    void edit(CheckGroup checkGroup, Integer[] checkItemIds);

    boolean delete(Integer id);

    List<CheckGroup> findAll();
}
