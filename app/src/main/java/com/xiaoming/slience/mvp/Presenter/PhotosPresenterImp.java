package com.xiaoming.slience.mvp.Presenter;

import com.google.gson.Gson;
import com.xiaoming.slience.bean.Photos;
import com.xiaoming.slience.utils.ACache;
import com.xiaoming.slience.mvp.view.PhotosView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1216:14
 */

public class PhotosPresenterImp implements PhotosPresenter {

    private String Url;
    private PhotosView mPhotoView;
    private int PAGE = 0;
    private ACache mACache;

    public PhotosPresenterImp(PhotosView photoView, ACache aCache) {
        mPhotoView = photoView;
        mACache = aCache;
    }

    @Override
    public void getDataFromServer(boolean isLoadMore, boolean isCache) {
        Url = "http://image.baidu.com/channel/listjson?pn=0&rn=20&tag1=%E7%BE%8E%E5%A5%B3";
        ServerData(Url, isLoadMore, isCache);
    }

    @Override
    public void getMoreDataFromServer(boolean isLoadMore, boolean isCache) {
        PAGE++;
        Url = "http://image.baidu.com/channel/listjson?pn=" + PAGE + "&rn=20&tag1=%E7%BE%8E%E5%A5%B3";
        ServerData(Url, isLoadMore, isCache);
    }

    private void ServerData(String url, final boolean isLoadMore, boolean isCache) {
        List<Photos.DataBean> dataBeanList = (List<Photos.DataBean>) mACache.getAsObject(Url);
        if (isCache && dataBeanList != null) {
            mPhotoView.onRequestData(dataBeanList, isLoadMore);
        } else {
            RequestParams params = new RequestParams(url);
            x.http().get(params, new Callback.CommonCallback<String>() {
                private List<Photos.DataBean> data;

                @Override
                public void onSuccess(String result) {
                    data = AnalysisData(result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    System.out.println("onFinished");

                    if (data != null) {
                        mPhotoView.onRequestData(data, isLoadMore);
                        mACache.put(Url, (Serializable) data);
                    }
                }
            });
        }

    }

    private List<Photos.DataBean> AnalysisData(String result) {
        Gson gson = new Gson();
        Photos photos = gson.fromJson(result, Photos.class);
        return photos.getData();
    }
}
