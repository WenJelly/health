package com.wenguodong.health.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wenguodong.health.constant.MessageConstant;
import com.wenguodong.health.dao.CheckItemDao;
import com.wenguodong.health.entity.PageResult;
import com.wenguodong.health.entity.QueryPageBean;
import com.wenguodong.health.entity.Result;
import com.wenguodong.health.pojo.CheckItem;
import com.wenguodong.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 *@Time：2023/7/9
 *@Author：Jelly
 */
//检查项业务层实现类
@Service
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    //业务层依赖DAO
    private CheckItemDao checkItemDao;

    /**
     * 添加检查项
     *
     * @param checkItem 前端提交过来的检查项数据
     */
    @Override
    public Result add(CheckItem checkItem) {
        // 调用DAO添加检查项
        checkItemDao.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 检查项分页
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        Page<CheckItem> page;
//        if (queryPageBean.getQueryString() != null && queryPageBean.getQueryString()!="") {
            //如果查询条件不为空，则调用不为空方法
//            page = checkItemDao.findPageByString(queryPageBean.getQueryString());
//        } else {
            //1. 使用分页插件设置分页参数
            //PageHelper.startPage(当前页，每页显示数量)
            //分页插件会计算 跳过过的数量
            PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
            //2. 调用DAO查询
            //如果查询条件为空，则调用为空方法
            page = checkItemDao.findPage(queryPageBean.getQueryString());
//        }
        // findPage配置的sql语句为：select * from t_checkitem
        // 分页插件会修改SQL语句，第一次：select count（0） from t_checkitem   查询总数量
        // 分页插件会修改SQL语句，第一次：select * from t_checkitem limit ?,?

        //3. 处理结果
        return new PageResult(page.getTotal(), page.getResult());
    }
    /**
     * 删除检查项
     * @param id 要删除的检查项id
     * @return 统一响应结果
     */
    @Transactional
    @Override
    public boolean delete(Integer id) {
        // 1.查询当前检查项是否和检查组关联
        long count = checkItemDao.findCountByCheckItemId(id);
        // 2.如果当前检查项在检查组中不能删除, 返回false
        if (count > 0) {
            return false;
        }
        // 3.检查项没有在检查组中,删除检查项
        checkItemDao.deleteById(id);
        return true;
    }
    /**
     * 根据id查询检查项
     * @param id 检查项id
     * @return 检查项数据
     */
    @Override
    public CheckItem findById(Integer id) {
        // 1.调用DAO 根据id查询检查项
        CheckItem checkItem = checkItemDao.findById(id);
        // 2.返回检查项对象
        return checkItem;
    }

    /**
     * 编辑检查项
     * @param checkItem 检查项数据
     */
    @Override
    public void edit(CheckItem checkItem) {
        //1. 调用业务层， 编辑检查项
        checkItemDao.edit(checkItem);
    }

    /**
     * 查询所有检查项
     * @return 所有检查项数据
     */
    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> checkItems = checkItemDao.findAll();
        return checkItems;
    }
}
