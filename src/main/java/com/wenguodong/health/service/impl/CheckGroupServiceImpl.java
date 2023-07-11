package com.wenguodong.health.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wenguodong.health.dao.CheckGroupDao;
import com.wenguodong.health.entity.PageResult;
import com.wenguodong.health.entity.QueryPageBean;
import com.wenguodong.health.entity.Result;
import com.wenguodong.health.pojo.CheckGroup;
import com.wenguodong.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/*
 *@Time：2023/7/10
 *@Author：Jelly
 */
@Service
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 新增检查组
     *
     * @param checkGroup   检查组信息
     * @param checkItemIds 检查组的检查项
     */
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {

        //1. 调用DAO层,将新增的检查组加入到数据库中
        checkGroupDao.add(checkGroup);

        //2. 将检查项加入到检查组中
        setCheckGroupAndCheckItem(checkGroup.getId(), checkItemIds);

    }

    /**
     * 检查组分页查询
     *
     * @param queryPageBean 前端提交过来的分页查询参数
     * @return 分页查询结果
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //1. 使用分页插件开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //2. 调用DAO层查询
        Page<CheckGroup> page = checkGroupDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查询检查组
     *
     * @param id 检查组id
     * @return 检查组数据
     */
    @Override
    public CheckGroup findById(Integer id) {
        //1. 调用Dao层，返回查询结构
        CheckGroup checkGroup = checkGroupDao.findById(id);
        return checkGroup;
    }

    /**
     * 根据检查组id查询对应的所有检查项id
     *
     * @param checkGroupId 检查组id
     * @return 检查组合的所有检查项id
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }

    /**
     * 编辑检查项
     *
     * @param checkGroup   检查组数据
     * @param checkItemIds
     */
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkItemIds) {
        //1. 编辑检查组
        checkGroupDao.edit(checkGroup);
        //2. 通过检查组Id将该检查组内的检查项删除
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //3. 通过检查组Id给这个检查组新增检查项
        setCheckGroupAndCheckItem(checkGroup.getId(), checkItemIds);
    }

    /**
     * 删除检查项
     *
     * @param id 要删除的检查项id
     * @return 统一响应结果
     */
    @Transactional
    @Override
    public boolean delete(Integer id) {
        //1. 调用Dao层删除检查组和检查项的关联表
        checkGroupDao.deleteAssociation(id);
        //2. 再调用Dao层删除检查组
        long count = checkGroupDao.delete(id);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询所有检查组
     * @return 所有检查组数据
     */
    @Override
    public List<CheckGroup> findAll() {

        List<CheckGroup> checkGroups = checkGroupDao.findAll();

        return checkGroups;
    }

    private void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkItemIds) {
        if (checkGroupId == null || checkItemIds.length == 0) {
            System.out.println("用户没有选择检查项");
            return;
        }

        for (Integer checkItemId : checkItemIds) {
            // 调用DAO往数据新增一个检查组id和一个检查项id
            checkGroupDao.setCheckGroupAndCheckItem(checkGroupId, checkItemId);
        }
    }

}
