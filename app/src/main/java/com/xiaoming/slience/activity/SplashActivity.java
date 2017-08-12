package com.xiaoming.slience.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.xiaoming.slience.R;
import com.xiaoming.slience.bean.Columns;
import com.xiaoming.slience.bean.Tabs;
import com.xiaoming.slience.global.Global_Title;
import com.xiaoming.slience.mvp.Presenter.HotLeftPresenter;
import com.xiaoming.slience.mvp.Presenter.HotLeftPresenterImp;
import com.xiaoming.slience.mvp.view.HotLeftView;
import com.xiaoming.slience.utils.ACache;
import com.xiaoming.slience.utils.DBManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/2210:09
 */

public class SplashActivity extends AppCompatActivity implements HotLeftView{

    private TextView mTvContent;
    private ACache mACache;
    private HotLeftPresenter mPresenter;
    private DBManager mDBManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mACache = ACache.get(this);
        mDBManager = DBManager.getInstance(this);
        mPresenter = new HotLeftPresenterImp(this,mACache);
        init();
    }

    private void init() {
        mTvContent = (TextView) findViewById(R.id.tv_Content);

        if(TextUtils.isEmpty(mACache.getAsString(getString(R.string.IsFirstInto)))){
            mCountDownTimer.start();
            mPresenter.getDataFromServer(false,false);
        }else{
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
        }
    }

    private CountDownTimer mCountDownTimer = new CountDownTimer(4000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mTvContent.setText(millisUntilFinished / 1000+"");
        }

        @Override
        public void onFinish() {
            mACache.put(getString(R.string.IsFirstInto),getString(R.string.First));
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
        }
    };

    @Override
    public void onRequestComplete(List<Columns.ColumnsBean> columnsList) {
        List<Tabs> mTabsList = new ArrayList<>();
        for(int i = 0; i< Global_Title.HOTLEFT_TABS.length; i++){
            Tabs tabs = new Tabs();
            tabs.setTabId(String.valueOf(columnsList.get(i).getId()));
            tabs.setTabName(Global_Title.HOTLEFT_TABS[i]);
            if (i == 0) {
                tabs.setVisible("0");
            } else {
                tabs.setVisible("1");
            }
            mTabsList.add(tabs);
        }
        mDBManager.insertTabsList(mTabsList);
    }
}
