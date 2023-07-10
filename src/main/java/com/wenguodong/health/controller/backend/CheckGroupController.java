package com.wenguodong.health.controller.backend;

import com.wenguodong.health.constant.MessageConstant;
import com.wenguodong.health.entity.PageResult;
import com.wenguodong.health.entity.QueryPageBean;
import com.wenguodong.health.entity.Result;
import com.wenguodong.health.pojo.CheckGroup;
import com.wenguodong.health.service.CheckGroupService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 *@Time：2023/7/10
 *@Author：Jelly
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Autowired
    private CheckGroupService checkGroupService;

    /**
     * 新增检查组
     *
     * @param checkGroup   检查组信息
     * @param checkItemIds 检查组的检查项
     * @return 统一响应结果
     */

    @RequestMapping("/add.do")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {
        //1. 调用Service层处理
        checkGroupService.add(checkGroup, checkItemIds);

        //2. 响应统一结果Result
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 检查组分页查询
     *
     * @param queryPageBean 前端提交过来的分页查询参数
     * @return 分页查询结果
     */
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        //1. 调用业务层分页查询
        PageResult pageResult = checkGroupService.findPage(queryPageBean);

        //2. 响应PageResult
        return pageResult;
    }

    /**
     * 根据id查询检查组
     *
     * @param id 检查组id
     * @return 统一响应结果, 包含检查组数据
     */
    @RequestMapping("/findById.do")
    private Result findById(Integer id) {
        CheckGroup checkGroup = checkGroupService.findById(id);

        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
    }

    /**
     * 根据检查组合id查询对应的所有检查项id
     *
     * @param checkGroupId
     * @return 统一响应结果, 包含所有检查项id
     */
    @RequestMapping("/findCheckItemIdsByCheckGroupId.do")
    private Result findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        List<Integer> checkItemIds =
                checkGroupService.findCheckItemIdsByCheckGroupId(checkGroupId);

        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemIds);
    }

    /**
     * 编辑检查项
     *
     * @param checkGroup 检查组数据
     * @return 统一响应结果
     */
    @RequestMapping("/edit.do")
    private Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {
        //1. 调用业务层编辑检查组
        checkGroupService.edit(checkGroup, checkItemIds);
        //2. 返回统一结构
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 删除检查组
     */
    @RequestMapping("/delete.do")
    public Result delete(Integer id) {
        //1. 调用Service层，根据检查组Id删除检查组
        boolean delete = checkGroupService.delete(id);
        String message = delete ? MessageConstant.DELETE_CHECKITEM_SUCCESS : MessageConstant.DELETE_CHECKITEM_FAIL;
        return new Result(delete, message);
    }

}
