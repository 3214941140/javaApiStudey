package com.lemon.utils;

import java.util.Random;

public class PhoneRandomUtil {
    public static void main(String[] args) {
        //思路1、先查询手机号码字段、按照倒叙排列，取得最大的数据号码+1
        //思路2、先生成一个随机的手机号，在通过该号码进入到数据库中查询，如果查询有记录，在生成一个新的，否则说明改手机号没有被使用过
        getUnregisterPhone();
    }

    public static String getRandomPhone(){
        Random ran = new Random();
        //nextInt：随机生成一个整数，如果输入一个10，生成的随机数就是0-9。
        String phonePrefix = "155";
        //生成八位的随机整数
        // int num = ran.nextInt(100000000 + 1);  //经常生成不是8位数的数
        for (int i = 0; i < 8; i++) {
            int num = ran.nextInt(10);
            phonePrefix = phonePrefix + num;
        }
        return phonePrefix;
    }

    public static String getUnregisterPhone(){
        String phone = "";
        while(true){
            phone = getRandomPhone();
            // 查询数据库
            Object result = JDBCUtils.querySingleData("select count(*) from member where mobile_phone = '" + phone + "';");
            if (0 == (Long)result) {
                //表示没有使用过，符合需求
                break;
            }else{
                //表示使用过，需要在执行上面的过程
                continue;
            }
        }
        return phone;
    }
}
