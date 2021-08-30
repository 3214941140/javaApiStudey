package com.test.day01;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Random;

public class RequestDemo {

    @Test
    public void registerLogin(){
        // ===========发起注册==========
        String jsonData = "{\"mobile_phone\":\"18102222174\",\"pwd\":\"lemon123456\",\"type\":1}";
        Response res1 = RestAssured.
                // given：设置 测试预设（包括请求头、请求参数、请求体、cookies 等等）
                        given().
                // header：请求头
                        header("X-Lemonban-Media-Type","lemonban.v1").
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
        // 从注册的响应结果中获取成功注册的手机号
        Object mobilePhone = res1.jsonPath().get("data.mobile_phone");
        System.out.println( "手机号为: " + mobilePhone);

        // ===========发起登录==========
        String jsonData2 = "{\"mobile_phone\":\"18102222174\",\"pwd\":\"lemon123456\"}";
        Response res2 =RestAssured.
                // given：设置 测试预设（包括请求头、请求参数、请求体、cookies 等等）
                        given().
                // header：请求头
                        header("X-Lemonban-Media-Type","lemonban.v2").
                        contentType("application/json").
                // body：请求体
                        body(jsonData2).
                //when ：所要执行的操作（GET/POST 请求）
                        when().
                        post("http://api.lemonban.com/futureloan/member/login").
                //then 解析结果、断言
                        then().
                        log().all().
                // extract().response():获取响应报文
                        extract().response();
        // 获取token值
        Object token = res2.jsonPath().get("data.token_info.token");
        Object userId = res2.jsonPath().get("data.id");
        System.out.println("token为： " + token + "，用户id为： " + userId);

        // ===========发起充值==========
        String jsonData3 = "{\"member_id\":\"" + userId + "\",\"amount\":\"10000\"}";
        RestAssured.
                // given：设置 测试预设（包括请求头、请求参数、请求体、cookies 等等）
                        given().
                // header：请求头
                        header("X-Lemonban-Media-Type","lemonban.v2").
                contentType("application/json").
                header("Authorization","Bearer " +token).
                // body：请求体
                        body(jsonData3).
                //when ：所要执行的操作（GET/POST 请求）
                        when().
                post("http://api.lemonban.com/futureloan/member/recharge").
                //then 解析结果、断言
                        then().
                log().all().
                // extract().response():获取响应报文
                        extract().response();



    }

    public static void main(String[] args) {
        Random ra = new Random();
        int i = ra.nextInt(1000);
        String phone = "18102222" + i;
        String pwd = "lemon123456";
    }

}
