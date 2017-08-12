package com.xiaoming.slience.base;


/**
 * @author slience
 * @des
 * @time 2017/6/1020:56
 */

public interface BasePresenter {
    void getDataFromServer(boolean isLoadMore);
    void getMoreDataFromServer(boolean isLoadMore,int maxId);
}
