package com.wenguodong.health.service;

import com.wenguodong.health.entity.PageResult;
import com.wenguodong.health.entity.QueryPageBean;
import com.wenguodong.health.entity.Result;
import com.wenguodong.health.pojo.CheckItem;

import java.util.List;

/*
 *@Time：2023/7/9
 *@Author：Jelly
 */
public interface CheckItemService {

    /**
     * 添加检查项
     * @param checkItem 前端提交过来的检查项数据
     */
    public Result add(CheckItem checkItem);

    public PageResult findPage(QueryPageBean queryPageBean);

    boolean delete(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();
}
