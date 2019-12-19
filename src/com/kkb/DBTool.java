package com.kkb;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBTool {

    //默认参数
    public   String ip = "127.0.0.1";
    public   int port = 3306;
    public   String
            user="root",
            password="admin",
            charset ="utf8",
            dbName="db1";

    private static boolean DriverLoaded=false;

    //使用默认参数链接数据库
    public DBTool() throws ClassNotFoundException {
        if(DriverLoaded)return;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("DBTools message:数据库驱动加载成功!");
        } catch (ClassNotFoundException e) {
            System.out.println("DBTools Error:驱动程序加载失败!");
            throw e;
        }
        DriverLoaded=true;
    }

    //自定义参数初始化
    public DBTool(String ip, int port, String user, String password, String dbName) throws ClassNotFoundException {
        this();
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
        this.dbName = dbName;
    }

    //自定义参数初始化
    public DBTool(String user, String password, String dbName) throws ClassNotFoundException {
        this();
        this.user = user;
        this.password = password;
        this.dbName = dbName;
    }


    //获取一个链接
    public Connection getConnection() throws SQLException {
        String url = String.format("jdbc:mysql://%s:%s/%s?characterEncoding=%s&user=%s&password=%s&useSSL=false",ip,port,dbName,charset,user,password);
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("DBTools Error 数据库连接失败!");
            throw e;
        }
    }

    //执行查询语句
    public List<Map<String,Object>> executeQuery(String sql, Object...args) throws SQLException {
        ArrayList<Map<String, Object>> res = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = getPreparedStatement(connection, sql, args);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultSet.getMetaData().getColumnCount();
                HashMap<String, Object> map = new HashMap<>();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount() ; i++) {
                    map.put(resultSet.getMetaData().getColumnName(i),resultSet.getObject(i));
                }
                res.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(resultSet != null)
                resultSet.close();
            if(preparedStatement != null)
                preparedStatement.close();
            if(connection != null)
                connection.close();
        }
        return res;
    }

    //sql参数预处理
    private PreparedStatement getPreparedStatement(Connection connection, String sql, Object[] args) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int count = sql.length() - sql.replace("?", "").length();
        if(count != args.length){
            throw new SQLException("DBTool Error: 参数个数不匹配");
        }
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i+1,args[i]);
        }
        return preparedStatement;
    }

    //执行更新语句
    public boolean executeUpdate(String sql,Object...args) throws SQLException {

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = getPreparedStatement(connection, sql, args);

            int i = preparedStatement.executeUpdate();
            //如果执行的是删除 更新 ,添加 那么返回的是受影响的行数
            if (i>0){return true;}
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return false;
    }
}