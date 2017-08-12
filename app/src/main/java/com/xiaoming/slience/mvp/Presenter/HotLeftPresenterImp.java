package com.xiaoming.slience.mvp.Presenter;

import com.google.gson.Gson;
import com.xiaoming.slience.global.Global_Url;
import com.xiaoming.slience.bean.Columns;
import com.xiaoming.slience.utils.ACache;
import com.xiaoming.slience.mvp.view.HotLeftView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1020:59
 */

public class HotLeftPresenterImp implements HotLeftPresenter {

    private String Url = Global_Url.COLUMNS;
    private HotLeftView mLeftView;
    private ACache mACache;

    public HotLeftPresenterImp(HotLeftView hotLeftView, ACache aCache) {
        mLeftView = hotLeftView;
        mACache = aCache;
    }

    @Override
    public void getDataFromServer(boolean isLoadMore, boolean isCache) {
        List<Columns.ColumnsBean> columnsBeanList = (List<Columns.ColumnsBean>) mACache.getAsObject(Url);
        if (isCache && columnsBeanList != null) {
            mLeftView.onRequestComplete(columnsBeanList);
        } else {
            RequestParams params = new RequestParams(Url);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    AnalysisData(result);
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

    private void AnalysisData(String result) {
        Gson gson = new Gson();
        Columns columnses = gson.fromJson(result, Columns.class);
        List<Columns.ColumnsBean> columns = columnses.getColumns();
        if (columns != null) {
            mLeftView.onRequestComplete(columns);
            mACache.put(Url, (Serializable) columns);
        }
    }
}
