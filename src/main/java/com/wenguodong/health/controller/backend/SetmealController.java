package com.wenguodong.health.controller.backend;

import com.wenguodong.health.constant.MessageConstant;
import com.wenguodong.health.entity.PageResult;
import com.wenguodong.health.entity.QueryPageBean;
import com.wenguodong.health.entity.Result;
import com.wenguodong.health.pojo.Setmeal;
import com.wenguodong.health.service.SetmealService;
import com.wenguodong.health.utils.QiniuUtils;
import com.wenguodong.health.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/*
 *@Time：2023/7/11
 *@Author：Jelly
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 文件上传到七牛云
     *
     * @param imgFile 前端提交的文件上传数据
     * @return 统一响应结果, 包含上传的文件名
     * @throws IOException
     */
    @RequestMapping("/upload.do")
    public Result upload(MultipartFile imgFile) throws IOException {
        System.out.println("imgFile = " + imgFile);

        //获取原始文件名
        String originalFilename = imgFile.getOriginalFilename(); //a.jpg
        System.out.println("originalFilename = " + originalFilename);

        //为了防止文件名重复,随机生成文件名
        String fileName = UUIDUtils.simpleUuid() + "-" + originalFilename;
        System.out.println("fileName = " + fileName);

        //把文件上传到七牛云
        QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);

        return new Result(true, MessageConstant.UPLOAD_SUCCESS, fileName);
    }

    /**
     * 新增套餐
     *
     * @param setmeal       套餐数据
     * @param checkgroupIds 套餐包含的检查组
     * @return 统一响应结果
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        //添加套餐
        setmealService.add(setmeal, checkgroupIds);
        //返回结果
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 套餐分页查询
     *
     * @param queryPageBean 前端提交过来的分页查询参数
     * @return 分页查询结果
     */
    @RequestMapping("/findPage.do")
    private PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        //业务层进行分页查询
        PageResult pageResult = setmealService.findPage(queryPageBean);
        // 2.响应 PageResult
        return pageResult;
    }

    /**
     * 根据id查询套餐信息
     *
     * @param id 套餐id
     * @return 统一响应结果，只包含套餐信息
     */
    @RequestMapping("/findById.do")
    public Result findById(Integer id) {
        // 1.调用业务层根据id获取套餐信息，只包含套餐信息
        Setmeal setmeal = setmealService.findById(id);

        // 2.响应统一结果Result，包含一条套餐信息
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }

    /**
     * 根据套餐id查询对应的所有检查组id
     *
     * @param setmealId 套餐id
     * @return 统一响应结果，套餐id对应的所有检查组id
     */
    @RequestMapping("/findCheckGroupIdsBySetmealId.do")
    public Result findCheckGroupIdsBySetmealId(Integer setmealId) {

        //1. 调用业务层根据套餐id查询对应的所有检查组id
        List<Integer> checkGroupIds = setmealService.findCheckGroupBySetmealId(setmealId);

        //2. 返回统一响应结果
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, checkGroupIds);
    }

    /**
     * 编辑套餐
     *
     * @param setmeal       前端提交过来的套餐数据
     * @param checkgroupIds 套餐对应的检查组id
     * @return 统一响应结果
     */
    @RequestMapping("/edit.do")
    public Result edit(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        //1. 编辑套餐表
        setmealService.edit(setmeal, checkgroupIds);

        //2. 返回统一响应结果
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    /**
     * 删除套餐
     *
     * @param id 套餐的id
     */
    @RequestMapping("/delete.do")
    public Result delete(Integer id) {
        //删除套餐
        boolean delete = setmealService.delete(id);
        String message = delete ? MessageConstant.DELECT_SETMEAL_SUCCESS : MessageConstant.DELECT_SETMEAL_FAIL;
        //返回统一结果
        return new Result(true, message);
    }
}
