package com.xiaoming.slience.mvp.mode;

import com.xiaoming.slience.bean.CloumnsPosts;
import com.xiaoming.slience.bean.Collection;
import com.xiaoming.slience.bean.Collections;
import com.xiaoming.slience.bean.SlienceUser;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/2015:37
 */

public interface HotLeftPagerDetaiModel {
    void collectionsQuery(SlienceUser user,HotLeftPagerDetaiModelImp.isCreateListener listener);

    void addCollection(Collections collections, List<CloumnsPosts.PostsBean> posts, int position, HotLeftPagerDetaiModelImp.isCreateListener listener);

    void addReads(SlienceUser mUser, CloumnsPosts.PostsBean bean, HotLeftPagerDetaiModelImp.CollectionListener listener);

    void ReadsQuery(SlienceUser mUser, HotLeftPagerDetaiModelImp.ReadsQueryListener listener);

    void addLikes(SlienceUser mUser, CloumnsPosts.PostsBean bean, HotLeftPagerDetaiModelImp.CollectionListener listener);

    void LikesQuery(SlienceUser mUser, HotLeftPagerDetaiModelImp.LikesQueryListener listener);

    void collectionQuery(Collections collections, HotLeftPagerDetaiModelImp.CollectionQueryListener listener);

    void collectionsDel(List<Collection> list,Collections collections, HotLeftPagerDetaiModelImp.CollectionListener listener);
}
