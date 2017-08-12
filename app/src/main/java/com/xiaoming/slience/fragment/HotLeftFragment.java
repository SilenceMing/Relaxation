package com.xiaoming.slience.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xiaoming.slience.R;
import com.xiaoming.slience.activity.CollectionsActivity;
import com.xiaoming.slience.activity.LoginActivity;
import com.xiaoming.slience.activity.TabSelectActivity;
import com.xiaoming.slience.adapter.HotLeftAdapter;
import com.xiaoming.slience.base.BaseFragment;
import com.xiaoming.slience.bean.Tabs;
import com.xiaoming.slience.utils.DBManager;
import com.xiaoming.slience.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class HotLeftFragment extends BaseFragment {

    private TabLayout mTabView;
    private ViewPager mVpContent;
    private HotLeftAdapter mAdapter;
    private ImageView mTvTabAdd;
    private DBManager mDBManager;
    private List<Tabs> tabsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        EventBus.getDefault().register(this);
        mDBManager = DBManager.getInstance(mActivity);
        mAdapter = new HotLeftAdapter(mActivity, mACache);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_hotleft, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mTabView = (TabLayout) view.findViewById(R.id.tab_view);
        mTabView.setTabMode(TabLayout.MODE_SCROLLABLE);
        mVpContent = (ViewPager) view.findViewById(R.id.vp_content);
        mTvTabAdd = (ImageView) view.findViewById(R.id.iv_tab_add);
        mTvTabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, TabSelectActivity.class));
            }
        });
    }

    @Override
    public void initData() {
        tabsList = mDBManager.queryTabsVisibleList();
        mAdapter.setData(tabsList);
        mVpContent.setAdapter(mAdapter);
        mTabView.setupWithViewPager(mVpContent);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mActivity.getMenuInflater().inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                break;
            case R.id.action_collections:
                if (SPUtils.getObjFromSp(mActivity, getString(R.string.SPUserClass)) != null) {
                    startActivity(new Intent(mActivity, CollectionsActivity.class));
                }else{
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventTabs(String msg) {
        if (msg.equals(getString(R.string.TabSelect_UpdataFinish))) {
            initData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
