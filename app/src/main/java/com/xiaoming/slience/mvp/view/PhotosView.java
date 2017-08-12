package com.xiaoming.slience.mvp.view;

import com.xiaoming.slience.bean.Photos;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1216:12
 */

public interface PhotosView{
    void onRequestData(List<Photos.DataBean> data,boolean isLoadMore);
}
