package com.xiaoming.slience.mvp.view;

import com.xiaoming.slience.bean.CloumnsPosts;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1113:27
 */

public interface HotLeftPagerView{
    void onRequestData(List<CloumnsPosts.PostsBean> posts,boolean isLoadMore);
}
