package com.test.day01.honeWork;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.File;

/**
 * 作业一： 通过REST-assured练习get/post/put/delete请求，练习地址：http://www.httpbin.org/
 */

public class HomeWork1 {

    @Test
    public void getRequest1(){
        RestAssured.given().
                contentType("application/json").
                when().
                get("http://www.httpbin.org/get?id=1&name=333").
                then().
                log().
                all();
    }

    @Test
    public void getRequest2(){
        RestAssured.given().
                contentType("application/x-www-form-urlencoded").
                queryParam("id",1).
                queryParam("name","sss").
                when().
                get("http://www.httpbin.org/get").
                then().
                log().
                all();
    }


    @Test
    public void postRequest1(){
        RestAssured.given().
                contentType("application/x-www-form-urlencoded").
                body("id=2&name=jjj").
                when().
                post("http://www.httpbin.org/post").
                then().
                log().
                all();
    }

    @Test
    public void postRequest2(){
        String jsonData = "{\"id\":\"1\",\"name\":\"ssss\"}";
        RestAssured.given().
                contentType("application/json").
                body(jsonData).
                when().
                post("http://www.httpbin.org/post").
                then().
                log().
                all();
    }

    @Test
    public void postRequest3(){
        String xmlStr =  "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<suite>\n" +
                "   <class>作业</class>\n" +
                "</suite>";
        RestAssured.given().
                contentType("application/xml").
                body(xmlStr).
                when().
                post("http://www.httpbin.org/post").
                then().
                log().
                all();
    }

    @Test
    public void postRequest4(){
        File file = new File("C:\\Users\\王胖子\\Desktop\\作业.docx");
        RestAssured.
                given().
                contentType("multipart/form-data").
                multiPart(file).
                when().
                        post("http://www.httpbin.org/post").
                then().
                log().all();
    }

    @Test
    public void putRequest1(){
        RestAssured.given().
                contentType("application/x-www-form-urlencoded").
                body("id=2&name=jjj").
                when().
                put("http://www.httpbin.org/put").
                then().
                log().
                all();
    }

    @Test
    public void putRequest2(){
        RestAssured.given().
                contentType("application/x-www-form-urlencoded").
                when().
                put("http://www.httpbin.org/put?id=2&name=jjj").
                then().
                log().
                all();
    }

    @Test
    public void deleteRequest1(){
        RestAssured.given().
                contentType("application/x-www-form-urlencoded").
                when().
                delete("http://www.httpbin.org/delete?id=2&name=jjj").
                then().
                log().
                all();
    }

    @Test
    public void deleteRequest2(){
        RestAssured.given().
                contentType("application/x-www-form-urlencoded").
                body("id=2&name=jjj").
                when().
                delete("http://www.httpbin.org/delete").
                then().
                log().
                all();
    }

    @Test
    public void deleteRequest3(){
        RestAssured.given().
                contentType("application/x-www-form-urlencoded").
                queryParam("id",2).
                when().
                delete("http://www.httpbin.org/delete").
                then().
                log().
                all();
    }
}
