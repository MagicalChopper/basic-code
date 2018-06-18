package com.coder.util.jdbc;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//实现对c3p0提供的连接池的管理
public class C3P0JdbcUtil {
    private static Log log = LogFactory.getLog(C3P0JdbcUtil.class);

    //ComboPooledDataSource是由C3P0提供的数据源类
    private static ComboPooledDataSource dataSource;
    //本地线程管理对象,作用:确保同一个线程获取的connection是同一个
    //目的是提高连接的使用率,一次请求多次使用
    private static ThreadLocal<Connection> t = new ThreadLocal<>();

    //静态块确保先执行,并且只运行一次
    static {
        //读取默认的c3p0数据配置文件创建数据源
        try{
            dataSource = new ComboPooledDataSource();//使用默认的配置项创建数据源
        } catch (Exception e){
            log.error("C3P0数据源初始化错误"+e.getMessage());
        }
    }

    public static Connection getConn(){
        //先从当前运行的线程中获取连接,如果当前运行的线程中没有可以使用的
        //连接,就到连接池中拿,拿到后把连接放入到当前线程中
        Connection conn = t.get();//t表示当前运行的线程,谁调用jdbcUtil,谁就是当前线程
        if (conn == null) {
            try{
                conn = dataSource.getConnection();
                t.set(conn);//将从连接池中获取的连接放入当前线程中
            } catch(Exception e) {
                //这个地方获取不到连接是一个比较严重的异常事件
                throw new RuntimeException(e.getMessage(),e);//抛出异常
            }
        }
        log.info("成功获取到一个连接"+conn);
        return conn;
    }

    public static ComboPooledDataSource getDataSource(){
        return dataSource;
    }
    //关闭连接
    public static void close(Connection conn){

        if (conn != null) {
            try{
                conn.close();
            } catch(SQLException e){
                log.error("关闭conn异常"+e.getMessage());
            } finally{//双保险,确保连接关掉
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        log.error("二次关闭conn异常"+e.getMessage());
                    }
                }
            }
        }
    }

    //关闭预处理,抛出异常,交由调用者来进行异常处理
    public static void close(PreparedStatement pstate) throws SQLException{
        if (pstate != null) {
            pstate.close();
        }
    }

    //关闭结果集
    public static void close(ResultSet rs) throws SQLException{
        if (rs != null) {
            rs.close();
        }
    }
}

