package com.test.day01;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class GetResponse {

    @Test
    public void register(){
        // 1、请求方式
        // 2、请求地址
        // 3、请求头
        // 4、请求内容
        String jsonData = "{\"mobile_phone\":\"18102222173\",\"pwd\":\"lemon123456\",\"type\":1}";
        Response res = RestAssured.
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
                log().all().
                // extract().response():获取响应报文
                        extract().response();
        // 获取接口的响应时间
        System.out.println(res.time());
        // 获取响应头的信息
        System.out.println(res.getHeader("Content-Type"));
        // 获取响应体的信息（json格式）
        // 通过json路径表示来获取json的中某一个节点的值
        Object userId = res.jsonPath().get("data.id");
        System.out.println("用户id为： " + userId);
        Object msg = res.jsonPath().get("msg");
        System.out.println("msg为： " + msg);
    }

    @Test
    public void getHtmlData() {
        Response res = RestAssured.
                // given：设置 测试预设（包括请求头、请求参数、请求体、cookies 等等）
                        given().
                //when ：所要执行的操作（GET/POST 请求）
                        when().
                        get("http://www.baidu.com").
                //then 解析结果、断言
                        then().
                        log().all().
                // extract().response():获取响应报文
                        extract().response();
        // 获取百度的title标签
        // 获取标签的文本值
        Object titleStr = res.htmlPath().get("html.head.title");
        System.out.println("百度的title标签的文本值： " + titleStr);

        // 获取标签的属性值
        Object str = res.htmlPath().get("html.head.meta[2].@name");
        System.out.println("百度的title标签的属性值： " + str);
    }

    @Test
    public void getXmlData() {
        Response res = RestAssured.
                // given：设置 测试预设（包括请求头、请求参数、请求体、cookies 等等）
                        given().
                //when ：所要执行的操作（GET/POST 请求）
                        when().
                        get("http://www.httpbin.org/xml").
                //then 解析结果、断言
                        then().
                        log().all().
                // extract().response():获取响应报文
                        extract().response();
        // 获取slide的title标签
        // 获取标签的文本值
        Object titleStr = res.htmlPath().get("slideshow.slide[0].title");
        System.out.println("slide的title标签的文本值： " + titleStr);

        // 获取标签的type
        Object str = res.htmlPath().get("slideshow.slide[1].type");
        System.out.println("slide的title标签的属性值： " + str);
    }
}
