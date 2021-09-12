package com.lemon.testCases;

import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.pojo.CasePojo;
import com.lemon.utils.ExcelUtil;
import com.lemon.utils.PhoneRandomUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;


/**
 * 注册功能模块的测试类
 */
public class RegisterTest extends BaseTest {

    /**
     * 发起接口请求
     * @param cp 数据驱动的对象
     */
    @Test(dataProvider = "getRegisterDatas")
    public void register(CasePojo cp) {    // 形参为数据源中的一维数组里数据的数据类型
        //参数替换
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
    public Object[] getRegisterDatas(){
        String phone1 = PhoneRandomUtil.getUnregisterPhone();
        String phone2  = PhoneRandomUtil.getUnregisterPhone();
        String phone3 = PhoneRandomUtil.getUnregisterPhone();
        String phone4 = PhoneRandomUtil.getUnregisterPhone();
        Environment.envMap.put("phone1",phone1);
        Environment.envMap.put("phone2",phone2);
        Environment.envMap.put("phone3",phone3);
        Environment.envMap.put("phone4",phone4);
        List<CasePojo> listDatas = ExcelUtil.readExcelSheetAllDatas(1);
        // list集合转为数组
//        Object[] datas = listData.toArray();
//        return datas;
        // 简化后为：
        return listDatas.toArray();
    }

}
