package com.wenguodong.health;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.*;

/*
 *@Time：2023/7/12
 *@Author：Jelly
 */
public class TestPOI {
    @Test
    public void test01() throws IOException {
        //有空行没关系
        //必须要使用InputStream的构造器，否则close（）时会出现OpenXML4JRuntimeException
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("E:\\TestRead.xlsx"));

        //关系：工作薄--->工作表--->行--->单元格

        //获取工作表，即可以根据工作表的顺序获取，也可以根据工作表名称获取
        XSSFSheet sheetAt0 = workbook.getSheetAt(0);

        //遍历工作表获得行对象
        for (Row row :sheetAt0) {
            //遍历行对象获得单元格对象
            for (Cell cell :row) {
                //获得里面的数值
                String value = cell.getStringCellValue();
                System.out.println(value + "\t");
            }
            System.out.println();
        }
        workbook.close();
    }


    /**
     * 还有一种方式就是获取工作表最后一个行号，从而根据行号获得行对象，通过行获取最后一个单元格索引，从而根据单元格索引获取每行的一个单元格对象，代码如下：
     * @throws IOException
     * @throws InvalidFormatException
     */

    @Test
    public void test02() throws IOException, InvalidFormatException {
        // 有空行会出现空指针异常
        // 创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("D:\\MyFileTest\\poi\\TestRead.xlsx"));

        // 获取工作表，既可以根据工作表的顺序获取，也可以根据工作表的名称获取
        XSSFSheet sheetAt0 = workbook.getSheetAt(0);

        // 获取当前工作表最后一行的行号，行号从0开始
        int lastRowNum = sheetAt0.getLastRowNum();
        // System.out.println("最后一行的行号 = " + lastRowNum);
        for (int i = 0; i <= lastRowNum; i++) {
            // 根据行号获取行对象
            XSSFRow row = sheetAt0.getRow(i);

            // 获取当前行的最后一列的列号，列号从1开始
            short lastCellNum = row.getLastCellNum();
            // System.out.println("\t最后一列的行号 = " + lastCellNum);
            for (short j = 0; j < lastCellNum; j++) {
                String value = row.getCell(j).getStringCellValue();
                System.out.print(value + "\t");
            }
            System.out.println();
        }
        workbook.close();
    }

    @Test
    public void test03() throws IOException {
        // 在内存中创建一个Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();

        //创建工作薄
        XSSFSheet test = workbook.createSheet("test");

        //创建一行
        XSSFRow row = test.createRow(0);
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("名称");
        row.createCell(2).setCellValue("年龄");

        //创建另一行
        XSSFRow row1 = test.createRow(1);
        row1.createCell(0).setCellValue(1);
        row1.createCell(1).setCellValue("温国栋");
        row1.createCell(2).setCellValue(18);

        //创建另一行
        XSSFRow row2 = test.createRow(2);
        row2.createCell(0).setCellValue(1);
        row2.createCell(1).setCellValue("温国栋");
        row2.createCell(2).setCellValue(19);

        //将数据写到文件中
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\TestRead.xlsx");
        workbook.write(fileOutputStream);

        fileOutputStream.close();
        workbook.close();
    }

    @Test
    //录入信息
    public void test04() throws IOException {
        // 在内存中创建一个Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建工作薄
        XSSFSheet sheet = workbook.createSheet("预约设置模板");
        //将数据写到文件中
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\ordersetting_template1.xlsx");

        XSSFRow row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("日期");
        row0.createCell(1).setCellValue("可预约数量");
        workbook.write(fileOutputStream);

        for (int i = 0; i < 12; i++) {
            //创建一行
            XSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue("2023/6/" + i + 3);
            row.createCell(1).setCellValue("300");
            workbook.write(fileOutputStream);
        }

        fileOutputStream.close();
        workbook.close();
    }
}
