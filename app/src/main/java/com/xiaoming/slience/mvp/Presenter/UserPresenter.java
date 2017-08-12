package com.xiaoming.slience.mvp.Presenter;

import com.xiaoming.slience.bean.SlienceUser;

import cn.bmob.v3.datatype.BmobFile;

/**
 * @author slience
 * @des
 * @time 2017/7/812:45
 */

public interface UserPresenter {
    void RegisterUser(String phone,String username,String password,String des);
    void FindPw(String phone,String password);
    void LoginUser(String phone,String password);
    void LoginIntegra(String phone,String username,String password,String des);
    void UpdataUser(SlienceUser user, BmobFile file,String username,String des);
}
