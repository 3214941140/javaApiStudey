package com.lemon.utils;

import com.lemon.data.Environment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//练习代码
public class RegexTest {
    public static void main(String[] args) {
        String str = "{\"mobile_phone\": \"{{mobile_phone}}\", \"pwd\": \"{{pwd}}\"}";
        //定义正则的原始表达式
        String regxExpr = "\\{\\{(.*?)\\}\\}";
        //匹配器
        Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
        //匹配对象
        Matcher matcher = pattern.matcher(str);
        String result = str;
        //循环遍历匹配的对象
        while(matcher.find()){
            //matcher.group(0)：获取整个匹配正则的字符串  {{mobile_phone}}
            String allFindStr = matcher.group(0);
            System.out.println("找到的完整的字符转：" + allFindStr);
            //matcher.group(1)：找到{{xxx}}内部的匹配的字符串  mobile_phone
            String innerStr = matcher.group(1);
            System.out.println("找到的内部的字符转：" + innerStr);
            //找到手机号：15087714882
            //具体的要替换的值（从环境变量中去找到的）
            Environment.envMap.put("mobile_phone","15087714882");
            Environment.envMap.put("pwd","123456");
            Object replaceValue = Environment.envMap.get(innerStr);
            //要替换的{{mobile_phone}} ---> 15087714882
            //第二次替换的时候应该是基于第一次替换的结果的基础上再去替换
            //解决方案：定义一个字符串变量，用来接收替换会后的字符串，这个变量等于要替换的原始的字符串，如：String result = str;
            result = result.replace(allFindStr, replaceValue + "");
            System.out.println(result);
        }
    }

}
