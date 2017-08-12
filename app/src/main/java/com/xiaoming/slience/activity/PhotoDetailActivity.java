package com.xiaoming.slience.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.xiaoming.slience.mvp.Presenter.PhotoDetailPresenterImp;
import com.xiaoming.slience.R;
import com.xiaoming.slience.adapter.PhotoDetailAdapter;
import com.xiaoming.slience.bean.Photos;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1221:25
 */

public class PhotoDetailActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private int mPosition;
    private List<Photos.DataBean> mDataBeen;
    private PhotoDetailAdapter mAdapter;
    private PhotoDetailPresenterImp mPresenterImp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_photodetail);
        mPosition = getIntent().getIntExtra("Position",0);
        mDataBeen = (List<Photos.DataBean>) getIntent().getSerializableExtra("DataBean");
        mPresenterImp = new PhotoDetailPresenterImp(this);
        mAdapter = new PhotoDetailAdapter(this,mPresenterImp);
        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mAdapter.setData(mDataBeen);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mPosition);
    }
}
