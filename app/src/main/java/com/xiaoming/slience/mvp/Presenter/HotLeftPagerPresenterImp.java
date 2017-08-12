package com.xiaoming.slience.mvp.Presenter;

import com.google.gson.Gson;
import com.xiaoming.slience.global.Global_Url;
import com.xiaoming.slience.bean.CloumnsPosts;
import com.xiaoming.slience.utils.ACache;
import com.xiaoming.slience.mvp.view.HotLeftPagerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1113:28
 */

public class HotLeftPagerPresenterImp implements HotLeftPagerPresenter {

    private String Url;
    private String mColumnId;
    private HotLeftPagerView mPagerView;
    private ACache mACache;

    public HotLeftPagerPresenterImp(String columnId, HotLeftPagerView pagerView, ACache aCache) {
        mColumnId = columnId;
        mPagerView = pagerView;
        mACache = aCache;
    }

    @Override
    public void getDataFromServer(boolean isLoadMore, boolean isCache) {
        Url = Global_Url.SERVER + "column/" + mColumnId + "/posts?max_id={maxId}";
        ServerData(Url, isLoadMore,isCache);
    }

    @Override
    public void getMoreDataFromServer(boolean isLoadMore, int maxId, boolean isCache) {
        Url = Global_Url.SERVER + "column/" + mColumnId + "/posts?max_id=" + maxId;
        ServerData(Url, isLoadMore,isCache);
    }

    private void ServerData(String url, final boolean isLoadMore, boolean isCache) {
        List<CloumnsPosts.PostsBean> postsBeanList = (List<CloumnsPosts.PostsBean>) mACache.getAsObject(Url);
        if (isCache && postsBeanList != null) {
            mPagerView.onRequestData(postsBeanList,isLoadMore);
        }else{
            RequestParams params = new RequestParams(url);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    AnalysisData(result, isLoadMore);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {

                }
            });
        }

    }

    private void AnalysisData(String result, boolean isLoadMore) {
        Gson gson = new Gson();
        CloumnsPosts cloumnsPosts = gson.fromJson(result, CloumnsPosts.class);
        List<CloumnsPosts.PostsBean> posts = cloumnsPosts.getPosts();
        if (posts != null) {
            mPagerView.onRequestData(posts, isLoadMore);
            mACache.put(Url, (Serializable) posts);
        }
    }
}