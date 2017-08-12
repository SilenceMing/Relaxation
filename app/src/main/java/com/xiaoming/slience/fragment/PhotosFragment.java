package com.xiaoming.slience.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.xiaoming.slience.R;
import com.xiaoming.slience.activity.PhotoDetailActivity;
import com.xiaoming.slience.adapter.PhotosAdapter;
import com.xiaoming.slience.base.BaseFragment;
import com.xiaoming.slience.bean.Photos;
import com.xiaoming.slience.mvp.Presenter.PhotosPresenter;
import com.xiaoming.slience.mvp.Presenter.PhotosPresenterImp;
import com.xiaoming.slience.mvp.view.PhotosView;
import com.xiaoming.slience.utils.EndlessRecyclerOnScrollListener1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Slience_Manager
 * @time 2017/4/28 9:59
 */

public class PhotosFragment extends BaseFragment implements PhotosView{

    private RecyclerView mRecyclerview;
    private ProgressBarCircularIndeterminate mPgView;
    private List<Photos.DataBean> mDataBeen;
    private PhotosPresenter mPhotosPresenter;
    private StaggeredGridLayoutManager mGridLayoutManager;
    private PhotosAdapter mAdapter;
    private SwipeRefreshLayout mSrlView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBeen = new ArrayList<>();
        mPhotosPresenter = new PhotosPresenterImp(this,mACache);
        mGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new PhotosAdapter();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.item_pager_hotleft, container, false);
        init(view);
        return view;
    }

    private void init(View v) {
        mRecyclerview = (RecyclerView) v.findViewById(R.id.recyclerview);
        mSrlView = (SwipeRefreshLayout) v.findViewById(R.id.srl_view);
        mRecyclerview.setLayoutManager(mGridLayoutManager);
        mPgView = (ProgressBarCircularIndeterminate) v.findViewById(R.id.pg_view);
        mSrlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPhotosPresenter.getDataFromServer(false,false);
            }
        });
        mRecyclerview.addOnScrollListener(new EndlessRecyclerOnScrollListener1(mGridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mPhotosPresenter.getMoreDataFromServer(true,false);
            }
        });
        mAdapter.setOnItemClickListener(new PhotosAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View v, int position) {
                Intent intent = new Intent(mActivity,PhotoDetailActivity.class);
                intent.putExtra("DataBean",(Serializable) mDataBeen);
                intent.putExtra("Position",position);
                mActivity.startActivity(intent);
            }
        });


    }

    @Override
    public void initData() {
        mPhotosPresenter.getDataFromServer(false,true);
    }

    @Override
    public void onRequestData(List<Photos.DataBean> data,boolean isLoadMore) {
        mSrlView.setRefreshing(false);
        mPgView.setVisibility(View.GONE);
        if(!isLoadMore){
            mDataBeen.clear();
            mDataBeen.addAll(data);
            mAdapter.setData(getActivity(),mDataBeen);
            mRecyclerview.setAdapter(mAdapter);
        }else{
            mDataBeen.addAll(data);
            mAdapter.notifyDataSetChanged();
        }
    }
}
