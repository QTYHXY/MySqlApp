package com.huoxinyu.mysql.common;


import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义通用工具类
 */
public class CommonUtils {

    /**
     * 获取当前时间的字符串
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateStrFromNow(){
        SimpleDateFormat  df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 从日期时间中获取时间字符串
     * @param dt 日期时间
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getStrFromNow(Date dt){
        SimpleDateFormat  df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(dt);
    }

    /**
     * 显示短消息
     * @param content 上下文
     * @param msg 显示的消息
     */
    public static void showShortMsg(Context content, String msg){
        Toast.makeText(content,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示长对话框
     * @param content 上下文
     * @param msg 显示的消息
     */
    public static void showLongMsg(Context content, String msg){
        Toast.makeText(content,msg,Toast.LENGTH_LONG).show();
    }

    /**
     * 显示消息对话框
     * @param context
     * @param msg
     */
    public static void showDLgMsg(Context context,String msg){
        new AlertDialog.Builder(context)//弹窗消息
                .setTitle("提示信息")
                .setMessage(msg)
                .setPositiveButton("确定",null)
                .setNegativeButton("取消",null)
                .create().show();
    }
}
