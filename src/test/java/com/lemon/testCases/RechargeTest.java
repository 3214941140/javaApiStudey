package com.lemon.testCases;

import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.pojo.CasePojo;
import com.lemon.utils.ExcelUtil;
import com.lemon.utils.PhoneRandomUtil;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class RechargeTest extends BaseTest {

    @BeforeClass
    public void beforeClass(){
        //生成随机的手机号码
        String phone = PhoneRandomUtil.getUnregisterPhone();
        //将手机号码添加到环境变量中
        Environment.envMap.put("phone",phone);
        //读取前置条件
        List<CasePojo> listData = ExcelUtil.readExcelSpecifyDatas(4, 1, 2);
        //调用封装的前置条件方法发起请求
        precondition(listData);
    }

    @Test(dataProvider = "getRechargeData")
    public void recharge(CasePojo cp){
        //参数替换
        cp = paramsReplace(cp);
        //发起请求
        Response res = request(cp);
        //断言
        assertResponse(cp,res);

    }

    @DataProvider
    public Object[] getRechargeData(){
        //读取充值用例
        List<CasePojo> listData = ExcelUtil.readExcelSpecifyDatas(4, 3);
        //将list转为数组
        return listData.toArray();
    }
}
