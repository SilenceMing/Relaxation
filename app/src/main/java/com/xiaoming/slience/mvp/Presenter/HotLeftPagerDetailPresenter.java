package com.xiaoming.slience.mvp.Presenter;

import com.xiaoming.slience.bean.CloumnsPosts;
import com.xiaoming.slience.bean.Collections;
import com.xiaoming.slience.bean.SlienceUser;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/2015:35
 */

public interface HotLeftPagerDetailPresenter {
    /**
     * 查询收藏集
     */
    void GetCollections(SlienceUser user);

    /**
     * 添加收藏
     *
     * @param collections
     * @param posts
     * @param position
     */
    void AddCollection(Collections collections, List<CloumnsPosts.PostsBean> posts, int position);

    /**
     * 添加喜欢列表
     * @param mUser
     * @param bean
     */
    void AddLikes(SlienceUser mUser,CloumnsPosts.PostsBean bean);

    /**
     * 查询Likes
     * @param mUser
     */
    void LikesQuery(SlienceUser mUser);

    /**
     * 添加阅读历史
     * @param mUser
     * @param bean
     */
    void AddReads(SlienceUser mUser,CloumnsPosts.PostsBean bean);

    /**
     * 查询阅读文章
     * @param mUser
     */
    void ReadsQuery(SlienceUser mUser);
    /**
     * 查询收藏
     *
     * @param collections
     */
    void GetCollection(Collections collections);

    /**
     * 删除收藏集
     * @param collections
     */
    void DelCollections(Collections collections);
}
