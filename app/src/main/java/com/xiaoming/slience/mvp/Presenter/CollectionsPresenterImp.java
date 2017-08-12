package com.xiaoming.slience.mvp.Presenter;

import com.xiaoming.slience.bean.Collections;
import com.xiaoming.slience.bean.SlienceUser;
import com.xiaoming.slience.mvp.mode.CollectionsDbModel;
import com.xiaoming.slience.mvp.mode.CollectionsDbModelImp;
import com.xiaoming.slience.mvp.view.CreateCollectionsView;

import java.io.File;

import cn.bmob.v3.exception.BmobException;

/**
 * @author slience
 * @des
 * @time 2017/6/2011:15
 */

public class CollectionsPresenterImp implements CollectionsPresenter {

    private CollectionsDbModel mModel;
    private CreateCollectionsView mView;

    public CollectionsPresenterImp(CreateCollectionsView view) {
        mModel = new CollectionsDbModelImp();
        mView = view;
    }

    @Override
    public void CreateCollections(SlienceUser user,String name, String des, File imgFile) {
        mModel.collectionsAdd(user,name, des, imgFile, new CollectionsDbModelImp.CollectionsListener() {
            @Override
            public void onSuccess() {
                mView.CreateFinish();
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void UpdateCollections(Collections collections,String name, String des, File imgFile) {
        mModel.collectionsUpdate(collections,name, des, imgFile, new CollectionsDbModelImp.CollectionsListener() {
            @Override
            public void onSuccess() {
                mView.UpdateFinish();
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }
}
