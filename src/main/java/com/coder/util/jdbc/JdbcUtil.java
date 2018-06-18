package com.coder.util.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtil {

    private static final String url = "jdbc:mysql://localhost:3306/happy-code";
    private static final String name = "root";
    private static final String pass = "root";

    private JdbcUtil(){}

    private static JdbcUtil jdbcUtil = null;

    public static JdbcUtil getJdbcUtil(){
        if(jdbcUtil==null){
            return new JdbcUtil();
        }
        return jdbcUtil;
    }

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void update(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(url,name,pass);
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement);
        }
    }

    public static List query(String sql){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List list = new ArrayList();
        try {
            connection = DriverManager.getConnection(url,name,pass);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Map<String,Object> map = new HashMap<>();//map中存的是一行数据
                //使用结果集元数据得到列名
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnCount = rsmd.getColumnCount();//得到列的总数
                for (int i = 1;i<=columnCount;i++){
                    map.put(rsmd.getColumnLabel(i),resultSet.getObject(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement,resultSet);
        }
        return list;
    }

    public static void close(Connection connection,Statement statement){
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(Connection connection,Statement statement,ResultSet resultSet){
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
