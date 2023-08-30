package com.huoxinyu.mysql.dao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.huoxinyu.mysql.R;
import com.huoxinyu.mysql.bean.Userinfo;
import com.huoxinyu.mysql.common.CommonUtils;
import com.huoxinyu.mysql.dao.UserDao;

/*
        修改用户界面
 */
public class UserEditActivity extends AppCompatActivity {
    private EditText et_uname, et_upass;
    private TextView tv_createDt;
    private UserDao userDao;
    private Userinfo userEdit;//当前要修改的用户信息
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        et_uname = findViewById(R.id.et_uname);
        et_upass = findViewById(R.id.et_upass);
        tv_createDt = findViewById(R.id.tv_createDt);

        Bundle bundle = getIntent().getExtras();//数据传输
        if (bundle != null) {
            userEdit = (Userinfo) bundle.getSerializable("userEdit");//从Bundle中获取Serializable数据
            et_uname.setText(userEdit.getUname());
            et_upass.setText(userEdit.getUpass());
            tv_createDt.setText(userEdit.getCreatDt());
        }
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

            userEdit.setUname(uname);
            userEdit.setUpass(upass);
            userEdit.setCreatDt(CommonUtils.getDateStrFromNow());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    final int iRow = userDao.editUser(userEdit);
                    if (iRow != 0) {
                        Log.i("修改状态", "修改成功");
                    }
                    Log.i("User成功改变行数", String.valueOf(iRow));
                    Log.i("Useruname", uname);
                    Log.i("Userupass", upass);
                    Log.i("User时间", CommonUtils.getDateStrFromNow());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            CommonUtils.showShortMsg(UserEditActivity.this, "修改成功");
                            setResult(1);
                            finish();
                        }
                    });
                }
            }).start();
        }
    }
}