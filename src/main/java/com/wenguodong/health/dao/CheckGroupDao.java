package com.wenguodong.health.dao;

import com.github.pagehelper.Page;
import com.wenguodong.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*
 *@Time：2023/7/10
 *@Author：Jelly
 */
@Mapper
public interface CheckGroupDao {
    /**
     * 新增检查组
     * @param checkGroup 检查组信息
     */
    @Insert("insert into t_checkgroup values (null, #{code}, #{name}, #{helpCode}, #{sex}, #{remark}, #{attention});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(CheckGroup checkGroup);

    @Insert("insert into t_checkgroup_checkitem values (#{checkGroupId}, #{checkItemId});")
    void setCheckGroupAndCheckItem(Integer checkGroupId, Integer checkItemId);

    /**
     * 检查组分页查询
     * @param queryString 查询关键字
     * @return 分页查询结果
     */
    Page<CheckGroup> findPage(String queryString);

    @Select("select * from t_checkgroup where id = #{id}")
    CheckGroup findById(Integer id);

    @Select("select checkitem_id from t_checkgroup_checkitem where checkgroup_id = #{checkGroupId};")
    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    /**
     * 编辑检查项
     * @param checkGroup 检查组数据
     */
    void edit(CheckGroup checkGroup);

    /**
     * 根据检查组id删除中间表数据（清理原有关联关系）
     * @param id 检查组id
     */
    @Delete("delete from t_checkgroup_checkitem where checkgroup_id = #{checkgroup_id};")
    void deleteAssociation(Integer id);

    @Delete("delete from t_checkgroup where id = #{id}")
    long delete(Integer id);
}
