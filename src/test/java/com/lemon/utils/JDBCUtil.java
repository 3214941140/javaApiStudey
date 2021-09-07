package com.lemon.utils;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

    /**
     *和数据库建立连接
     * @return conn 数据库连接对象
     */
    public static Connection getConnection() {
        //定义数据库连接
        //Oracle：jdbc:oracle:thin:@localhost:1521:DBName
         //SqlServer：jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=DBName
        //MySql：jdbc:mysql://localhost:3306/DBName
        String url="jdbc:mysql://api.lemonban.com/futureloan?useUnicode=true&characterEncoding=utf-8";
        String user="future";
        String password="123456";
        //定义数据库连接对象
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user,password);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        //1、建立连接
        Connection conn = getConnection();
        //2、在用户表里插入对应的数据
        QueryRunner qr = new QueryRunner();
        String sql = "insert into member values (74511,'长颜君','1b7570b3e6b2ecf6a99ef133da2303a6','19186974545',1,1000.0,'2021-09-07 17:48:30')";
        try {
            qr.update(conn,sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
