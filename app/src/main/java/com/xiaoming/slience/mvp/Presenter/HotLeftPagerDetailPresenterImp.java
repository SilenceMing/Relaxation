package com.xiaoming.slience.mvp.Presenter;

import com.xiaoming.slience.bean.CloumnsPosts;
import com.xiaoming.slience.bean.Collection;
import com.xiaoming.slience.bean.Collections;
import com.xiaoming.slience.bean.Likes;
import com.xiaoming.slience.bean.Reads;
import com.xiaoming.slience.bean.SlienceUser;
import com.xiaoming.slience.mvp.mode.HotLeftPagerDetaiModel;
import com.xiaoming.slience.mvp.mode.HotLeftPagerDetaiModelImp;
import com.xiaoming.slience.mvp.view.HotLeftPagerDetailView;
import com.xiaoming.slience.mvp.view.LikesView;
import com.xiaoming.slience.mvp.view.WatchView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * @author slience
 * @des
 * @time 2017/6/2015:35
 */

public class HotLeftPagerDetailPresenterImp implements HotLeftPagerDetailPresenter{

    private HotLeftPagerDetaiModel mModel;
    private HotLeftPagerDetailView mHotLeftPagerDetailView;
    private LikesView mLikesView;
    private WatchView mWatchView;

    public HotLeftPagerDetailPresenterImp(HotLeftPagerDetailView view) {
        mHotLeftPagerDetailView = view;
        mModel = new HotLeftPagerDetaiModelImp();
    }

    public HotLeftPagerDetailPresenterImp(LikesView likesView) {
        mLikesView = likesView;
        mModel = new HotLeftPagerDetaiModelImp();
    }

    public HotLeftPagerDetailPresenterImp(WatchView watchView) {
        mWatchView = watchView;
        mModel = new HotLeftPagerDetaiModelImp();
    }

    @Override
    public void GetCollections(SlienceUser user) {
        mModel.collectionsQuery(user,new HotLeftPagerDetaiModelImp.isCreateListener() {
            @Override
            public void onSuccess(List<Collections> list) {
                mHotLeftPagerDetailView.CollectionsQuery(list);
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void AddCollection(Collections collections, List<CloumnsPosts.PostsBean> posts, int position) {
        mModel.addCollection(collections,posts,position, new HotLeftPagerDetaiModelImp.isCreateListener() {
            @Override
            public void onSuccess(List<Collections> list) {
                mHotLeftPagerDetailView.addCollectionFinish();
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void AddReads(SlienceUser mUser, CloumnsPosts.PostsBean bean) {
        mModel.addReads(mUser, bean, new HotLeftPagerDetaiModelImp.CollectionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void ReadsQuery(SlienceUser mUser) {
        mModel.ReadsQuery(mUser, new HotLeftPagerDetaiModelImp.ReadsQueryListener() {
            @Override
            public void onSuccess(List<Reads> mReads) {
                mWatchView.ReadsQuery(mReads);
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void AddLikes(SlienceUser mUser, CloumnsPosts.PostsBean bean) {
        mModel.addLikes(mUser, bean, new HotLeftPagerDetaiModelImp.CollectionListener() {
            @Override
            public void onSuccess() {
                mHotLeftPagerDetailView.AddLikeFinish();
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void LikesQuery(SlienceUser mUser) {
        mModel.LikesQuery(mUser, new HotLeftPagerDetaiModelImp.LikesQueryListener() {
            @Override
            public void onSuccess(List<Likes> mLikes) {
                mLikesView.LikesQuery(mLikes);
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void GetCollection(Collections collections) {
        mModel.collectionQuery(collections, new HotLeftPagerDetaiModelImp.CollectionQueryListener() {
            @Override
            public void onSuccess(List<Collection> list) {
                mHotLeftPagerDetailView.CollectionQuery(list);
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void DelCollections(final Collections collections) {
        mModel.collectionQuery(collections, new HotLeftPagerDetaiModelImp.CollectionQueryListener() {
            @Override
            public void onSuccess(List<Collection> list) {
                mModel.collectionsDel(list,collections, new HotLeftPagerDetaiModelImp.CollectionListener() {
                    @Override
                    public void onSuccess() {
                        mHotLeftPagerDetailView.delCollectionsFinish();
                    }

                    @Override
                    public void onFailure(BmobException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });


    }
}
