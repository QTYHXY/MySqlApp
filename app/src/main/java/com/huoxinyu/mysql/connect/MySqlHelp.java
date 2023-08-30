package com.huoxinyu.mysql.connect;


import android.util.Log;
import android.widget.EditText;

import com.huoxinyu.mysql.R;
import com.huoxinyu.mysql.activity.MainActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 测试数据是否连接
 */
public class MySqlHelp {
//    public EditText et_ip;
//    public String getip(){
//        et_ip= findViewById(R.id.et_ip);
//        final String ip=et_ip.getText().toString().trim();
//        Log.i("ip111111111111111111111111111111111111111111",ip);
//        return ip;
//    }

    public static int getUserSize(String ip) {
        final String CLS = "com.mysql.jdbc.Driver";
        final String URL = "jdbc:mysql://" + ip + ":3306/bookdb";
        final String USER = "conuse";
        final String PWD = "123456";

        int count = 0;//用户数量

        try {
            Class.forName(CLS);
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            String sql = "select count(1) as s1 from userinfo";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                count = rs.getInt("s1");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }



}
