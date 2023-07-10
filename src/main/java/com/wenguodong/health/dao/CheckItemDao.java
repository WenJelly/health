package com.wenguodong.health.dao;

import com.github.pagehelper.Page;
import com.wenguodong.health.pojo.CheckItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
 *@Time：2023/7/9
 *@Author：Jelly
 */
@Mapper
public interface CheckItemDao {
    /**
     * 添加检查项
     *
     * @param checkItem
     */
    @Insert("insert into t_checkitem values (null, #{code}, #{name}, #{sex}, #{age}, #{price}, #{type}, #{remark}, #{attention});")
    public void add(CheckItem checkItem);

    //真正的分页sql语句是：select * from t_checkitem limit 20，10;
    //不需要谢limit，因为分页插件会写
//    @Select("select * from t_checkitem where code like CONCAT('%', #{queryString}, '%') or name like CONCAT('%', #{queryString}, '%') ")
//    public Page<CheckItem> findPageByString(String queryString);

//    @Select("select * from t_checkitem")
    public Page<CheckItem> findPage(String queryString);

//    @Select("select count(*) from t_checkgroup_checkitem where checkitem_id = #{id};")
    @Select("select count(*) from t_checkitem inner join t_checkgroup_checkitem on t_checkgorup_checkitem.checkitem_id = t_checkitem.id where t_checkitem.id = #{id};")
    long findCountByCheckItemId(Integer id);

    @Delete("delete from t_checkitem where id = #{id};")
    void deleteById(Integer id);

    @Select("select * from t_checkitem where id = #{id}")
    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);
    @Select("select * from t_checkitem;")
    List<CheckItem> findAll();
}