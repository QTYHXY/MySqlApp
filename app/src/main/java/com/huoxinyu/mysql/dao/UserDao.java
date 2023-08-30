package com.huoxinyu.mysql.dao;

import android.util.Log;

import com.huoxinyu.mysql.bean.Userinfo;
import com.huoxinyu.mysql.common.CommonUtils;
import com.huoxinyu.mysql.bean.DbOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据库操作类
 * 实现用户的CRUD操作
 */
public class UserDao extends DbOpenHelper {
    /**
     * 查询所有数据
     * @return
     */
    public List<Userinfo> getAllUserList() {
        List<Userinfo> list = new ArrayList<>();//创建数据元素为userinfo的集合
        try {
            getConnection();//获取连接信息
            String sql = "select * from userinfo";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Userinfo item = new Userinfo();
                item = new Userinfo();
                item.setId(rs.getInt("id"));
                item.setUname(rs.getString("uname"));
                item.setUpass(rs.getString("upass"));
                item.setCreatDt(rs.getString("createDt"));
                list.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    /**
     * 按用户名和密码查询用户信息 r
     *
     * @param uname 用户名
     * @param upass 密码
     * @return Userinfo
     */
    public Userinfo getUserByUnameAndUpass(String uname, String upass) {
        Userinfo item = null;
        try {
            getConnection();
            String sql = "select * from userinfo where uname=? and upass=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, uname);
            pStmt.setString(2, upass);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                item = new Userinfo();
                item.setId(rs.getInt("id"));
                item.setUname(uname);
                item.setUpass(upass);
                item.setCreatDt(rs.getString("createDt"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return item;
    }

    /**
     * 添加用户信息 c
     *
     * @param item 要添加的用户
     * @return int 改变的行数
     */
    public int addUser(Userinfo item) {
        int iRow = 0;
        try {
            getConnection();
            String sql = "insert into userinfo (uname,upass,createDt) values(?,?,now())";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, item.getUname());
            pStmt.setString(2, item.getUpass());
//            pStmt.setString(3, item.getCreatDt());
            Log.i("addDaouname", item.getUname());
            Log.i("addDaoupass", item.getUpass());
            Log.i("addDao时间",item.getCreatDt());
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }


    /**
     * 编辑用户信息 u
     *
     * @param item 要编辑的用户
     * @return int 改变的行数
     */
    public int editUser(Userinfo item) {
        int iRow = 0;
        try {
            getConnection();
            String sql = "update userinfo set uname=?,upass=? where id=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, item.getUname());
            pStmt.setString(2, item.getUpass());
            pStmt.setInt(3, item.getId());
            Log.i("Edituname", item.getUname());
            Log.i("Editupass", item.getUpass());
            Log.i("User时间", CommonUtils.getDateStrFromNow());
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }

    /**
     * 根据id删除用户信息
     *
     * @param id 要删除游湖的id
     * @return int 改变的行数
     */
    public int delUser(int id) {
        int iRow = 0;
        try {
            getConnection();
            String sql = "delete from userinfo where id=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, id);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }

    /**
     * 清空数据库
     * @return
     */
    public int delAll() {
        int iRow = 0;
        try {
            getConnection();
            String sql = "truncate table userinfo";
            pStmt = conn.prepareStatement(sql);
            iRow = pStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }
}
