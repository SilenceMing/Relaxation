package com.xiaoming.slience.mvp.Presenter;

/**
 * @author slience
 * @des
 * @time 2017/6/1216:36
 */

public interface VideoPresenter {
    void getDataFromServer(boolean isLoadMore,boolean isCache);
    void getMoreDataFromServer(boolean isLoadMore,boolean isCache);
}
