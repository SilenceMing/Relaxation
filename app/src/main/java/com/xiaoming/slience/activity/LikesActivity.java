package com.xiaoming.slience.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.xiaoming.slience.R;
import com.xiaoming.slience.adapter.LikesAdapter;
import com.xiaoming.slience.base.BaseActivity;
import com.xiaoming.slience.bean.Likes;
import com.xiaoming.slience.bean.SlienceUser;
import com.xiaoming.slience.mvp.Presenter.HotLeftPagerDetailPresenter;
import com.xiaoming.slience.mvp.Presenter.HotLeftPagerDetailPresenterImp;
import com.xiaoming.slience.mvp.view.LikesView;
import com.xiaoming.slience.utils.SPUtils;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/7/917:09
 */

public class LikesActivity extends BaseActivity implements LikesView{

    private ListView mLvContent;
    private ProgressBarCircularIndeterminate mPgView;
    private LikesAdapter mLikesAdapter;
    private HotLeftPagerDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("我喜欢的");
        mLikesAdapter = new LikesAdapter(this);
        mPresenter = new HotLeftPagerDetailPresenterImp(this);
        initView();
    }

    private void initView() {
        mLvContent = (ListView) findViewById(R.id.lv_content);
        mPgView = (ProgressBarCircularIndeterminate) findViewById(R.id.pg_view);
        mPresenter.LikesQuery((SlienceUser) SPUtils.getObjFromSp(this,getString(R.string.SPUserClass)));
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
    public void LikesQuery(final List<Likes> mLikes) {
        mPgView.setVisibility(View.GONE);
        mLikesAdapter.setData(mLikes);
        mLvContent.setAdapter(mLikesAdapter);
        mLvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LikesActivity.this,HotLeftDetailActivity.class);
                intent.setAction(getString(R.string.LikesActivity_Action));
                intent.putExtra(getString(R.string.LikesClass),mLikes.get(position));
                startActivity(intent);
            }
        });
    }
}
