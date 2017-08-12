package com.xiaoming.slience.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiaoming.slience.mvp.Presenter.VideoPresenter;
import com.xiaoming.slience.mvp.Presenter.VideoPresenterImp;
import com.xiaoming.slience.R;
import com.xiaoming.slience.adapter.VideoAdapter;
import com.xiaoming.slience.base.BaseFragment;
import com.xiaoming.slience.bean.MV;
import com.xiaoming.slience.utils.EndlessRecyclerOnScrollListener;
import com.xiaoming.slience.mvp.view.VideoView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Slience_Manager
 * @time 2017/4/28 9:59
 */

public class VideoFragment extends BaseFragment implements VideoView {

    private SwipeRefreshLayout mSrlView;
    private RecyclerView mRcvContent;
    private List<MV> mMVs;
    private VideoPresenter mVideoPresenter;
    private LinearLayoutManager mManager;
    private VideoAdapter mVideoAdapter;
    private ProgressBarCircularIndeterminate mPgView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mMVs = new ArrayList<>();
        mManager = new LinearLayoutManager(mActivity);
        mVideoPresenter = new VideoPresenterImp(this,mACache);
        mVideoAdapter = new VideoAdapter(mActivity);
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.item_pager_hotleft, container, false);
        init(view);
        return view;
    }


    private void init(View view) {
        mSrlView = (SwipeRefreshLayout) view.findViewById(R.id.srl_view);
        mRcvContent = (RecyclerView) view.findViewById(R.id.recyclerview);
        mPgView = (ProgressBarCircularIndeterminate) view.findViewById(R.id.pg_view);
        mRcvContent.setLayoutManager(mManager);
        mSrlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mVideoPresenter.getDataFromServer(false, false);
            }
        });
        mRcvContent.addOnScrollListener(new EndlessRecyclerOnScrollListener(mManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mVideoPresenter.getMoreDataFromServer(true, false);
            }
        });
        mRcvContent.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                NiceVideoPlayer niceVideoPlayer = (NiceVideoPlayer) view.findViewById(R.id.nice_video_player);
                if (niceVideoPlayer != null) {
                    niceVideoPlayer.release();
                }
            }
        });
    }

    @Override
    public void initData() {
        mVideoPresenter.getDataFromServer(false, true);
    }

    @Override
    public void onRequestData(final List<MV> mvList, final boolean isLoadMore) {
        mSrlView.setRefreshing(false);
        if (!isLoadMore) {
            mMVs.clear();
            mMVs.addAll(mvList);
            mVideoAdapter.setData(mMVs);
            mRcvContent.setAdapter(mVideoAdapter);
        } else {
            mMVs.addAll(mvList);
            mVideoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgress() {
        mPgView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mPgView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(String msg) {

    }
}
