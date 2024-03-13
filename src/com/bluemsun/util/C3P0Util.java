package com.bluemsun.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class C3P0Util {

    private static final ComboPooledDataSource DATA_SOURCE = new ComboPooledDataSource();//为空代表使用默认配置

    public static Connection getConnection() {
        Connection conn;
        try {
            conn = DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static void releaseConnection(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if(resultSet!=null){
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connection!=null){
                connection.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}