package com.lemon.utils;  //util：工具

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.lemon.pojo.CasePojo;

import java.io.File;
import java.util.List;

/**
 * 工具类
 */
public class ExcelUtil {
    /**
     * 读取excel指定sheet里面的全部数据
     * @return list集合
     */
    public static List<CasePojo> readExcelSheetAllDatas(int sheetNum) {
        // 1、指定excel的所在位置
        File file = new File(Constants.EXCEL_PATH);
        // 2、创建对象，读取/导入excel的一些参数配置
        ImportParams ip = new ImportParams();
        //3、通过easy poi读取excel的数据，将excel每一行数据映射成实体对象
        // 设置sheet（通过索引来设置）,sheet是可变的，所以要进行参数化；sheetNum-1：是为了方便传参，如传1，这里是0
        ip.setStartSheetIndex(sheetNum-1);
        // 用list来接收读取的数据（easy poi）
        List<CasePojo> listData  = ExcelImportUtil.importExcel(file,CasePojo.class,ip);
        return listData;
    }

    /**
     * 读取excel指定sheet里面的指定行
     * @return list集合
     */
    public static List<CasePojo> readExcelSpecifyDatas(int sheetNum,int starRows) {
        // 1、指定excel的所在位置
        File file = new File(Constants.EXCEL_PATH);
        // 2、创建对象，读取/导入excel的一些参数配置
        ImportParams ip = new ImportParams();
        //3、通过easy poi读取excel的数据，将excel每一行数据映射成实体对象
        // 设置sheet（通过索引来设置）,sheet是可变的，所以要进行参数化；sheetNum-1：是为了方便传参，如传1，这里是0
        ip.setStartSheetIndex(sheetNum-1);
        //设置读取开始行,row是可变的，所以要进行参数化；starRows-1：是为了方便传参，如传1，这里是0
        ip.setStartRows(starRows-1);
        // 用list来接收读取的数据（easy poi）
        List<CasePojo> listData  = ExcelImportUtil.importExcel(file,CasePojo.class,ip);
        return listData;
    }

    /**
     * 读取excel指定sheet里面的指定行
     * @return list集合
     */
    public static List<CasePojo> readExcelSpecifyDatas(int sheetNum,int starRows,int readRows) {
        // 1、指定excel的所在位置
        File file = new File(Constants.EXCEL_PATH);
        // 2、创建对象，读取/导入excel的一些参数配置
        ImportParams ip = new ImportParams();
        //3、通过easy poi读取excel的数据，将excel每一行数据映射成实体对象
        // 设置sheet（通过索引来设置）,sheet是可变的，所以要进行参数化；sheetNum-1：是为了方便传参，如传1，这里是0
        ip.setStartSheetIndex(sheetNum-1);
        //设置读取开始行,row是可变的，所以要进行参数化；starRows-1：是为了方便传参，如传1，这里是0
        ip.setStartRows(starRows-1);
        //设置读取的行数
        ip.setReadRows(readRows);
        // 用list来接收读取的数据（easy poi）
        List<CasePojo> listData  = ExcelImportUtil.importExcel(file,CasePojo.class,ip);
        return listData;
    }

}
