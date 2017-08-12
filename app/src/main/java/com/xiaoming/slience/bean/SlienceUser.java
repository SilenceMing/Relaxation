package com.xiaoming.slience.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author slience
 * @des 用户对象
 * @time 2017/7/515:50
 */

public class SlienceUser extends BmobObject implements Serializable {

    private String User_Phone;
    private String User_Name;
    private String User_Password;
    private String User_Des;
    private BmobFile User_Image;

    public String getUser_Phone() {
        return User_Phone;
    }

    public void setUser_Phone(String user_Phone) {
        User_Phone = user_Phone;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getUser_Password() {
        return User_Password;
    }

    public void setUser_Password(String user_Password) {
        User_Password = user_Password;
    }

    public String getUser_Des() {
        return User_Des;
    }

    public void setUser_Des(String user_Des) {
        User_Des = user_Des;
    }

    public BmobFile getUser_Image() {
        return User_Image;
    }

    public void setUser_Image(BmobFile user_Image) {
        User_Image = user_Image;
    }
}
