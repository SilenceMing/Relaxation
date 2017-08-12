package com.xiaoming.slience.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.xiaoming.slience.R;
import com.xiaoming.slience.adapter.TabSelectAdapter;
import com.xiaoming.slience.base.BaseActivity;
import com.xiaoming.slience.bean.Tabs;
import com.xiaoming.slience.utils.DBManager;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/229:21
 */

public class TabSelectActivity extends BaseActivity{

    private ListView mLvContent;
    private DBManager mDBManager;
    private TabSelectAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.Title_TabSelect);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDBManager = DBManager.getInstance(this);
        mAdapter = new TabSelectAdapter(mDBManager);
        initView();
    }

    private void initView() {
        mLvContent = (ListView) findViewById(R.id.lv_content);
        List<Tabs> queryTabsList = mDBManager.queryTabsList();
        mAdapter.setData(queryTabsList);
        mLvContent.setAdapter(mAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tabselect;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(getString(R.string.TabSelect_UpdataFinish));
    }
}
