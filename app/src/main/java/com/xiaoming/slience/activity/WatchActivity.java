package com.xiaoming.slience.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.xiaoming.slience.R;
import com.xiaoming.slience.adapter.ReadsAdapter;
import com.xiaoming.slience.base.BaseActivity;
import com.xiaoming.slience.bean.Reads;
import com.xiaoming.slience.bean.SlienceUser;
import com.xiaoming.slience.mvp.Presenter.HotLeftPagerDetailPresenter;
import com.xiaoming.slience.mvp.Presenter.HotLeftPagerDetailPresenterImp;
import com.xiaoming.slience.mvp.view.WatchView;
import com.xiaoming.slience.utils.SPUtils;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/7/917:10
 */

public class WatchActivity extends BaseActivity implements WatchView{

    private ListView mLvContent;
    private ProgressBarCircularIndeterminate mPgView;
    private ReadsAdapter mReadsAdapter;
    private HotLeftPagerDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("阅读过的文章");
        mReadsAdapter = new ReadsAdapter(this);
        mPresenter = new HotLeftPagerDetailPresenterImp(this);
        initView();
    }

    private void initView() {
        mLvContent = (ListView) findViewById(R.id.lv_content);
        mPgView = (ProgressBarCircularIndeterminate) findViewById(R.id.pg_view);
        mPresenter.ReadsQuery((SlienceUser) SPUtils.getObjFromSp(this,getString(R.string.SPUserClass)));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_watch;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ReadsQuery(final List<Reads> mReads) {
        mPgView.setVisibility(View.GONE);
        mReadsAdapter.setData(mReads);
        mLvContent.setAdapter(mReadsAdapter);
        mLvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WatchActivity.this,HotLeftDetailActivity.class);
                intent.setAction(getString(R.string.WatchActivity_Action));
                intent.putExtra(getString(R.string.ReadsClass),mReads.get(position));
                startActivity(intent);
            }
        });
    }
}
