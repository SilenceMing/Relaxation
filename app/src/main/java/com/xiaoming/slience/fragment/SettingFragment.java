package com.xiaoming.slience.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoming.slience.R;
import com.xiaoming.slience.activity.CollectionsActivity;
import com.xiaoming.slience.activity.LikesActivity;
import com.xiaoming.slience.activity.LoginActivity;
import com.xiaoming.slience.activity.UserDetailsActivity;
import com.xiaoming.slience.activity.WatchActivity;
import com.xiaoming.slience.base.BaseFragment;
import com.xiaoming.slience.bean.SlienceUser;
import com.xiaoming.slience.utils.FileUtils;
import com.xiaoming.slience.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Slience_Manager
 * @time 2017/4/28 9:59
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener{

    private RelativeLayout mRlUser;
    private RelativeLayout mRlLikes;
    private CircleImageView mIvTopImg;
    private TextView mTvUsername;
    private ImageView mIvLike;
    private RelativeLayout mRlCollections;
    private ImageView mIvCollection;
    private RelativeLayout mRlWatch;
    private ImageView mIvWatch;
    private RelativeLayout mRlClearCache;
    private TextView mTvCache;
    private Button mBtnOutLog;
    private SlienceUser user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_setting,container,false);
        init(view);
        return view;
    }

    private void init(View v) {
        mRlUser = (RelativeLayout) v.findViewById(R.id.rl_user);
        mRlLikes = (RelativeLayout) v.findViewById(R.id.rl_likes);
        mIvTopImg = (CircleImageView) v.findViewById(R.id.iv_topImg);
        mTvUsername = (TextView) v.findViewById(R.id.tv_username);
        mIvLike = (ImageView) v.findViewById(R.id.iv_like);
        mRlCollections = (RelativeLayout) v.findViewById(R.id.rl_collections);
        mIvCollection = (ImageView) v.findViewById(R.id.iv_collection);
        mRlWatch = (RelativeLayout) v.findViewById(R.id.rl_watch);
        mIvWatch = (ImageView) v.findViewById(R.id.iv_watch);
        mRlClearCache = (RelativeLayout) v.findViewById(R.id.rl_clearCache);
        mTvCache = (TextView) v.findViewById(R.id.tv_Cache);
        mBtnOutLog = (Button) v.findViewById(R.id.btn_outLog);

        long folderSize = FileUtils.getFolderSize(new FileUtils(mActivity).mkCacheDir());
        String formatSize = FileUtils.getFormatSize(folderSize);
        mTvCache.setText(formatSize);

        mRlUser.setOnClickListener(this);
        mRlCollections.setOnClickListener(this);
        mRlWatch.setOnClickListener(this);
        mRlClearCache.setOnClickListener(this);
        mBtnOutLog.setOnClickListener(this);
        mRlLikes.setOnClickListener(this);
    }

    @Override
    public void initData() {
        user = (SlienceUser) SPUtils.getObjFromSp(mActivity, getString(R.string.SPUserClass));
        if(user!=null){
            mTvUsername.setText(user.getUser_Name());
            if(user.getUser_Image()!=null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = getImageFromNet(user.getUser_Image().getUrl());
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mIvTopImg.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();
            }else{
                mIvTopImg.setImageResource(R.mipmap.ic_user_default);
            }
        }else{
            mIvTopImg.setImageResource(R.mipmap.ic_user_default);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_user:
                if(user == null){
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }else{
                    Intent intent = new Intent(mActivity, UserDetailsActivity.class);
                    intent.putExtra(getString(R.string.SPUserClass),user);
                    startActivity(intent);
                }
                break;
            case R.id.rl_likes:
                if(user == null){
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }else{
                    startActivity(new Intent(mActivity, LikesActivity.class));
                }
                break;
            case R.id.rl_collections:
                if(user == null){
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }else{
                    startActivity(new Intent(mActivity, CollectionsActivity.class));
                }
                break;
            case R.id.rl_watch:
                if(user == null){
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }else{
                    startActivity(new Intent(mActivity, WatchActivity.class));
                }
                break;
            case R.id.rl_clearCache:
                mACache.clear();
                mTvCache.setText("0");
                break;
            case R.id.btn_outLog:
                SPUtils.RemoveObj2sp(mActivity,getString(R.string.SPUserClass));
                EventBus.getDefault().post(getString(R.string.EventBus_OutLogin));
                initData();
                mActivity.startActivity(new Intent(mActivity,LoginActivity.class));
                break;
        }
    }
    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onEventBusUserFinish(String msg){
        if(msg.equals(getString(R.string.EventBus_UserDetailFinish))){
            initData();
        }
    }
    private Bitmap getImageFromNet(String url) {
        HttpURLConnection conn = null;
        try {
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("GET"); //设置请求方法
            conn.setConnectTimeout(10000); //设置连接服务器超时时间
            conn.setReadTimeout(5000);  //设置读取数据超时时间
            conn.connect(); //开始连接
            int responseCode = conn.getResponseCode(); //得到服务器的响应码
            if (responseCode == 200) {
                //访问成功
                InputStream is = conn.getInputStream(); //获得服务器返回的流数据
                Bitmap bitmap = BitmapFactory.decodeStream(is); //根据流数据 创建一个bitmap对象
                return bitmap;
            } else {
                //访问失败
                Log.d("lyf--", "访问失败===responseCode：" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect(); //断开连接
            }
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
