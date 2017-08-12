package com.xiaoming.slience.mvp.Presenter;

/**
 * @author slience
 * @des
 * @time 2017/6/1216:36
 */

public interface HotLeftPagerPresenter {
    void getDataFromServer(boolean isLoadMore, boolean isCache);
    void getMoreDataFromServer(boolean isLoadMore, int maxId, boolean isCache);
}
