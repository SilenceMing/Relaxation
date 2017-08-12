package com.xiaoming.slience.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.xiaoming.slience.R;
import com.xiaoming.slience.adapter.CollectionsAdapter;
import com.xiaoming.slience.bean.CloumnsPosts;
import com.xiaoming.slience.bean.Collection;
import com.xiaoming.slience.bean.Collections;
import com.xiaoming.slience.bean.Likes;
import com.xiaoming.slience.bean.Reads;
import com.xiaoming.slience.bean.SlienceUser;
import com.xiaoming.slience.mvp.Presenter.HotLeftPagerDetailPresenter;
import com.xiaoming.slience.mvp.Presenter.HotLeftPagerDetailPresenterImp;
import com.xiaoming.slience.mvp.view.HotLeftPagerDetailView;
import com.xiaoming.slience.utils.SPUtils;
import com.xiaoming.slience.widget.CustomDialog;
import com.xiaoming.slience.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.bmob.v3.Bmob;
import nsu.edu.com.library.SwipeBackActivity;
import nsu.edu.com.library.SwipeBackLayout;

/**
 * @author slience
 * @des
 * @time 2017/6/1116:16
 */

public class HotLeftDetailActivity extends SwipeBackActivity implements HotLeftPagerDetailView, View.OnClickListener {

    private WebView mWvContent;
    private int mPosition;
    private List<CloumnsPosts.PostsBean> mPosts;
    private SwipeBackLayout mSwipeBackLayout;
    private ProgressBarDeterminate mProgressDeterminate;
    private Toolbar mToolbar;
    private TextView mTvLike;
    private TextView mTvComment;
    private ImageView mIvLike;
    private ImageView mIvCollection;
    private TextView mTvMdCollections;
    private ListView mLvCollections;
    private HotLeftPagerDetailPresenter mPresenter;
    private ProgressBarCircularIndeterminate mPgView;
    private CollectionsAdapter collectionsAdapter;
    private String mAction;
    private Collection mCollection;
    private String mContentUrl;
    private String mShareTitle;
    private String mShareImg;
    private String mShareDes;
    private ShareAction mShareAction;
    private LoadingDialog mLoadingDialog;
    private SlienceUser user;
    private Dialog mDialog;
    private Reads mReads;
    private Likes mLikes;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotleftdetail);
        EventBus.getDefault().register(this);
        Bmob.initialize(this, "8ece0e1c21f9685ab8ddf4602f14008a");
        mPosition = getIntent().getIntExtra(getString(R.string.Post_Positions), 0);
        mPosts = (List<CloumnsPosts.PostsBean>) getIntent().getSerializableExtra(getString(R.string.Posts));
        mAction = getIntent().getAction();
        mCollection = (Collection) getIntent().getSerializableExtra(getString(R.string.CollectionActivity_Collection));
        mReads = (Reads) getIntent().getSerializableExtra(getString(R.string.ReadsClass));
        mLikes = (Likes) getIntent().getSerializableExtra(getString(R.string.LikesClass));
        mPresenter = new HotLeftPagerDetailPresenterImp(this);
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(true);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        mSwipeBackLayout.setEdgeSize(200);
        user = (SlienceUser) SPUtils.getObjFromSp(this,getString(R.string.SPUserClass));
        init();
    }

    private void init() {
        mIvLike = (ImageView) findViewById(R.id.iv_like);
        mIvCollection = (ImageView) findViewById(R.id.iv_collection);
        mTvComment = (TextView) findViewById(R.id.tv_comment);
        mTvLike = (TextView) findViewById(R.id.tv_like);
        mWvContent = (WebView) findViewById(R.id.wv_content);
        mToolbar = (Toolbar) findViewById(R.id.hot_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mAction.equals(getString(R.string.CollectionActivity_Action))) {
            mContentUrl = mCollection.getCollection_Url();
            mIvCollection.setEnabled(false);
            mTvLike.setText(mCollection.getCollection_LikeCount());
            mTvComment.setText(mCollection.getCollection_CommCount());
            mShareTitle = mCollection.getCollection_Title();
            mShareDes = "分享一下...";
            mShareImg = mCollection.getCollection_ImgUrl();
        } else if (mAction.equals(getString(R.string.HotLeftPager_Action))) {
            CloumnsPosts.PostsBean bean = mPosts.get(mPosition);
            mContentUrl = bean.getUrl();
            mTvLike.setText("喜欢 " + bean.getLike_count());
            mTvComment.setText("评论 " + bean.getComments_count());
            mShareTitle = bean.getTitle();
            mShareDes = bean.getAbstractX();
            mShareImg = bean.getShare_pic_url();

            mPresenter.AddReads(user,bean);
        }else if(mAction.equals(getString(R.string.WatchActivity_Action))){
            mContentUrl = mReads.getReads_Url();
            mTvLike.setText("喜欢 " + mReads.getReads_LikeCount());
            mTvComment.setText("评论 " + mReads.getReads_CommCount());
            mShareTitle = mReads.getReads_Title();
            mShareDes = mReads.getReads_Des();
            mShareImg = mReads.getReads_ImgUrl();
        }else if(mAction.equals(getString(R.string.LikesActivity_Action))){
            mContentUrl = mLikes.getLikes_Url();
            mTvLike.setText("喜欢 " + mLikes.getLikes_LikeCount());
            mTvComment.setText("评论 " + mLikes.getLikes_CommCount());
            mShareTitle = mLikes.getLikes_Title();
            mShareDes = mLikes.getLikes_Des();
            mShareImg = mLikes.getLikes_ImgUrl();
        }
        mWvContent.loadUrl(mContentUrl);
        mIvLike.setOnClickListener(this);
        mIvCollection.setOnClickListener(this);
        mProgressDeterminate = (ProgressBarDeterminate) findViewById(R.id.progressDeterminate);
        mWvContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress == 100) {
                    mProgressDeterminate.setVisibility(View.GONE);
                } else {
                    if (mProgressDeterminate.getVisibility() == View.GONE)
                        mProgressDeterminate.setVisibility(View.VISIBLE);
                    mProgressDeterminate.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                getSupportActionBar().setTitle(title);
            }

        });
        mWvContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hotleftdetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_share:
                //TODO 网页分享
                UMWeb web = new UMWeb(mContentUrl);
                web.setTitle(mShareTitle);//标题
                web.setThumb(new UMImage(this, mShareImg));  //缩略图
                web.setDescription(mShareDes);//描述

                ShareBoardConfig config = new ShareBoardConfig();
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);

                mShareAction = new ShareAction(this);
                mShareAction.withMedia(web)
                        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                                SHARE_MEDIA.ALIPAY, SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN, SHARE_MEDIA.TENCENT, SHARE_MEDIA.SMS, SHARE_MEDIA.EMAIL, SHARE_MEDIA.GOOGLEPLUS, SHARE_MEDIA.LINE
                        ).setCallback(new CustomShareListener()).open(config);
                break;
            case R.id.menu_browser:
                startActivity(new Intent().setAction("android.intent.action.VIEW").setData(Uri.parse(mContentUrl)));
                break;
            case R.id.menu_copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(ClipData.newUri(getContentResolver(), "uri", Uri.parse(mContentUrl)));
                Toast.makeText(this, "已复制", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class CustomShareListener implements UMShareListener{

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.d("shareListener","onStart");
            mLoadingDialog = new LoadingDialog(HotLeftDetailActivity.this);
            mLoadingDialog.mLoadingDialog.show();
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Log.d("shareListener","onResult");
            mLoadingDialog.mLoadingDialog.dismiss();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.d("shareListener","onError");
            mLoadingDialog.mLoadingDialog.dismiss();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.d("shareListener","onCancel");
            mLoadingDialog.mLoadingDialog.dismiss();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_like:
                if(user == null){
                    startActivity(new Intent(this,LoginActivity.class));
                }else{
                    mPresenter.AddLikes(user,mPosts.get(mPosition));
                }
                break;
            case R.id.iv_collection:
                if(user == null){
                    startActivity(new Intent(this,LoginActivity.class));
                }else{
                    showCustomDialog();
                    mPresenter.GetCollections(user);
                }
                break;
            case R.id.tv_mdCollections:
                startActivity(new Intent(this, CreateCollectionsActivity.class));
                break;
        }
    }

    private void showCustomDialog() {
        CustomDialog customDialog = new CustomDialog(this);
        Object[] objects = customDialog.Custom(R.style.BottomDialog, R.layout.item_collections_bottom_dialog, Gravity.BOTTOM);
        mDialog= (Dialog) objects[0];
        View mContentView = (View) objects[1];
        initView(mContentView);
        mDialog.show();
    }

    private void initView(View view) {
        mTvMdCollections = (TextView) view.findViewById(R.id.tv_mdCollections);
        mLvCollections = (ListView) view.findViewById(R.id.lv_collections);
        mPgView = (ProgressBarCircularIndeterminate) view.findViewById(R.id.pg_view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = getResources().getDisplayMetrics().heightPixels / 3;
        view.setLayoutParams(layoutParams);
        mTvMdCollections.setOnClickListener(this);
    }


    @Override
    public void CollectionsQuery(final List<Collections> list) {
        mPgView.setVisibility(View.GONE);
        collectionsAdapter = new CollectionsAdapter(this, list);
        mLvCollections.setAdapter(collectionsAdapter);

        mLvCollections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.AddCollection(list.get(position), mPosts, mPosition);
            }
        });
    }

    @Override
    public void addCollectionFinish() {
        mDialog.cancel();
    }

    @Override
    public void CollectionQuery(List<Collection> list) {

    }

    @Override
    public void delCollectionsFinish() {
    }

    @Override
    public void AddLikeFinish() {
        mIvLike.setImageResource(R.mipmap.like_select);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(String msg) {
        if (msg.equals(getString(R.string.eventBus_createFinish))) {
            mPresenter.GetCollections(user);
            collectionsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mShareAction.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
