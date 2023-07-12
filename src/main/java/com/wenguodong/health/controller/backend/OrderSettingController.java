package com.wenguodong.health.controller.backend;

/*
 *@Time：2023/7/12
 *@Author：Jelly
 */

import com.wenguodong.health.constant.MessageConstant;
import com.wenguodong.health.entity.Result;
import com.wenguodong.health.pojo.OrderSetting;
import com.wenguodong.health.service.OrderSettingService;
import com.wenguodong.health.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Autowired
    private OrderSettingService orderSettingService;

    /**
     * Excel文件上传，并解析文件内容保存到数据库
     *
     * @param excelFile 前端上传的名字
     * @return 统一响应结果
     */
    @RequestMapping("/upload.do")
    public Result upload(MultipartFile excelFile) throws IOException {
        try {
            //1. 读取Excel文件数据
            List<String[]> list = POIUtils.readExcel(excelFile);

            //2. 创建List集合保存Excel中的每行数据
            List<OrderSetting> orderSettings = new ArrayList<>();
            for (String[] strings : list) {
                //3. 遍历得到excel的每一行数据, 放到OrderSetting对象中
                OrderSetting orderSetting = new OrderSetting(new Date(strings[0]), Integer.parseInt(strings[1]));
                //4. 把OrderSetting对象保存得到集合中
                orderSettings.add(orderSetting);
            }
            //5. 把读取的数据保存得到数据库
            orderSettingService.add(orderSettings);
        } catch (IOException e) {
            //6. 出现异常，返回统一响应结果，设置失败信息
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        //7. 没有异常，返回统一响应结果，设置成功信息
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    /**
     * 根据日期查询预约设置数据(获取指定日期所在月份的预约设置数据)
     * @param month 月份，格式为：2023-6
     * @return 统一响应结果,包含预约设置数据
     */
    @RequestMapping("/getOrderSettingByMonth.do")
    public Result getOrderSettingByMonth(@DateTimeFormat(pattern = "yyyy-MM") Date month) {
        // 1.调用业务根据日期查询预约设置数据
        List<OrderSetting> orderSettings = orderSettingService.getOrderSettingByMonth(month);

        // 2.响应统一结果Result
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS, orderSettings);
    }

    /**
     * 根据指定日期修改可预约人数
     * @param orderSetting 新的可预约人数, 要修改的日期
     * @return 统一响应结果
     */
    @RequestMapping("/editNumberByOrderDate.do")
    public Result editNumberByOrderDate(@RequestBody OrderSetting orderSetting) {
        // 1.调用业务根据指定日期修改可预约人数
        orderSettingService.editNumberByOrderDate(orderSetting);

        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}
