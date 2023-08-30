package com.huoxinyu.mysql.bean;

import android.util.Log;

import com.huoxinyu.mysql.activity.MainActivity;
import com.huoxinyu.mysql.connect.MySqlHelp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/*
MySQL数据库的连接辅助类
 */
public class DbOpenHelper extends MainActivity{

    //    final static String CLS = "com.mysql.jdbc.Driver";
//    final static String CLS = "com.mysql.jdbc.Driver";
//    final static String URL = "jdbc:mysql://"+"192.168.1.126"+":3306/bookdb";
//    //    final static String URL = "jdbc:mysql://localhost:3306/bookdb?useSSL=false&serverTimezone=UTC";
//    final static String USER = "conuse";
//    final static String PWD = "123456";


    public static Connection conn;//连接对象
    public static Statement stmt;//命令集
    public static PreparedStatement pStmt;//预编译命令集
    public static ResultSet rs;//结果集


    /**
     * 取得连接的方法
     */
    public static void getConnection() {
        final String ip= et_ip.getText().toString().trim();
        final String CLS = "com.mysql.jdbc.Driver";
        final String URL = "jdbc:mysql://" + ip + ":3306/bookdb";
        //    final static String URL = "jdbc:mysql://localhost:3306/bookdb?useSSL=false&serverTimezone=UTC";
        final String USER = "conuse";
        final String PWD = "123456";

        try {
            Class.forName(CLS);//动态加载类
            conn = DriverManager.getConnection(URL, USER, PWD);
            Log.i("连接情况", String.valueOf(conn));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 关闭对象，保证程序的安全性
     */
    public static void closeAll() {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (pStmt != null) {
                pStmt.close();
                pStmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
