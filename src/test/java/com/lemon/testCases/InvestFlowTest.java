package com.lemon.testCases;

import com.alibaba.fastjson.JSONObject;
import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.pojo.CasePojo;
import com.lemon.utils.ExcelUtil;
import com.lemon.utils.JDBCUtils;
import com.lemon.utils.PhoneRandomUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class InvestFlowTest extends BaseTest {

    /**
     * 在用例执行之前先执行前置条件
     */
    @BeforeClass
    public void beforeClass(){
        //生成三个随机的手机号码
        String investorPhone = PhoneRandomUtil.getUnregisterPhone();
        String borrowerPhone = PhoneRandomUtil.getUnregisterPhone();
        String adminPhone = PhoneRandomUtil.getUnregisterPhone();
        Environment.envMap.put("investorPhone",investorPhone);
        Environment.envMap.put("borrowerPhone",borrowerPhone);
        Environment.envMap.put("adminPhone",adminPhone);
        //前置条件
        List<CasePojo> listData = ExcelUtil.readExcelSpecifyDatas(3, 1, 9);
        // 调用前置条件发起请求
        precondition(listData);
    }


    /**
     * 接口请求的方法
     * @param cp 测试用例的实体类对象
     */
    @Test(dataProvider = "getDatas")
    public void testCase(CasePojo cp){
        // 替换参数
        cp =paramsReplace(cp);
        //发起请求
        Response res = request(cp);

        // 断言
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
     * 通过testng DataProvider 实现数据驱动
     * 将读取到的excel数据转为数组
     * @return Object[] excel的数据
     */
    @DataProvider
    public Object[] getDatas(){
        List<CasePojo> datas = ExcelUtil.readExcelSpecifyDatas(3,1);
        //datas为list，返回的要求必须是数组，所以必须要把list转成数组
        return datas.toArray();
    }
}
