package com.xiaoming.slience.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.xiaoming.slience.R;

import cn.bmob.v3.Bmob;
import nsu.edu.com.library.SwipeBackActivity;
import nsu.edu.com.library.SwipeBackLayout;

/**
 * @author slience
 * @des
 * @time 2017/6/1815:15
 */

public abstract class BaseActivity extends SwipeBackActivity {

    private SwipeBackLayout mSwipeBackLayout;
    private Toolbar mBaseToolbar;
    private RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Bmob.initialize(this, "8ece0e1c21f9685ab8ddf4602f14008a");
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(true);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        mSwipeBackLayout.setEdgeSize(200);
        init();
        View view = LayoutInflater.from(this).inflate(getLayoutId(),mLayout,false);
        mLayout.addView(view);
    }

    public abstract int getLayoutId();

    private void init() {
        mBaseToolbar = (Toolbar) findViewById(R.id.base_toolbar);
        mLayout = (RelativeLayout) findViewById(R.id.rl_content);
        setSupportActionBar(mBaseToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
