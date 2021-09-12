package com.lemon.testCases;

import com.alibaba.fastjson.JSONObject;
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


/*
    接口用例（excel）中sheet之间要保证没有关联，方便维护，防止出现问题
    一个sheet写一个请求（类），方便维护
 */

/**
 * 登录功能模块的测试类
 */
public class LoginTest extends BaseTest {

    @BeforeClass
    public void before(){
        //生成一个没有被使用过的手机号
        String phone = PhoneRandomUtil.getUnregisterPhone();
        //将该手机号存入到环境变量中，注意：这里的随机手机号的变量名与用例中的手机号的变量名保持一致，不然会报错
        Environment.envMap.put("phone",phone);

        //只需要读取第一条数据
        List<CasePojo> datas = ExcelUtil.readExcelSpecifyDatas(2, 1, 1);
        CasePojo cp = datas.get(0);
        //将环境变量中的手机号替换到用例中
        cp = paramsReplace(cp);
        // 发起【注册】请求
        Response res = request(cp);
        //获取响应数据中有用的值
       /*
        String mobilephone = res.jsonPath().get("data.mobile_phone");
        //将手机号保存到环境变量中
        Environment.envMap.put("mobile_phone",mobilephone);
        */
        extractToEnvironment(res,cp);
    }



    /**
     * 发起接口请求
     * @param cp 数据驱动的对象
     */
    @Test(dataProvider = "getLoginDatas")
    public void login(CasePojo cp) {    // 形参为数据源中的一维数组里数据的数据类型
        //替换参数
        cp = paramsReplace(cp);
        //发起接口请求
        Response res = request(cp);
        //断言
        assertResponse(cp,res);
    }


    /**
     * 将读取到的excel数据转化为数组
     * @return Object[]数组
     */
    // @DataProvider 数据提供源
    @DataProvider
    public Object[] getLoginDatas(){
        List<CasePojo> listDatas = ExcelUtil.readExcelSpecifyDatas(2,2);
        // list集合转为数组
//        Object[] datas = listData.toArray();
//        return datas;
        // 简化后为：
        return listDatas.toArray();
    }

}
