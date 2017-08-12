package com.xiaoming.slience.mvp.view;

import com.xiaoming.slience.bean.SlienceUser;

/**
 * @author slience
 * @des
 * @time 2017/7/519:03
 */

public interface LoginView {
    void LoginSuccess(SlienceUser user);
    void LoginFailure();
}
