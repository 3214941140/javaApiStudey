package com.test.day01;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.File;

public class RestAssuredTest {

    @Test
    public void postRequest(){
        // 1、请求方式
        // 2、请求地址
        // 3、请求头
        // 4、请求内容
        String jsonData = "{\"mobile_phone\":\"18102222168\",\"pwd\":\"lemon123456\",\"type\":1}";
        RestAssured.
                // given：设置 测试预设（包括请求头、请求参数、请求体、cookies 等等）
                given().
                // header：请求头
                header("X-Lemonban-Media-Type","lemonban.v1").
                header("contentType","application/json").
                //header("contentType","application/json")和contentType("application/json")的意思是一样的，都是请求头里的键值对
                contentType("application/json").
                // body：请求体
                body(jsonData).
                //when ：所要执行的操作（GET/POST 请求）
                when().
                post("http://api.lemonban.com/futureloan/member/register").
                //then 解析结果、断言
                then().
                // log：打印日志；all：全部数据（包含请求报文和响应报文）
                log().all();
    }

    @Test
    public void getRequest01(){
        // get 请求的第一种传参方式
        RestAssured.
                given().
                contentType("application/json").
                when().
                // http://www.httpbin.org/：练习地址；get：请求方式
                        get("http://www.httpbin.org/get?mobilePhone=18102222168&pwd=123456").
                then().
                log().all();
    }

    @Test
    public void getRequest02(){
        // get 请求的第二种传参方式
        RestAssured.
                given().
                contentType("application/json").
                queryParam("mobilePhone","18102222168").
                queryParam("pwd","123456").
                when().
                // http://www.httpbin.org/：练习地址；get：请求方式
                get("http://www.httpbin.org/get").
                then().
                log().all();
    }

    @Test
    public void postRequest01(){
        // post 请求的第一种传参方式
        RestAssured.
                given().
                contentType("application/x-www-form-urlencoded").
                body("mobilePhone=18102222168&pwd=123456").
                when().
                // http://www.httpbin.org/：练习地址；get：请求方式
                        post("http://www.httpbin.org/post").
                then().
                log().all();
    }

    @Test
    public void postRequest02(){
        // post 请求的第二种传参方式
        String jsonData = "{\"mobilephone\":\"13323234545\",\"pwd\":\"123456\"}";
        RestAssured.
                given().
                contentType("application/json").
                body(jsonData).
                when().
                // http://www.httpbin.org/：练习地址；get：请求方式
                        post("http://www.httpbin.org/post").
                then().
                log().all();
    }

    @Test
    public void postRequest03(){
        // post 请求的第三种传参方式
        String xmlStr =  "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                         "<suite>\n" +
                         "   <class>测试xml</class>\n" +
                         "</suite>";
        RestAssured.
                given().
                contentType("application/xml").
                body(xmlStr).
                when().
                // http://www.httpbin.org/：练习地址；get：请求方式
                        post("http://www.httpbin.org/post").
                then().
                log().all();
    }

    @Test
    public void postRequest04(){
        // post 请求的第四种传参方式
        File file = new File("C:\\Users\\王胖子\\Desktop\\作业.docx");
        RestAssured.
                given().
                contentType("multipart/form-data").
                multiPart(file).
                when().
                // http://www.httpbin.org/：练习地址；get：请求方式
                        post("http://www.httpbin.org/post").
                then().
                log().all();
    }
}
