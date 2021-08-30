package com.test.day01.honeWork;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Random;

/**
 * 作业二：通过REST-assured完成业务流自动化实现：注册->登录->充值业务流程（投资人），注：使用lemonban.v2鉴权方式
 */
public class HomeWork2 {

    @Test
    public void request(){
        Random ra = new Random();
        int i = ra.nextInt(10000);
        String phone  = "1818697"+i;
        String pwd = "lemon123456";
        int type = 1;
        double amount = 10000.0;
        String header1 = "X-Lemonban-Media-Type";
        String header1Value1 = "lemonban.v1";
        String header1Value2 = "lemonban.v2";
        String header2Value = "application/json";
        String header3 ="Authorization";
        String header3Value = "Bearer ";
        String url = "http://api.lemonban.com/futureloan";

        // 发起注册
        String jsonData = "{\"mobile_phone\":\"" + phone +"\",\"pwd\":\""+pwd+"\",\"type\":\""+type+"\"}";
        Response res1 = RestAssured.given().
                header(header1, header1Value1).
                contentType(header2Value).
                body(jsonData).
                when().
                post(url+"/member/register").
                then().
                log().all().
                extract().response();
        // 获取用户id
        Object userId = res1.jsonPath().get("data.id");
        // 获取手机号
        Object mobilePhone = res1.jsonPath().get("data.mobile_phone");

        // 发起登录
        String jsonData2 = "{\"mobile_phone\":\"" + mobilePhone +"\",\"pwd\":\""+pwd+"\"}";
        Response res2 = RestAssured.given().
                header(header1, header1Value1).
                contentType(header2Value).
                body(jsonData2).
                when().
                post(url+"/member/login").
                then().
                log().all().
                extract().response();
        // 获取token
        Object token = res2.jsonPath().get("data.token_info.token");


        // 发起充值
        String jsonData3 = "{\"member_id\":\"" + userId +"\",\"amount\":\""+amount+"\"}";
        RestAssured.given().
                header(header1, header1Value1).
                contentType(header2Value).
                header(header3,header3Value + token).
                body(jsonData3).
                when().
                post(url+ "/member/recharge").
                then().
                log().all().
                extract().response();
    }

}
