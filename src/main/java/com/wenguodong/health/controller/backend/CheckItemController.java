package com.wenguodong.health.controller.backend;

/*
 *@Time：2023/7/9
 *@Author：Jelly
 */

import com.wenguodong.health.constant.MessageConstant;
import com.wenguodong.health.entity.PageResult;
import com.wenguodong.health.entity.QueryPageBean;
import com.wenguodong.health.entity.Result;
import com.wenguodong.health.pojo.CheckItem;
import com.wenguodong.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Autowired
    //控制器依赖业务层
    private CheckItemService checkItemService;

    /**
     * 添加检查项
     *
     * @param checkItem 前端提交过来的检查项数据
     * @return 统一响应结果
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody CheckItem checkItem) {
        //1. 调用业务层接口
        Result result = checkItemService.add(checkItem);
        //2. 响应统一接口Result
        return result;
    }

    /**
     * 检查项分页
     *
     * @return
     */

    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        //1. 调用业务层，检查项分页查询
        PageResult pageResult = checkItemService.findPage(queryPageBean);

        //2. 返回结果
        return pageResult;
    }

    /**
     * 删除检查项
     *
     * @param id 要删除的检查项id
     * @return 统一响应结果
     */
    @RequestMapping("/delete.do")
    public Result delete(Integer id) {
        // 1.调用业务层分页查询
        boolean delete = checkItemService.delete(id);
        // 2.响应统一结果Result
        String message = delete ? MessageConstant.DELETE_CHECKITEM_SUCCESS : MessageConstant.DELETE_CHECKITEM_FAIL;
        return new Result(delete, message);
    }

    /**
     * 根据id查询检查项
     *
     * @param id 检查项id
     * @return 统一响应结果, 包含检查项数据
     */
    @RequestMapping("/findById.do")
    public Result findById(Integer id) {
        // 1.调用业务层根据id查询检查项
        CheckItem checkItem = checkItemService.findById(id);
        // 2.响应统一结果Result,包含检查项数据
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
    }

    /**
     * 编辑检查项
     * @param checkItem 检查项数据
     * @return 统一响应结果
     */
    @RequestMapping("/edit.do")
    public Result edit(@RequestBody CheckItem checkItem){
        // 1.调用业务层编辑检查项
        checkItemService.edit(checkItem);
        // 2.响应统一结果Result
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 查询所有检查项
     * @return 所有检查项数据
     */

    @RequestMapping("/findAll.do")
    public Result findAll() {
        List<CheckItem> checkItems = checkItemService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItems);
    }

}
