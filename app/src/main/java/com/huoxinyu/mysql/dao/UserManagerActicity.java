package com.huoxinyu.mysql.dao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.huoxinyu.mysql.adapter.LvUserinfoAdapter;
import com.huoxinyu.mysql.R;
import com.huoxinyu.mysql.bean.Userinfo;
import com.huoxinyu.mysql.common.CommonUtils;

import java.util.List;

/*
    用户管理界面代码
 */
public class UserManagerActicity extends AppCompatActivity implements View.OnClickListener {

    private ImageView btn_return, btn_add, btn_deleteall;//图片按钮
    private UserDao userDao;//数据库操作对象
    private List<Userinfo> userinfoList;//用户数据集合
    private LvUserinfoAdapter lvUserinfoAdapter;//用户信息适配器
    private ListView lv_user;//列表组件
    private Handler mainHandler;//主线程


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);

        initView();
        loadUserDb();
    }


    private void initView() {
        btn_add = findViewById(R.id.btn_add);
        btn_return = findViewById(R.id.btn_return);
        btn_deleteall = findViewById(R.id.btn_deleteall);

        lv_user = findViewById(R.id.lv_user);

        userDao = new UserDao();//对象
        //获取主线程
        mainHandler = new Handler(getMainLooper());

        //添加点击事件
        btn_return.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_deleteall.setOnClickListener(this);
    }

    /**
     * 加载数据
     */
    private void loadUserDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                userinfoList = userDao.getAllUserList();
                Log.i("管理界面的数据", "用户数量" + userinfoList.size());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLvData();
                    }
                });
            }
        }).start();
    }

    //    显示数据
    private void showLvData() {
        if (lvUserinfoAdapter == null) {//首次加载
            //实例化
            lvUserinfoAdapter = new LvUserinfoAdapter(this, userinfoList);
            lv_user.setAdapter(lvUserinfoAdapter);
        } else {//更新时
            lvUserinfoAdapter.setUserinfoList(userinfoList);
            //通过调用其notifyDataSetChanged()方法来通知ListView数据发生了更改，重新绘制并更新ListView的内容
            lvUserinfoAdapter.notifyDataSetChanged();
        }
        /**
         * 编辑按钮事件
         */
        lvUserinfoAdapter.setOnEditBtnClickListener(new OnEditBtnClickListener() {
            @Override
            public void onEditBtnClick(View view, int position) {
                Userinfo item = userinfoList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userEdit", item);
                Intent intent = new Intent(UserManagerActicity.this, UserEditActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });

        /**
         * 删除按钮事件
         */
        lvUserinfoAdapter.setOnDelBtnClickListener(new OnDelBtnClickListener() {
            @Override
            public void onDelBtnClick(View view, int position) {
                Userinfo item = userinfoList.get(position);
                new AlertDialog.Builder(UserManagerActicity.this)
                        .setTitle("删除确定")
                        .setMessage("你确定要删除：\nid:[" + item.getId() + "]\n用户名：[" + item.getUname() + "]\n的信息吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                doDelUser(item.getId());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create().show();
            }
        });

//        lvUserinfoAdapter.setOnDeleteAllClickLinstener(new OnDeleteAllBtnClickListener() {
//            @Override
//            public void onDeleteAllBtnClick(View view, int position) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        final int iRow = userDao.delAll();
//                        mainHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                loadUserDb();
//                            }
//                        });
//                    }
//                }).start();
//            }
//        });


    }

    private void doDelUser(final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int iRow = userDao.delUser(id);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        CommonUtils.showShortMsg(UserManagerActicity.this, "删除成功");
                        loadUserDb();
                    }
                });
            }
        }).start();
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_return:
                finish();
                break;
            case R.id.btn_add:
//                打开添加用户界面
                Intent intent = new Intent(this, UserAddActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_deleteall:
                new AlertDialog.Builder(UserManagerActicity.this)
                        .setTitle("删除确定")
                        .setMessage("你确定要删除所有用户的信息吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        final int iRow = userDao.delAll();
                                        mainHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                loadUserDb();
                                            }
                                        });
                                    }
                                }).start();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create().show();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            Log.i("requestCode", "requestCode" + requestCode);
            loadUserDb();
        }
    }
}