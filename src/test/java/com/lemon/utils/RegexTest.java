package com.lemon.utils;

//练习代码
public class RegexTest {
    public static void main(String[] args) {
        String str = "{\"mobile_phone\": \"{{mobile_phone}}\", \"pwd\": \"lemon123456\"}";
        //定义正则的原始表达式
        String regxExpr = "\\{\\{(.*?)\\}\\}";
    }
}
