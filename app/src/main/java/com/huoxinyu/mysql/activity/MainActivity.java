package com.huoxinyu.mysql.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huoxinyu.mysql.R;
import com.huoxinyu.mysql.bean.Userinfo;
import com.huoxinyu.mysql.common.CommonUtils;
import com.huoxinyu.mysql.connect.MySqlHelp;
import com.huoxinyu.mysql.dao.UserDao;
import com.huoxinyu.mysql.dao.UserManagerActicity;

/**
 * 登录界面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_query_count, btn_login;//查询测试按钮，登录按钮
    private TextView tv_user_count, tv_ifOk;//用户数量、显示连接情况


    private EditText et_uname, et_upass;//用户名，密码
    public static EditText et_ip;

    private UserDao dao;//数据库操作
    private Handler mainHandler;//主线程


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        btn_query_count = findViewById(R.id.btn_query_count);
        tv_user_count = findViewById(R.id.tv_user_count);
        tv_ifOk = findViewById(R.id.tv_ifOk);
        et_ip = findViewById(R.id.et_ip);

        et_uname = findViewById(R.id.et_uname);
        et_upass = findViewById(R.id.et_upass);
        btn_login = findViewById(R.id.btn_login);

        dao = new UserDao();//数据库操作对象
        mainHandler = new Handler(getMainLooper());       //获取主线程

        btn_login.setOnClickListener(this);//设置点击监听器
        btn_query_count.setOnClickListener(this);
    }


    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query_count:
                doQueryCount();
                break;
            case R.id.btn_login:
                doLogin();
                break;
        }
    }

    /**
     * 登录操作
     */
    private void doLogin() {
        final String uname = et_uname.getText().toString().trim();//获取内容转字符串去除字符串两端的空格
        final String upass = et_upass.getText().toString().trim();
        final String ip = et_ip.getText().toString().trim();//获取内容转字符串去除字符串两端的空格
        Log.i("35", uname);
        Log.i("35", upass);

        if (TextUtils.isEmpty(uname)) {//检查是不是空
            CommonUtils.showShortMsg(this, "请输入用户名");
            et_uname.requestFocus();//获取焦点，请求输入
        } else if (TextUtils.isEmpty(upass)) {
            CommonUtils.showShortMsg(this, "请输入密码");
            et_upass.requestFocus();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //final Userinfo item = dao.getUserByUnameAndUpass(uname,upass);
                    mainHandler.post(new Runnable() {//传递一个Runnable对象作为参数来在主线程中执行后台任务
                        @Override
                        public void run() {
                            if (uname.equals("2004413235") && upass.equals("2004413235")) {
                                CommonUtils.showLongMsg(MainActivity.this, "登录成功");
                                //CommonUtils.showDLgMsg(MainActivity.this,"登录成功");
                                Intent intent = new Intent(MainActivity.this, UserManagerActicity.class);
                                startActivity(intent);
                            } else {
                                CommonUtils.showDLgMsg(MainActivity.this, "用户名或密码错误");
                            }
                        }
                    });
                }
            }).start();
        }
    }


    /**
     * 测试数据库是否连接
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                int count = (Integer) msg.obj;
                tv_user_count.setText("数据库中的用户数量为：" + count);
                tv_ifOk.setText("连接成功");
            }
            if (msg.what == 0) {
                tv_user_count.setText("数据库中的用户数量查询失败");
                tv_ifOk.setText("连接失败");
            }
        }
    };

    /**
     * 执行查询用户数量的方法并发送到主线程
     */
    private void doQueryCount() {
        final String ip = et_ip.getText().toString().trim();//获取内容转字符串去除字符串两端的空格
        //创建线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取数据库内容数量
                int count = MySqlHelp.getUserSize(ip);
                if (count != 0) {
                    //创建消息
                    Message msg = Message.obtain();//避免重复创建对象
                    msg.what = 1;//标志
                    msg.obj = count;
                    //向主线程发送数据
                    handler.sendMessage(msg);
                }
                if (count == 0) {
                    Message msg = Message.obtain();//避免重复创建对象
                    msg.what = 0;//标志
                    msg.obj = "失败";
                    //向主线程发送数据
                    handler.sendMessage(msg);
                }
            }
        }).start();//开启线程
    }
}