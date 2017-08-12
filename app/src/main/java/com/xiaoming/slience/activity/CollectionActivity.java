package com.xiaoming.slience.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.xiaoming.slience.R;
import com.xiaoming.slience.adapter.CollectionAdapter;
import com.xiaoming.slience.adapter.CollectionTopAdapter;
import com.xiaoming.slience.bean.Collection;
import com.xiaoming.slience.bean.Collections;
import com.xiaoming.slience.bean.SlienceUser;
import com.xiaoming.slience.mvp.Presenter.HotLeftPagerDetailPresenter;
import com.xiaoming.slience.mvp.Presenter.HotLeftPagerDetailPresenterImp;
import com.xiaoming.slience.mvp.view.HotLeftPagerDetailView;
import com.xiaoming.slience.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import nsu.edu.com.library.SwipeBackActivity;
import nsu.edu.com.library.SwipeBackLayout;

/**
 * @author slience
 * @des 收藏详情页
 * @time 2017/6/2020:17
 */

public class CollectionActivity extends SwipeBackActivity implements HotLeftPagerDetailView {

    private SwipeBackLayout mSwipeBackLayout;
    private ViewPager mVpTopContent;
    private ListView mLvContent;
    private Toolbar mCollToolbar;
    private Collections mCollections;
    private int mCollectionsPostion;
    private HotLeftPagerDetailPresenter mPresenter;
    private CollectionAdapter mCollectionAdapter;
    private CollectionTopAdapter mTopAdapter;
    private ImageView mIvVpBg;
    private ProgressBarCircularIndeterminate mPgView;
    private List<Collection> collectionList;
    private SlienceUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        Bmob.initialize(this, "8ece0e1c21f9685ab8ddf4602f14008a");
        EventBus.getDefault().register(this);
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(true);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        mSwipeBackLayout.setEdgeSize(200);
        mCollections = (Collections) getIntent().getSerializableExtra(getString(R.string.Collections));
        mCollectionsPostion = getIntent().getIntExtra(getString(R.string.CollectionsPosition),0);
        mPresenter = new HotLeftPagerDetailPresenterImp(this);
        mTopAdapter = new CollectionTopAdapter(this);
        mCollectionAdapter = new CollectionAdapter(this);
        collectionList = new ArrayList<>();
        mUser = (SlienceUser) SPUtils.getObjFromSp(this,getString(R.string.SPUserClass));
        init();
    }

    private void init() {
        mVpTopContent = (ViewPager) findViewById(R.id.vp_topContent);
        mLvContent = (ListView) findViewById(R.id.lv_content);
        mCollToolbar = (Toolbar) findViewById(R.id.coll_toolbar);
        mIvVpBg = (ImageView) findViewById(R.id.iv_vp_bg);
        mPgView = (ProgressBarCircularIndeterminate) findViewById(R.id.pg_view);
        setSupportActionBar(mCollToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mPresenter.GetCollection(mCollections);
        BmobFile collections_img = mCollections.getCollections_Img();
        String mVpBgUrl = collections_img == null ? null : collections_img.getFileUrl();
        Glide.with(this).load(mVpBgUrl).error(R.mipmap.vp_bg_default).placeholder(R.mipmap.vp_bg_default).into(mIvVpBg);

        mLvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CollectionActivity.this,HotLeftDetailActivity.class);
                intent.setAction(getString(R.string.CollectionActivity_Action));
                intent.putExtra(getString(R.string.CollectionActivity_Collection),collectionList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_collections_share) {
            //TODO 分享收藏集
        } else if (item.getItemId() == R.id.action_collections_delete) {
            //删除收藏集
            mPresenter.DelCollections(mCollections);
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void CollectionsQuery(List<Collections> list) {
        mTopAdapter.setData(list.get(mCollectionsPostion), collectionList.size());
        mTopAdapter.notifyDataSetChanged();
    }

    @Override
    public void addCollectionFinish() {

    }

    @Override
    public void CollectionQuery(List<Collection> list) {
        collectionList.addAll(list);
        mPgView.setVisibility(View.GONE);
        mTopAdapter.setData(mCollections, list.size());
        mVpTopContent.setAdapter(mTopAdapter);
        mCollectionAdapter.setData(list);
        mLvContent.setAdapter(mCollectionAdapter);
    }

    @Override
    public void delCollectionsFinish() {
        startActivity(new Intent(this,CollectionsActivity.class));
    }

    @Override
    public void AddLikeFinish() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(String msg){
        if(msg.equals(getString(R.string.updateFinish))){
            mPresenter.GetCollections(mUser);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
