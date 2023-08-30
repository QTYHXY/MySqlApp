package com.huoxinyu.mysql.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huoxinyu.mysql.R;
import com.huoxinyu.mysql.dao.OnDelBtnClickListener;
import com.huoxinyu.mysql.dao.OnDeleteAllBtnClickListener;
import com.huoxinyu.mysql.dao.OnEditBtnClickListener;
import com.huoxinyu.mysql.bean.Userinfo;

import java.util.List;

/*
    自定义用户数据适配器类
 */
public class LvUserinfoAdapter extends BaseAdapter {
    private Context context;//上下文信息,操作对象

    private List<Userinfo> userinfoList;//用户数据集合

    private OnEditBtnClickListener onEditBtnClickListener;//修改按钮的点击事件的监听实例
    private OnDelBtnClickListener onDelBtnClickListener;
//    private OnDeleteAllBtnClickListener onDeleteAllBtnClickListener;

    public LvUserinfoAdapter() {
    }

    public LvUserinfoAdapter(Context context, List<Userinfo> userinfoList) {
        this.context = context;
        this.userinfoList = userinfoList;
    }

    public void setUserinfoList(List<Userinfo> userinfoList) {
        this.userinfoList = userinfoList;
    }

    public void setOnEditBtnClickListener(OnEditBtnClickListener onEditBtnClickListener) {
        this.onEditBtnClickListener = onEditBtnClickListener;
    }

    public void setOnDelBtnClickListener(OnDelBtnClickListener onDelBtnClickListener) {
        this.onDelBtnClickListener = onDelBtnClickListener;
    }
//    public void setOnDeleteAllClickLinstener(OnDeleteAllBtnClickListener onDeleteAllBtnClickListener){
//        this.onDeleteAllBtnClickListener=onDeleteAllBtnClickListener;
//    }

    @Override
    public int getCount() {
        return userinfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return userinfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder =null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.user_list_item,null);//实例化对象
            viewHolder=new ViewHolder();

            viewHolder.tv_id=convertView.findViewById(R.id.tv_id);
            viewHolder.tv_uname=convertView.findViewById(R.id.tv_uname);
            viewHolder.tv_upass=convertView.findViewById(R.id.tv_upass);
            viewHolder.tv_createDt=convertView.findViewById(R.id.tv_createDt);

            viewHolder.btn_edit=convertView.findViewById(R.id.btn_edit);
            viewHolder.btn_delete=convertView.findViewById(R.id.btn_delete);
//            viewHolder.btn_deleteAll=convertView.findViewById(R.id.btn_deleteall);

            convertView.setTag(viewHolder);//把viewholder存起来

        }else {
            viewHolder=(ViewHolder)convertView.getTag();//获取保存的实例 强转
        }
        //数据填充
        Userinfo item = userinfoList.get(position);
        viewHolder.tv_id.setText(item.getId()+"");//不加“”系统会认为是系统资源
        viewHolder.tv_uname.setText(item.getUname());
        viewHolder.tv_upass.setText(item.getUpass());
        viewHolder.tv_createDt.setText(item.getCreatDt());

        viewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //把原接口的绑定到自定义
                onEditBtnClickListener.onEditBtnClick(view,position);
            }
        });

        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDelBtnClickListener.onDelBtnClick(view,position);
            }
        });

//        viewHolder.btn_deleteAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onDeleteAllBtnClickListener.onDeleteAllBtnClick(view, position);
//            }
//        });
        return convertView;
    }

    /**
     *
     */
    private class ViewHolder{//用于列表控件性能
        private TextView tv_id,tv_uname,tv_upass,tv_createDt;
        private ImageView btn_edit,btn_delete;
    }
}
