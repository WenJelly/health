package com.wenguodong.health.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wenguodong.health.dao.SetmealDao;
import com.wenguodong.health.entity.PageResult;
import com.wenguodong.health.entity.QueryPageBean;
import com.wenguodong.health.pojo.Setmeal;
import com.wenguodong.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/*
 *@Time：2023/7/11
 *@Author：Jelly
 */
@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;
    /**
     * 新增套餐
     * @param setmeal 套餐数据
     * @param checkgroupIds 套餐包含的检查组
     */
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //向数据库添加套餐
        setmealDao.add(setmeal);

        //通过id将检查组和套餐进行关联
        setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
    }

    /**
     * 套餐分页查询
     * @param queryPageBean 前端提交过来的分页查询参数
     * @return 分页查询结果
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        //开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        //调用Dao层进行分页
        Page<Setmeal> page = setmealDao.findAll(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据id查询套餐信息
     * @param id 套餐id
     * @return 统一响应结果，只包含套餐信息
     */
    @Override
    public Setmeal findById(Integer id) {
        // 1.调用DAO获取一条套餐信息，只包含套餐信息
        Setmeal setmeal = setmealDao.findById(id);
        // 2.返回一条套餐信息，findById
        return setmeal;
    }

    /**
     * 根据套餐id查询对应的所有检查组id
     * @param setmealId 套餐id
     * @return 统一响应结果，套餐id对应的所有检查组id
     */
    @Override
    public List<Integer> findCheckGroupBySetmealId(Integer setmealId) {
        // 1.调用DAO根据套餐id查询对应的所有检查组id
        List<Integer> checkgroupIds = setmealDao.findCheckGroupBySetmealId(setmealId);
        // 2.返回套餐id对应的所有检查组id
        return checkgroupIds;
    }

    /**
     * 编辑套餐
     * @param setmeal 前端提交过来的套餐数据
     * @param checkgroupIds 套餐对应的检查组id
     */
    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        //1. 修改套餐
        setmealDao.edit(setmeal);

        //2. 通过套餐id删除原有中间表
        setmealDao.deleteAssociation(setmeal.getId());

        //3. 通过套餐id与检查组新增中间表
        setSetmealAndCheckGroup(setmeal.getId(), checkgroupIds);
    }

    /**
     * 删除套餐
     * @param id
     * @return
     */
    @Transactional
    @Override
    public boolean delete(Integer id) {
        //1. 根据套餐id删除中间表
        setmealDao.deleteAssociation(id);
        //2. 根据套餐id删除套餐
        long count = setmealDao.delete(id);
        if(count > 0 ){
            //删除成功
            return true;
        }
        return false;
    }

    private void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkgroupIds) {
        if(setmealId == null || checkgroupIds.length == 0) {
            System.out.println("套餐没有对应的检查组");
        }

        // 把套餐的id和对应的检查组id添加到中间表
        for (Integer checkgroupId : checkgroupIds) {
            setmealDao.setSetmealAndCheckGroupId(setmealId, checkgroupId);
        }
    }


}
