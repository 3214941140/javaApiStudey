package com.lemon.common;

import com.alibaba.fastjson.JSONObject;
import com.lemon.data.Environment;
import com.lemon.pojo.CasePojo;
import com.lemon.utils.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;

public class BaseTest {

    @BeforeSuite
    public void beforeSuite(){
        //注意：使用rest-assured框架，接口返回的json小数，就会自动设置为float类型
        // 解决方法：将rest-assured，接口返回的json小数，替换成BigDecimal类型
        //方法一：直接在given后面加config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL))).
        //方法二：设置一个变量
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL));
        //rest-assured提供了基础的baseUrl设置
        RestAssured.baseURI = Constants.BASE_URL;
    }

    /**
     * 接口请求方法封装
     * @param cp  请求数据（实体类）
     * @return Response，响应结果
     */
    public Response request(CasePojo cp){
        //(请求头)JSON字符串转成map
        String requestHeaders = cp.getHeader();
        Map rHMap = JSONObject.parseObject(requestHeaders);
        // 获取接口请求的地址
        String url = cp.getUrl();
        // 获取请求参数
        String params = cp.getParameter();
        // 获取到请求方式
        String method = cp.getMethod();

        //定义Response
        Response res = null;
        // 对不同的请求方式做封装（get/post/patch/put/delete）
        // url :因为rest-assured里提供了baseurl方法，所以url会自动拼接，不需要手动拼接
        if("get".equalsIgnoreCase(method)){
            res = RestAssured.given().log().all().headers(rHMap).when().get(url).then().log().all().extract().response();
        }else if("post".equalsIgnoreCase(method)){
            res = RestAssured.given().log().all().headers(rHMap).body(params).when().post(url).then().log().all().extract().response();
        }else if("patch".equalsIgnoreCase(method)){
            res = RestAssured.given().log().all().headers(rHMap).body(params).when().patch( url).then().log().all().extract().response();
        }else if("put".equalsIgnoreCase(method)){
            res = RestAssured.given().log().all().headers(rHMap).body(params).when().put(url).then().log().all().extract().response();
        }else if("delete".equalsIgnoreCase(method)){
            res = RestAssured.given().log().all().headers(rHMap).body(params).when().delete( url).then().log().all().extract().response();
        }
        return res;
    }


    /**
     * 响应断言
     * @param cp  请求数据（实体类）
     * @param res 实际响应结果
     */
    public void assertResponse(CasePojo cp,Response res){
        //断言
        // 从表格数据里面取出来期望的响应结果（json格式，键名：Gpath表达式，键值：对应的期望结果）
        String expected = cp.getExpected();
        if (expected != null) {
            // 转成map
            Map<String, Object> expectedMap = JSONObject.parseObject(expected);
            // 遍历map，进行断言
            Set<String> allKeySet = expectedMap.keySet();
            for (String key : allKeySet) {
                // key：我们对象的Gpath表达式
                // 获取实际的响应结果
                Object actualResult = res.jsonPath().get(key);
                System.out.println("实际响应结果：" + actualResult);
                System.out.println("实际响应结果类型：" + actualResult.getClass());
                // 获取期望结果
                Object expectedResult = expectedMap.get(key);
                System.out.println("期望结果：" + expectedResult);
                System.out.println("期望结果类型：" + expectedResult.getClass());
                Assert.assertEquals(actualResult, expectedResult);
            }
        }
    }

    /**
     * 通过提取表达式将对应的响应值保存到环境变量中
     * @param res 接口响应信息
     * @param cp 实体类对象
     */
    public void extractToEnvironment(Response res , CasePojo cp){
        String extractStr = cp.getExtractExper();
        if (extractStr != null) {
            // 把提取表达式转成Map
            Map<String , Object> map = JSONObject.parseObject(extractStr);
            Set<String> allKeys = map.keySet();
            for (String key : allKeys) {
                // key：变量名，value:为提取的Gpath表达式
                Object value = map.get(key);
                // value为Object类型，jsonPath().get()方法里填的是String类型，所以必须向下转型
                Object actualValue = res.jsonPath().get((String) value);
                //将对应的键和值保存到环境变量中
                Environment.envMap.put(key,actualValue);
            }
        }
    }

    /**
     * 正则替换功能，如：
     * 原始字符串是：String str = "{\"mobile_phone\": \"{{mobile_phone}}\", \"pwd\": \"{{pwd}}\"}";
     * 替换为：{"mobile_phone": "15087714882", "pwd": "123456"}
     * 15087714882 和 123456 是为环境变量中的mobile_phone和pwd变量对应的变量值
     * @param orgStr 原始字符串
     * @return 替换后的字符串或原始字符串
     */
    public String regexReplace(String orgStr){
        if(orgStr != null){
            //匹配器
            Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
            //匹配对象
            Matcher matcher = pattern.matcher(orgStr);
            String result = orgStr;
            //循环遍历匹配的对象
            while(matcher.find()){
                //matcher.group(0)：获取整个匹配正则的字符串  {{mobile_phone}}
                String allFindStr = matcher.group(0);
                //matcher.group(1)：找到{{xxx}}内部的匹配的字符串  mobile_phone
                String innerStr = matcher.group(1);
                //找到手机号：15087714882
                //具体的要替换的值（从环境变量中去找到的）
                Object replaceValue = Environment.envMap.get(innerStr);
                //要替换的{{mobile_phone}} ---> 15087714882
                //第二次替换的时候应该是基于第一次替换的结果的基础上再去替换
                //解决方案：定义一个字符串变量，用来接收替换会后的字符串，这个变量等于要替换的原始的字符串，如：String result = str;
                result = result.replace(allFindStr, replaceValue + "");
            }
            return result;
        }else {
            return orgStr;
        }
    }

    /**
     * 整条用例数据的参数化替换，只要在对应的用例数据里有{{xxx}}，那么就会从环境变中找到xxx，如果找到的话就去替换，否则不会
     * @param cp 实体类对象
     * @return cp 具体参数
     */
    public CasePojo paramsReplace(CasePojo cp){
        //1、请求头
        String requestHeader = cp.getHeader();
        //要将替换后的字符串添加到参数中
        cp.setHeader(regexReplace(requestHeader));
        //2、接口地址
        String url = cp.getUrl();
        cp.setUrl(regexReplace(url));
        //3、请求参数
        String params = cp.getParameter();
        cp.setParameter(regexReplace(params));
        //4、期望结果
        String expected = cp.getExpected();
        cp.setExpected(regexReplace(expected));
        return cp;
    }
}
