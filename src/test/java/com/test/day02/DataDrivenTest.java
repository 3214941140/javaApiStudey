package com.test.day02;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class DataDrivenTest {

    public static void main(String[] args) {
        // 1、指定excel的所在位置
        File file = new File("src/test/resources/TestCase.xlsx");
        // 2、创建对象
        ImportParams ip = new ImportParams();
        //3、通过easy poi读取excel的数据，将excel每一行数据映射成实体对象
        // 设置sheet（通过索引来设置）
        ip.setStartSheetIndex(0);
        // 用list来接收读取的数据
        List<CasePojo> list  = ExcelImportUtil.importExcel(file);
    }
}
