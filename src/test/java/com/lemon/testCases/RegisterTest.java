package com.lemon.testCases;


import com.alibaba.fastjson.JSONObject;
import com.lemon.pojo.CasePojo;
import com.lemon.utils.ExcelUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;


/**
 * 注册功能模块的测试类
 */
public class RegisterTest {

    @BeforeTest
    public void before(){
        //注意：使用rest-assured框架，接口返回的json小数，就会自动设置为float类型
        // 解决方法：将rest-assured，接口返回的json小数，替换成BigDecimal类型
        //方法一：直接在given后面加config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL))).
        //方法二：设置一个变量
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL));
        //rest-assured提供了基础的baseUrl设置
        RestAssured.baseURI = "http://api.lemonban.com/futureloan";
    }

    /**
     * 发起接口请求
     * @param cp 数据驱动的对象
     */
    @Test(dataProvider = "getRegisterDatas")
    public void register(CasePojo cp) {    // 形参为数据源中的一维数组里数据的数据类型
        //接口请求
        Response res = request(cp);

        // 断言
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
     * 接口请求方法封装
     * @param cp  请求数据（实体类）
     * @return Response，响应结果
     */
    public Response request(CasePojo cp){
        //(请求头)JSON字符串转成map
        String requestHeaders = cp.getHeader();
        Map requestHeaderMap = JSONObject.parseObject(requestHeaders);
        // 获取接口请求的地址
        String url = cp.getUrl();
        // 获取请求参数
        String params = cp.getParameter();
        // 获取到请求方式
        String method = cp.getMethod();

        //定义Response
        Response res = null;
        // 对不同的请求方式做封装（get/post/patch/put/delete）
        if("get".equalsIgnoreCase(method)){
            res = RestAssured.given().headers(requestHeaderMap).when().get(url).then().log().all().extract().response();
        }else if("post".equalsIgnoreCase(method)){
            res = RestAssured.given().headers(requestHeaderMap).body(params).when().post(url).then().log().all().extract().response();
        }else if("patch".equalsIgnoreCase(method)){
            res = RestAssured.given().headers(requestHeaderMap).body(params).when().patch( url).then().log().all().extract().response();
        }else if("put".equalsIgnoreCase(method)){
            res = RestAssured.given().headers(requestHeaderMap).body(params).when().put(url).then().log().all().extract().response();
        }else if("delete".equalsIgnoreCase(method)){
            res = RestAssured.given().headers(requestHeaderMap).body(params).when().delete( url).then().log().all().extract().response();
        }
        return res;
    }

    /**
     * 将读取到的excel数据转化为数组
     * @return Object[]数组
     */
    // @DataProvider 数据提供源
    @DataProvider
    public Object[] getRegisterDatas(){
        List<CasePojo> listDatas = ExcelUtil.readExcel(1);
        // list集合转为数组
//        Object[] datas = listData.toArray();
//        return datas;
        // 简化后为：
        return listDatas.toArray();
    }


}
