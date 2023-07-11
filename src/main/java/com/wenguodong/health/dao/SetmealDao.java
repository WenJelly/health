package com.wenguodong.health.dao;

import com.github.pagehelper.Page;
import com.wenguodong.health.pojo.Setmeal;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*
 *@Time：2023/7/11
 *@Author：Jelly
 */
@Mapper
public interface SetmealDao {

    /**
     * 新增套餐
     * @param setmeal 套餐数据
     */
    @Insert("insert into t_setmeal values (null, #{name}, #{code}, #{helpCode}, #{sex}, #{age}, #{price}, #{remark}, #{attention}, #{img});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(Setmeal setmeal);


    @Insert("insert into t_setmeal_checkgroup values (#{setmealId}, #{checkgroupId});")
    void setSetmealAndCheckGroupId(Integer setmealId, Integer checkgroupId);

    /**
     * 套餐分页查询
     * @param queryString 查询关键字
     * @return 分页查询的数据
     */
    Page<Setmeal> findAll(String queryString);

    /**
     * 根据id查询套餐信息
     * @param id 套餐id
     * @return 统一响应结果，只包含套餐信息
     */
    @Select("select * from t_setmeal where id = #{id}")
    Setmeal findById(Integer id);

    /**
     * 根据套餐id查询对应的所有检查组id
     * @param setmealId 套餐id
     * @return 统一响应结果，套餐id对应的所有检查组id
     */
    @Select("select checkgroup_id from t_setmeal_checkgroup where setmeal_id = #{setmealId};")
    List<Integer> findCheckGroupBySetmealId(Integer setmealId);

    /**
     * 编辑套餐
     * @param setmeal 套餐数据
     */
    void edit(Setmeal setmeal);

    /**
     * 根据套餐id删除中间表数据（清理原有关联关系）
     * @param id 套餐id
     */
    @Delete("delete from t_setmeal_checkgroup where setmeal_id = #{id)};")
    void deleteAssociation(Integer id);

    /**
     * 删除套餐
     * @param id
     * @return
     */
    @Delete("delete from t_setmeal where id = #{id}")
    long delete(Integer id);
}
