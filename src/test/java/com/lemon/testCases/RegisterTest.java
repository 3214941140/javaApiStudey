package com.lemon.testCases;

import com.alibaba.fastjson.JSONObject;
import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.pojo.CasePojo;
import com.lemon.utils.ExcelUtil;
import com.lemon.utils.JDBCUtils;
import com.lemon.utils.PhoneRandomUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;


/**
 * 注册功能模块的测试类
 */
public class RegisterTest extends BaseTest {

    @BeforeClass
    public void beforeClass(){
        //生成随机的手机号
        String phone1 = PhoneRandomUtil.getUnregisterPhone();
        String phone2 = PhoneRandomUtil.getUnregisterPhone();
        String phone3 = PhoneRandomUtil.getUnregisterPhone();
        String phone4 = PhoneRandomUtil.getUnregisterPhone();
        Environment.envMap.put("phone1",phone1);
        Environment.envMap.put("phone2",phone2);
        Environment.envMap.put("phone3",phone3);
        Environment.envMap.put("phone4",phone4);
    }

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
        //响应断言
        assertResponse(cp,res);
        //数据库断言
        String dbAssert = cp.getDbAssert();
        if(dbAssert != null) {
            // 转成map
            Map<String, Object> map = JSONObject.parseObject(dbAssert);
            // 遍历map，进行断言
            Set<String> keys = map.keySet();
            for (String key : keys) {
                //key 就是执行的sql语句
                // value就是数据库断言的期望值
                // 获取期望结果
                Integer expectedValue = (Integer) map.get(key);
                long ev = expectedValue.longValue();
                // 获取实际的响应结果
                Object actualValue = JDBCUtils.querySingleData(key);
                //断言
                Assert.assertEquals(actualValue, ev);
            }
        }
    }

    /**
     * 将读取到的excel数据转化为数组
     * @return Object[]数组
     */
    // @DataProvider 数据提供源
    @DataProvider
    public Object[] getRegisterDatas(){
        List<CasePojo> listDatas = ExcelUtil.readExcelSheetAllDatas(1);
        // list集合转为数组
//        Object[] datas = listData.toArray();
//        return datas;
        // 简化后为：
        return listDatas.toArray();
    }

    /**
     * 如果后的接口会受到影响的话可以使用，一般是不需要的
     */
//    @AfterTest
//    public void teardown(){
//        //清空掉环境变量中的数据
//        Environment.envMap.clear();
//    }

}
