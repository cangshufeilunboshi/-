package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

public class test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "123456";
        //3.连接成功，数据库对象 Connection
        Connection connection = DriverManager.getConnection(url, username, password);
        //4.执行SQL对象Statement，执行SQL的对象
        Statement statement = connection.createStatement();
        //5.执行SQL的对象去执行SQL，返回结果集
        //String sql = "SELECT *FROM studentinfo;";
        //String sqls = "SELECT * FROM users where username=2019211909127 and password=288718";

        String sqlss = "insert into users(username,password,can) values('2019211909125',123456,'1')";
      int a= statement.executeUpdate(sqlss);
      System.out.println(a);

    }
}
