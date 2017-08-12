package com.xiaoming.slience.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoming.slience.R;
import com.xiaoming.slience.base.BaseActivity;
import com.xiaoming.slience.bean.SlienceUser;
import com.xiaoming.slience.mvp.Presenter.UserPresenter;
import com.xiaoming.slience.mvp.Presenter.UserPresenterImp;
import com.xiaoming.slience.mvp.view.UserDetailView;
import com.xiaoming.slience.utils.SPUtils;
import com.xiaoming.slience.widget.CustomDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @des 设置 - 用户详情页
 * @author slience
 * @time 2017/7/511:33
 */

public class UserDetailsActivity extends BaseActivity implements UserDetailView,View.OnClickListener{

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private RelativeLayout mRlDetailUser;
    private CircleImageView mIvTopImg;
    private RelativeLayout mRlDetailUsername;
    private RelativeLayout mRlDetailDes;
    private Dialog imgDialog,editDialog;
    private TextView mCamera,mGallery;
    private String username,des;
    private BmobFile userImg;
    private EditText input;
    private TextView positive,negativel;
    private TextView tv_username,tv_des;
    private SlienceUser user;
    private UserPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("个人设置");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter = new UserPresenterImp(this);
        user = (SlienceUser) getIntent().getSerializableExtra(getString(R.string.SPUserClass));
        userImg = user.getUser_Image();
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_userdetails;
    }

    private void initView() {
        mRlDetailUser = (RelativeLayout) findViewById(R.id.rl_detail_user);
        mIvTopImg = (CircleImageView) findViewById(R.id.iv_topImg);
        mRlDetailUsername = (RelativeLayout) findViewById(R.id.rl_detail_username);
        mRlDetailDes = (RelativeLayout) findViewById(R.id.rl_detail_des);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_des = (TextView) findViewById(R.id.tv_des);

        tv_username.setText(user.getUser_Name());
        tv_des.setText(user.getUser_Des());
        if(user.getUser_Image()!=null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Bitmap bitmap = getImageFromNet(user.getUser_Image().getUrl());
                    runOnUiThread(new Runnable() {
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



        mRlDetailUser.setOnClickListener(this);
        mRlDetailUsername.setOnClickListener(this);
        mRlDetailDes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_detail_user:
                showCustomDialog();
                imgDialog.show();
                break;
            case R.id.rl_detail_username:
                showCustomEditDialog(tv_username);
                editDialog.show();
                break;
            case R.id.rl_detail_des:
                showCustomEditDialog(tv_des);
                editDialog.show();
                break;
            case R.id.tv_Camera:
                imgDialog.cancel();
                GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
                break;
            case R.id.tv_Gallery:
                imgDialog.cancel();
                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
                break;
        }
    }
    private void showCustomDialog() {
        CustomDialog customDialog = new CustomDialog(this);
        Object[] objects = customDialog.Custom(R.style.BottomDialog, R.layout.item_img_bottom_dialog, Gravity.CENTER);
        imgDialog = (Dialog) objects[0];
        View mContentView = (View) objects[1];
        mCamera = (TextView) mContentView.findViewById(R.id.tv_Camera);
        mGallery = (TextView) mContentView.findViewById(R.id.tv_Gallery);
        mCamera.setOnClickListener(this);
        mGallery.setOnClickListener(this);
    }
    private void showCustomEditDialog(final TextView view) {
        CustomDialog customDialog = new CustomDialog(this);
        Object[] objects = customDialog.Custom(R.style.BottomDialog, R.layout.item_edit_bottom_dialog, Gravity.CENTER);
        editDialog = (Dialog) objects[0];
        View mContentView = (View) objects[1];
        input = (EditText) mContentView.findViewById(R.id.ed_input);
        positive = (TextView) mContentView.findViewById(R.id.tv_positive);
        negativel = (TextView) mContentView.findViewById(R.id.tv_negative);
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setText(input.getText().toString());
                editDialog.cancel();
            }
        });
        negativel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgDialog.cancel();
            }
        });
    }
    /**
     * 图片选择回调
     */
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mIvTopImg.setImageBitmap(BitmapFactory.decodeFile(resultList.get(0).getPhotoPath()));
                userImg = new BmobFile(new File(resultList.get(0).getPhotoPath()));
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(UserDetailsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_userdetail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_userdetail:
                username = tv_username.getText().toString();
                des = tv_des.getText().toString();
                mPresenter.UpdataUser(user,userImg,username,des);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void UserDetailFinish() {
        SlienceUser user = (SlienceUser) SPUtils.getObjFromSp(this, getString(R.string.SPUserClass));
        user.setUser_Image(userImg);
        user.setUser_Name(tv_username.getText().toString());
        user.setUser_Des(tv_des.getText().toString());
        SPUtils.saveObj2SP(this,user,getString(R.string.SPUserClass));
        EventBus.getDefault().post(getString(R.string.EventBus_UserDetailFinish));
        finish();
    }
}
