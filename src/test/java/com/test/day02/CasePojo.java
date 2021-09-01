package com.test.day02;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Objects;

/**
 * 基础类：excel的数据
 */
public class CasePojo {
    // @Excel是easypoi中的注解
    @Excel(name="序号")
    private int caseId;

    @Excel(name="接口模块")
    private String interfaceName;

    @Excel(name="用例标题")
    private String title;

    @Excel(name="请求头")
    private String header;

    @Excel(name="请求方式")
    private String method;

    @Excel(name="接口地址")
    private String url;

    @Excel(name="参数输入")
    private String parameter;

    @Excel(name="期望结果")
    private String expected;

    public CasePojo() {
    }

    public CasePojo(int caseId, String interfaceName, String title, String header, String method, String url, String parameter, String expected) {
        this.caseId = caseId;
        this.interfaceName = interfaceName;
        this.title = title;
        this.header = header;
        this.method = method;
        this.url = url;
        this.parameter = parameter;
        this.expected = expected;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    @Override
    public String toString() {
        return "CasePojo{" +
                "caseId=" + caseId +
                ", interfaceName=" + interfaceName +
                ", title=" + title +
                ", header=" + header +
                ", method=" + method +
                ", url=" + url +
                ", parameter=" + parameter +
                ", expected=" + expected +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CasePojo casePojo = (CasePojo) o;
        return caseId == casePojo.caseId &&
                interfaceName == casePojo.interfaceName &&
                title == casePojo.title &&
                header == casePojo.header &&
                method == casePojo.method &&
                url == casePojo.url &&
                parameter == casePojo.parameter &&
                expected == casePojo.expected;
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseId, interfaceName, title, header, method, url, parameter, expected);
    }
}
