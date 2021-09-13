package com.lemon.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JDBCUtils {

    /**
     * 和数据库建立连接
     * @return conn 数据库连接对象
     */
    public static Connection getConnection() {
        //定义数据库连接
        //Oracle：jdbc:oracle:thin:@localhost:1521:DBName
         //SqlServer：jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=DBName
        //MySql：jdbc:mysql://localhost:3306/DBName
        //定义数据库连接对象
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Constants.DB_BASE_URL, Constants.DB_USERNAME,Constants.DB_PWD);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }


    /**
     * 关闭数据库连接
     * @param conn 数据库连接对象
     */
    public static void closeConnection(Connection conn){
        //判空
        if(null != conn){
            //关闭资源
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * sql的更新操作（增删改）
     * @param sql 要执行的sql语句
     */
    public static void update(String sql){
        //1、建立数据库连接
        Connection conn = getConnection();
        //2、实例化数据库操作对象
        QueryRunner qr = new QueryRunner();
        try {

            qr.update(conn,sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //关闭资源
            closeConnection(conn);
        }
    }

    /**
     * 查询所有的结果集
     * @param sql 要执行的sql语句
     * @return 返回的结果集
     */
    public static List<Map<String, Object>> queryAll(String sql){
        //1、建立数据库连接
        Connection conn = getConnection();
        //2、实例化数据库操作对象
        QueryRunner qr = new QueryRunner();
        List<Map<String, Object>> result = null;
        try {
            //查询要有返回结果
            result = qr.query(conn, sql, new MapListHandler());
            System.out.println(result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //关闭资源
            closeConnection(conn);
        }
        return result;
    }

    /**
     * 查询结果集中的第一条
     * @param sql 要执行的sql语句
     * @return 返回的结果集
     */
    public static Map<String, Object> queryOne(String sql){
        //1、建立数据库连接
        Connection conn = getConnection();
        //2、实例化数据库操作对象
        QueryRunner qr = new QueryRunner();
        Map<String, Object> result = null;
        try {
            //查询要有返回结果
            result = qr.query(conn, sql, new MapHandler());
            System.out.println(result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //关闭资源
            closeConnection(conn);
        }
        return result;
    }

    /**
     * 查询单条的数据
     * @param sql 要执行的sql语句
     * @return 返回的结果集
     */
    public static Object querySingleData(String sql){
        //1、建立数据库连接
        Connection conn = getConnection();
        //2、实例化数据库操作对象
        QueryRunner qr = new QueryRunner();
        Object result = null;
        try {
            //查询要有返回结果
            result = qr.query(conn, sql, new ScalarHandler<>());
            System.out.println(result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //关闭资源
            closeConnection(conn);
        }
        return result;
    }

    public static void main(String[] args) {
        //1、建立数据库连接
        Connection conn = getConnection();
        //2、实例化数据库操作对象
        QueryRunner qr = new QueryRunner();
      /*
        //插入数据
        String sql = "insert into member values (1236899999,'长颜君','1b7570b3e6b2ecf6a99ef133da2303a6','19186974545',1,1000.0,'2021-09-07 17:48:30')";
        //修改数据
        String sql = "update member set reg_name ='丸子' where id = 1236899999;";
        //3、对数据库进行更新操作
        try {

            qr.update(conn,sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
       */

        // 4、查询数据
        // 查询某一条数据使用MapHandler：将结果集中第一条记录封装到Map<String,Object>中
        /*
        String sql = "select * from member where id = 1236899999;";
        try {
            //查询要有返回结果
            Map<String, Object> result = qr.query(conn, sql, new MapHandler());
            System.out.println(result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        */

        //查询多条数据：MapListHandler 将结果集中每一条记录封装到Map<String,Object>中
        /*
        String sql = "select * from member where id < 10;";
        try {
            //查询要有返回结果
            List<Map<String, Object>> result = qr.query(conn, sql, new MapListHandler());
            System.out.println(result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
         */

        //统计数据:ScalarHandler 用于单个数据
        String sql = "select count(*) from member where id < 10;";
        try {
            //查询要有返回结果
            Object result = qr.query(conn, sql, new ScalarHandler<Object>());
            System.out.println(result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            closeConnection(conn);
        }
    }

}
