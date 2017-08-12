package com.xiaoming.slience.mvp.Presenter;

import com.xiaoming.slience.bean.SlienceUser;
import com.xiaoming.slience.mvp.mode.UserModel;
import com.xiaoming.slience.mvp.mode.UserModelImp;
import com.xiaoming.slience.mvp.view.FindPwView;
import com.xiaoming.slience.mvp.view.LoginView;
import com.xiaoming.slience.mvp.view.RegisterView;
import com.xiaoming.slience.mvp.view.UserDetailView;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * @author slience
 * @des
 * @time 2017/7/812:48
 */

public class UserPresenterImp implements UserPresenter {

    private RegisterView mRegisterView;
    private FindPwView mFindPwView;
    private LoginView mLoginView;
    private UserDetailView mUserDetailView;
    private UserModel mModel;

    public UserPresenterImp(RegisterView registerView) {
        mRegisterView = registerView;
        mModel = new UserModelImp();
    }

    public UserPresenterImp(FindPwView findPwView) {
        mFindPwView = findPwView;
        mModel = new UserModelImp();
    }

    public UserPresenterImp(LoginView loginView) {
        mLoginView = loginView;
        mModel = new UserModelImp();
    }

    public UserPresenterImp(UserDetailView userDetailView) {
        mUserDetailView = userDetailView;
        mModel = new UserModelImp();
    }

    @Override
    public void RegisterUser(String phone, String username, String password, String des) {
        mModel.UserAdd(phone, username, password, des, new UserModelImp.UserListener() {
            @Override
            public void onSuccess() {
                mRegisterView.onRegisterFinish();
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void FindPw(String phone,String password) {
        mModel.UserFindPw(phone,password, new UserModelImp.UserListener() {
            @Override
            public void onSuccess() {
                mFindPwView.onFindPwFinish();
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void LoginUser(String phone, String password) {
        mModel.UserLogin(phone, password, new UserModelImp.UserLoginListener() {

            @Override
            public void onSuccess(SlienceUser user) {
                mLoginView.LoginSuccess(user);
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
                mLoginView.LoginFailure();
            }
        });
    }

    @Override
    public void LoginIntegra(String phone, String username, String password, String des) {
        mModel.UserLoginIntegra(phone, username, password, des, new UserModelImp.UserLoginListener() {
            @Override
            public void onSuccess(SlienceUser user) {
                mLoginView.LoginSuccess(user);
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void UpdataUser(SlienceUser user, BmobFile file, String username, String des) {
        mModel.UserUpdata(user, file, username, des, new UserModelImp.UserListener() {
            @Override
            public void onSuccess() {
                mUserDetailView.UserDetailFinish();
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }
}
