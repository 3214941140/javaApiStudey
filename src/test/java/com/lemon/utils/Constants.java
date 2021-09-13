package com.lemon.utils;

public class Constants {
    // 项目访问地址
    public static final String PROJECT_URL = "http://api.lemonban.com";
    // 项目的baseUrl地址
    public static final String BASE_URL = PROJECT_URL + "/futureloan";
    //用例excel文件地址
    public static final String EXCEL_PATH = "src/test/resources/TestCase.xlsx";
    //数据库baseURI
    public static final String DB_BASE_URI="api.lemonban.com";
    //数据库名
    public static final String DB_NAME = "futureloan";
    //数据库地址
    public static final String DB_BASE_URL ="jdbc:mysql://" + DB_BASE_URI + DB_NAME + "/?useUnicode=true&characterEncoding=utf-8";
    //数据库账号
    public static final String DB_USERNAME="future";
    //数据库密码
    public static final String DB_PWD="123456";

}
