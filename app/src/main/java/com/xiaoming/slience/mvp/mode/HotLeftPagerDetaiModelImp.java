package com.xiaoming.slience.mvp.mode;

import android.util.Log;

import com.xiaoming.slience.bean.CloumnsPosts;
import com.xiaoming.slience.bean.Collection;
import com.xiaoming.slience.bean.Collections;
import com.xiaoming.slience.bean.Likes;
import com.xiaoming.slience.bean.Reads;
import com.xiaoming.slience.bean.SlienceUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author slience
 * @des
 * @time 2017/6/20 15:38
 */

public class HotLeftPagerDetaiModelImp implements HotLeftPagerDetaiModel {

    @Override
    public void collectionsQuery(SlienceUser user, final isCreateListener listener) {
        BmobQuery<Collections> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("mUser", user);
        bmobQuery.findObjects(new FindListener<Collections>() {
            @Override
            public void done(List<Collections> list, BmobException e) {
                if (e == null) {
                    if (list != null) {
                        listener.onSuccess(list);
                    }
                } else {
                    listener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void addCollection(final Collections collections, final List<CloumnsPosts.PostsBean> posts, final int position, final isCreateListener listener) {
        BmobQuery<Collection> query = new BmobQuery<>();
        query.addWhereEqualTo("Collection_Title", posts.get(position).getTitle());
        query.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.size() <= 0) {
                        Collection collection = new Collection();
                        collection.setCollection_Title(posts.get(position).getTitle());
                        collection.setCollection_LikeCount(posts.get(position).getLike_count() + "");
                        collection.setCollection_Time(posts.get(position).getPublished_time());
                        collection.setCollection_Des(posts.get(position).getAbstractX());
                        collection.setCollection_CommCount(posts.get(position).getComments_count() + "");
                        collection.setCollection_Url(posts.get(position).getUrl());
                        List<CloumnsPosts.PostsBean.ThumbsBean> thumbs = posts.get(position).getThumbs();
                        if (thumbs != null && thumbs.size() > 0) {
                            collection.setCollection_ImgUrl(thumbs.get(0).getSmall().getUrl());
                        }
                        collection.setCollections(collections);
                        collection.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    listener.onSuccess(null);
                                } else {
                                    listener.onFailure(e);
                                }
                            }
                        });
                    }
                } else {
                    listener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void addReads(final SlienceUser mUser, final CloumnsPosts.PostsBean bean, final CollectionListener listener) {
        BmobQuery<Reads> query = new BmobQuery<>();
        query.addWhereEqualTo("Reads_Title", bean.getTitle());
        query.findObjects(new FindListener<Reads>() {
            @Override
            public void done(List<Reads> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.size() <= 0) {
                        Reads mReads = new Reads();
                        mReads.setReads_Title(bean.getTitle());
                        mReads.setReads_CommCount(bean.getComments_count() + "");
                        mReads.setReads_LikeCount(bean.getLike_count() + "");
                        mReads.setReads_Time(bean.getPublished_time());
                        mReads.setReads_Des(bean.getAbstractX());
                        mReads.setReads_Url(bean.getUrl());
                        List<CloumnsPosts.PostsBean.ThumbsBean> thumbs = bean.getThumbs();
                        if (thumbs != null && thumbs.size() > 0) {
                            mReads.setReads_ImgUrl(thumbs.get(0).getSmall().getUrl());
                        }
                        mReads.setUser(mUser);
                        mReads.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    listener.onSuccess();
                                } else {
                                    listener.onFailure(e);
                                }
                            }
                        });
                    }
                } else {
                    listener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void ReadsQuery(SlienceUser mUser, final ReadsQueryListener listener) {
        BmobQuery<Reads> query = new BmobQuery<>();
        query.addWhereEqualTo("mUser", new BmobPointer(mUser));
        query.findObjects(new FindListener<Reads>() {
            @Override
            public void done(List<Reads> list, BmobException e) {
                if (e == null) {
                    if (list != null) {
                        listener.onSuccess(list);
                    }
                } else {
                    listener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void addLikes(final SlienceUser mUser, final CloumnsPosts.PostsBean bean, final CollectionListener listener) {
        BmobQuery<Likes> query = new BmobQuery<>();
        query.addWhereEqualTo("Likes_Title", bean.getTitle());
        query.findObjects(new FindListener<Likes>() {
            @Override
            public void done(List<Likes> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.size() <= 0) {
                        Likes mLikes = new Likes();
                        mLikes.setLikes_Title(bean.getTitle());
                        mLikes.setLikes_CommCount(bean.getComments_count() + "");
                        mLikes.setLikes_LikeCount(bean.getLike_count() + "");
                        mLikes.setLikes_Time(bean.getPublished_time());
                        mLikes.setLikes_Des(bean.getAbstractX());
                        mLikes.setLikes_Url(bean.getUrl());
                        List<CloumnsPosts.PostsBean.ThumbsBean> thumbs = bean.getThumbs();
                        if (thumbs != null && thumbs.size() > 0) {
                            mLikes.setLikes_ImgUrl(thumbs.get(0).getSmall().getUrl());
                        }
                        mLikes.setUser(mUser);
                        mLikes.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    listener.onSuccess();
                                } else {
                                    listener.onFailure(e);
                                }
                            }
                        });
                    }
                } else {
                    listener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void LikesQuery(SlienceUser mUser, final LikesQueryListener listener) {
        BmobQuery<Likes> query = new BmobQuery<>();
        query.addWhereEqualTo("mUser", new BmobPointer(mUser));
        query.findObjects(new FindListener<Likes>() {
            @Override
            public void done(List<Likes> list, BmobException e) {
                if (e == null) {
                    if (list != null) {
                        listener.onSuccess(list);
                    }
                } else {
                    listener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void collectionQuery(Collections collections, final CollectionQueryListener listener) {
        BmobQuery<Collection> query = new BmobQuery<>();
        query.addWhereEqualTo("mCollections", new BmobPointer(collections));
        query.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> list, BmobException e) {
                if (e == null) {
                    if (list != null) {
                        Log.d("Collection", list.size() + "");
                        listener.onSuccess(list);
                    }
                } else {
                    listener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void collectionsDel(List<Collection> list, final Collections collections, final CollectionListener listener) {
        List<BmobObject> collectionList = new ArrayList<>();
        collectionList.addAll(list);
        new BmobBatch().deleteBatch(collectionList).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if (e == null) {
                    collections.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                listener.onSuccess();
                            } else {
                                listener.onFailure(e);
                            }
                        }
                    });
                } else {
                    listener.onFailure(e);
                }
            }
        });
    }

    public interface isCreateListener {
        void onSuccess(List<Collections> list);

        void onFailure(BmobException e);
    }

    public interface CollectionQueryListener {
        void onSuccess(List<Collection> list);

        void onFailure(BmobException e);
    }

    public interface CollectionListener {
        void onSuccess();

        void onFailure(BmobException e);
    }

    public interface ReadsQueryListener {
        void onSuccess(List<Reads> mReads);

        void onFailure(BmobException e);
    }

    public interface LikesQueryListener {
        void onSuccess(List<Likes> mLikes);

        void onFailure(BmobException e);
    }

}
