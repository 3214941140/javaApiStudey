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

public class LoanAddTest extends BaseTest {

    @BeforeClass
    public void beforeClass(){
        // 拿到为注册的手机号
        String phone = PhoneRandomUtil.getUnregisterPhone();
        //添加到环境变量中
        Environment.envMap.put("phone",phone);
        //读取excel中的前置条件
        List<CasePojo> listData = ExcelUtil.readExcelSpecifyDatas(5, 1, 2);
        //调用前置处理数据的方法
        precondition(listData);
    }

    @Test(dataProvider = "getLoanAddData")
    public void loanAdd(CasePojo cp){
        //参数替换
        cp = paramsReplace(cp);
        //发起请求
        Response res = request(cp);
        //断言
        assertResponse(cp,res);
    }

    @DataProvider
    public Object[] getLoanAddData(){
        //拿到excel里面的数据
        List<CasePojo> listData = ExcelUtil.readExcelSpecifyDatas(5, 3);
        //将list数据转为数组
        return listData.toArray();
    }

}
