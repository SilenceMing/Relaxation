package com.xiaoming.slience.mvp.mode;

import com.xiaoming.slience.bean.SlienceUser;

import cn.bmob.v3.datatype.BmobFile;

/**
 * @author slience
 * @des
 * @time 2017/7/516:04
 */

public interface UserModel {
    void UserAdd(String phone,String name,String password,String des,UserModelImp.UserListener listener);
    void UserFindPw(String phone,String password, UserModelImp.UserListener listener);
    void UserLogin(String phone, String password, UserModelImp.UserLoginListener listener);
    void UserLoginIntegra(String phone,String name,String password,String des,UserModelImp.UserLoginListener listener);
    void UserUpdata(SlienceUser user, BmobFile file, String username, String des, UserModelImp.UserListener listener);
}
