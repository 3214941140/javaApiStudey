package com.test.day02;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Objects;

/**
 * 基础类：excel的数据
 */
public class CasePojo {

    @Excel(name="序号")
    private int caseId;

    @Excel(name="接口模块")
    private int interfaceName;

    @Excel(name="用例标题")
    private int title;

    @Excel(name="请求头")
    private int header;

    @Excel(name="请求方式")
    private int method;

    @Excel(name="接口地址")
    private int url;

    @Excel(name="参数输入")
    private int parameter;

    @Excel(name="期望结果")
    private int expect;

    public CasePojo() {
    }

    public CasePojo(int caseId, int interfaceName, int title, int header, int method, int url, int parameter, int expect) {
        this.caseId = caseId;
        this.interfaceName = interfaceName;
        this.title = title;
        this.header = header;
        this.method = method;
        this.url = url;
        this.parameter = parameter;
        this.expect = expect;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public int getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(int interfaceName) {
        this.interfaceName = interfaceName;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public int getParameter() {
        return parameter;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }

    public int getExpect() {
        return expect;
    }

    public void setExpect(int expect) {
        this.expect = expect;
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
                ", expect=" + expect +
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
                expect == casePojo.expect;
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseId, interfaceName, title, header, method, url, parameter, expect);
    }
}
