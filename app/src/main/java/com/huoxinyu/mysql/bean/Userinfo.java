package com.huoxinyu.mysql.bean;

import java.io.Serializable;

/*
 *用户信息实体类
 */
public class Userinfo implements Serializable {//在界面之间传递数据
    private int id;
    private String uname;
    private String upass;
    private String creatDt;

    public Userinfo() {
    }

    public Userinfo(int id, String uname, String upass, String creatDt) {
        this.id = id;
        this.uname = uname;
        this.upass = upass;
        this.creatDt = creatDt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }

    public String getCreatDt() {
        return creatDt;
    }

    public void setCreatDt(String creatDt) {
        this.creatDt = creatDt;
    }
}
