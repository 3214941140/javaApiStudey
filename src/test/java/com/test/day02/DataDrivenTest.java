package com.test.day02;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;


/*
    接口测试思路：
        1、通过easy poi技术读取用例数据
        2、通过testng的 DataProvider注解实现数据驱动测试
        3、通过REST-Assured发起接口请求
 */
public class DataDrivenTest {

    /**
     * 发起接口请求
     * @param cp 数据驱动的对象
     */
    @Test(dataProvider = "getRegisterDatas")
    public void register(CasePojo cp) {    // 形参为数据源中的一维数组里数据的数据类型
        //(请求头)JSON字符串转成map
        String requestHeaders = cp.getHeader();
        Map requestHeaderMap = JSONObject.parseObject(requestHeaders);
        // 获取接口请求的地址
        String url = cp.getUrl();
        // 获取请求参数
        String params = cp.getParameter();

        Response res = RestAssured.
                // given：设置 测试预设（包括请求头、请求参数、请求体、cookies 等等）
                        given().
                // header：请求头，需要为map类型
                        headers(requestHeaderMap).
                // body：请求体
                        body(params).
                //when ：所要执行的操作（GET/POST 请求）
                        when().
                        post("http://api.lemonban.com/futureloan" + url).
                //then 解析结果、断言
                        then().
                        log().all().
                // extract().response():获取响应报文
                        extract().response();

        // 断言
        //这样是不行的，这个断言只对第一条用例有用
        /*
        //1、断言code业务码
        int actualCode = res.jsonPath().get("code");
        Assert.assertEquals(actualCode,0);
        //2、断言msg业务码
        String actualMsg = res.jsonPath().get("msg");
        Assert.assertEquals(actualCode,"OK");
        //3、断言用户名
        String regName = res.jsonPath().get("data.reg_name");
        Assert.assertEquals(actualCode,"小柠檬");
        //4、断言手机号
        String actualPhone = res.jsonPath().get("data.mobile_phone");
        Assert.assertEquals(actualCode,"15087714873");
         */

        // 从表格数据里面取出来期望的响应结果（json格式，键名：Gpath表达式，键值：对应的期望结果）
        String expected = cp.getExpected();
        // 转成map
        Map<String,Object> expectedMap = JSONObject.parseObject(expected);
        // 遍历map，进行断言
        Set<String> allKeySet = expectedMap.keySet();
        for (String key : allKeySet) {
            // key：我们对象的Gpath表达式
            // 获取实际的响应结果
            Object actualResult = res.jsonPath().get(key);
            // 获取期望结果
            Object expectedResult = expectedMap.get(key);
            Assert.assertEquals(actualResult,expectedResult);
        }
    }

    /**
     * 将读取到的excel数据转化为数组
     * @return Object[]数组
     */
    // @DataProvider 数据提供源
    @DataProvider
    public Object[] getRegisterDatas(){
        List<CasePojo> listDatas = readExcel();
        // list集合转为数组
//        Object[] datas = listData.toArray();
//        return datas;
        // 简化后为：
        return listDatas.toArray();
    }

    /**
     * 读取excel的数据
     * @return list集合
     */
    public List<CasePojo> readExcel() {
        // 1、指定excel的所在位置
        File file = new File("src/test/resources/TestCase.xlsx");
        // 2、创建对象，读取/导入excel的一些参数配置
        ImportParams ip = new ImportParams();
        //3、通过easy poi读取excel的数据，将excel每一行数据映射成实体对象
        // 设置sheet（通过索引来设置）
        ip.setStartSheetIndex(0);
        //设置读取开始行
        ip.setStartRows(0);
        //设置读取的行数
        ip.setReadRows(1);
        // 用list来接收读取的数据（easy poi）
        List<CasePojo> listData  = ExcelImportUtil.importExcel(file,CasePojo.class,ip);
        return listData;
    }
}
