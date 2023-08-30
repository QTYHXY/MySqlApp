package com.huoxinyu.mysql.dao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.huoxinyu.mysql.R;
import com.huoxinyu.mysql.bean.Userinfo;
import com.huoxinyu.mysql.common.CommonUtils;
import com.huoxinyu.mysql.dao.UserDao;

/**
 * 添加用户
 */
public class UserAddActivity extends AppCompatActivity {

    private EditText et_uname, et_upass;
    private UserDao userDao;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        et_uname = findViewById(R.id.et_uname);
        et_upass = findViewById(R.id.et_upass);
        userDao = new UserDao();
        mainHandler = new Handler(getMainLooper());
    }

    public void btn_ok_click(View view) {
        final String uname = et_uname.getText().toString().trim();
        final String upass = et_upass.getText().toString().trim();
        if (TextUtils.isEmpty(uname)) {
            CommonUtils.showShortMsg(this, "请输入用户名");
            et_uname.requestFocus();
        } else if (TextUtils.isEmpty(upass)) {
            CommonUtils.showShortMsg(this, "请输入密码");
            et_upass.requestFocus();
        } else {
            final Userinfo item = new Userinfo();

            item.setUname(uname);
            item.setUpass(upass);
            item.setCreatDt(CommonUtils.getDateStrFromNow());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    final int iRow = userDao.addUser(item);
                    int temp = iRow;
                    Log.i("User成功改变行数", String.valueOf(temp));
                    Log.i("Useruname", uname);
                    Log.i("Userupass", upass);
                    Log.i("User时间", CommonUtils.getDateStrFromNow());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            CommonUtils.showShortMsg(UserAddActivity.this, "添加成功");
                            setResult(1);//表示当前界面操作成功，并返回主界面
                            finish();
                        }
                    });
                }
            }).start();
        }
    }
}