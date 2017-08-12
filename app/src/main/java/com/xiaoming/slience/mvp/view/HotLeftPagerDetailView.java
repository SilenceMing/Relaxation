package com.xiaoming.slience.mvp.view;

import com.xiaoming.slience.bean.Collection;
import com.xiaoming.slience.bean.Collections;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/2015:33
 */

public interface HotLeftPagerDetailView {

    void CollectionsQuery(List<Collections> list);

    void addCollectionFinish();

    void CollectionQuery(List<Collection> list);

    void delCollectionsFinish();

    void AddLikeFinish();
}
