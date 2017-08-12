package com.xiaoming.slience.pager;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.xiaoming.slience.R;
import com.xiaoming.slience.activity.HotLeftDetailActivity;
import com.xiaoming.slience.adapter.HotLeftPagerAdapter;
import com.xiaoming.slience.bean.CloumnsPosts;
import com.xiaoming.slience.bean.Tabs;
import com.xiaoming.slience.mvp.Presenter.HotLeftPagerPresenter;
import com.xiaoming.slience.mvp.Presenter.HotLeftPagerPresenterImp;
import com.xiaoming.slience.mvp.view.HotLeftPagerView;
import com.xiaoming.slience.utils.ACache;
import com.xiaoming.slience.utils.EndlessRecyclerOnScrollListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.OvershootInRightAnimator;

/**
 * @author slience
 * @des
 * @time 2017/6/1020:17
 */

public class HotLeftPager implements HotLeftPagerView {

    private Activity mActivity;
    private RecyclerView mRecyclerview;
    private HotLeftPagerPresenter mPresenter;
    public View view;
    private LinearLayoutManager layoutManager;
    private HotLeftPagerAdapter pagerAdapter;
    private List<CloumnsPosts.PostsBean> mPosts;
    private ProgressBarCircularIndeterminate mPgView;
    private SwipeRefreshLayout mSrlView;
    private Tabs mTabs;

    public HotLeftPager(Activity activity, Tabs tabs , ACache aCache) {
        mActivity = activity;
        mTabs = tabs;
        mPosts = new ArrayList<>();
        layoutManager = new LinearLayoutManager(mActivity);
        mPresenter = new HotLeftPagerPresenterImp(mTabs.getTabId(), this,aCache);
        pagerAdapter = new HotLeftPagerAdapter();
        view = initView();
    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.item_pager_hotleft, null);
        init(view);
        return view;
    }

    public void initData(){
        mPresenter.getDataFromServer(false,true);
    }

    private void init(View v) {
        mSrlView = (SwipeRefreshLayout) v.findViewById(R.id.srl_view);
        mRecyclerview = (RecyclerView) v.findViewById(R.id.recyclerview);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setItemAnimator(new OvershootInRightAnimator());
        mPgView = (ProgressBarCircularIndeterminate) v.findViewById(R.id.pg_view);

        mSrlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getDataFromServer(false,false);
            }
        });
        mRecyclerview.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mPresenter.getMoreDataFromServer(true, mPosts.get(mPosts.size() - 1).getId(),false);
            }
        });

        pagerAdapter.setOnItemClickListener(new HotLeftPagerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                Intent intent = new Intent(mActivity, HotLeftDetailActivity.class);
                intent.putExtra(mActivity.getString(R.string.Post_Positions), position);
                intent.putExtra(mActivity.getString(R.string.Posts), (Serializable) mPosts);
                intent.setAction(mActivity.getString(R.string.HotLeftPager_Action));
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestData(final List<CloumnsPosts.PostsBean> posts, boolean isLoadMore) {
        mSrlView.setRefreshing(false);
        mPgView.setVisibility(View.GONE);
        if (!isLoadMore) {
            mPosts.clear();
            mPosts.addAll(posts);
            pagerAdapter.setData(mPosts);
            mRecyclerview.setAdapter(pagerAdapter);
        }else{
            mPosts.addAll(posts);
            pagerAdapter.notifyDataSetChanged();
        }
    }
}
