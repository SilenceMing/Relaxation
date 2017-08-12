package com.xiaoming.slience.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiaoming.slience.R;
import com.xiaoming.slience.base.BaseFragment;
import com.xiaoming.slience.bean.SlienceUser;
import com.xiaoming.slience.fragment.HotLeftFragment;
import com.xiaoming.slience.fragment.MusicFragment;
import com.xiaoming.slience.fragment.PhotosFragment;
import com.xiaoming.slience.fragment.SettingFragment;
import com.xiaoming.slience.fragment.ShareFragment;
import com.xiaoming.slience.fragment.VideoFragment;
import com.xiaoming.slience.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private FrameLayout fl_content;
    private List<BaseFragment> mFragments;
    private Toolbar mToolbar;
    private CircleImageView mTopImg;
    private TextView mTopDes;
    private TextView mTopName;
    private Dialog dialog;
    private TextView mCamera,mGallery;
    private String name;
    private String iconImg;
    private String location;
    private boolean isAuthorize;
    private SlienceUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    private void initView() {
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setCurrentFragment(0,"热点新闻",true);
        setSupportActionBar(mToolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //左上角的动态旋转图标
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        mTopImg  = (CircleImageView) headerView.findViewById(R.id.iv_topImg);
        mTopDes = (TextView) headerView.findViewById(R.id.tv_topDes);
        mTopName = (TextView) headerView.findViewById(R.id.tv_topName);

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_news:
                setCurrentFragment(0,item.getTitle(),false);
                break;
            case R.id.nav_photos:
                setCurrentFragment(1,item.getTitle(),false);
                break;
            case R.id.nav_video:
                setCurrentFragment(2,item.getTitle(),false);
                break;
            case R.id.nav_music:
                setCurrentFragment(3,item.getTitle(),false);
                break;
            case R.id.nav_setting:
                setCurrentFragment(4,item.getTitle(),false);
                break;
            case R.id.nav_share:
                setCurrentFragment(5,item.getTitle(),false);
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setCurrentFragment(int position,CharSequence title,boolean isInit) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        if(isInit){
            tr.replace(R.id.fl_content,new HotLeftFragment());
        }else{
            tr.replace(R.id.fl_content, mFragments.get(position));
        }
        tr.commit();
        //设置toolbar标题
        mToolbar.setTitle(title);
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new HotLeftFragment());
        mFragments.add(new PhotosFragment());
        mFragments.add(new VideoFragment());
        mFragments.add(new MusicFragment());
        mFragments.add(new SettingFragment());
        mFragments.add(new ShareFragment());

        mTopImg.setOnClickListener(this);
        SPData();

    }
    public void SPData(){
        if(SPUtils.getObjFromSp(this, getString(R.string.SPUserClass))!=null){
            user = (SlienceUser) SPUtils.getObjFromSp(this, getString(R.string.SPUserClass));
            mTopName.setText(user.getUser_Name());
            mTopDes.setText(user.getUser_Des());
            if(user.getUser_Image()!=null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = getImageFromNet(user.getUser_Image().getUrl());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTopImg.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();
            }else{
                mTopImg.setImageResource(R.mipmap.ic_user_default);
            }
        }else{
            mTopName.setText("未登录");
            mTopDes.setText("休闲阅读");
            mTopImg.setImageResource(R.mipmap.ic_user_default);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_topImg){
            if(user!=null){
                //TODO 图片选择
            }else{
                startActivity(new Intent(this,LoginActivity.class));
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(NiceVideoPlayerManager.instance().onBackPressd()){
            return;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusUserDetail(String msg){
        if(msg.equals(getString(R.string.EventBus_UserDetailFinish))){
            SPData();
        }else if(msg.equals(getString(R.string.EventBus_OutLogin))){
            SPData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
