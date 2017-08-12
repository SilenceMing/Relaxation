package com.xiaoming.slience.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xiaoming.slience.R;
import com.xiaoming.slience.base.BaseActivity;
import com.xiaoming.slience.bean.SlienceUser;
import com.xiaoming.slience.mvp.Presenter.UserPresenter;
import com.xiaoming.slience.mvp.Presenter.UserPresenterImp;
import com.xiaoming.slience.mvp.view.FindPwView;
import com.xiaoming.slience.mvp.view.LoginView;
import com.xiaoming.slience.utils.SPUtils;
import com.xiaoming.slience.widget.LoadingDialog;

import java.util.Map;

/**
 * @author slience
 * @des
 * @time 2017/6/2420:01
 */

public class LoginActivity extends BaseActivity implements LoginView,View.OnClickListener{

    private ImageView mIvAppIcon;
    private EditText mEdUserPhone;
    private EditText mEdPassword;
    private Button mBtnLogin;
    private TextView mTvForgetPw;
    private TextView mTvRegisterUser;
    private TextView mTvSinaLogin;
    private TextView mTvWeixinLogin;
    private TextView mTvQqLogin;
    private String mGetPhone;
    private UserPresenter mPresenter;
    private String phone;
    private String password;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mGetPhone = getIntent().getStringExtra(getString(R.string.RegisterPhone));
        mPresenter = new UserPresenterImp(this);
        initView();
    }

    private void initView() {

        mIvAppIcon = (ImageView) findViewById(R.id.iv_app_icon);
        mEdUserPhone = (EditText) findViewById(R.id.ed_userPhone);
        mEdPassword = (EditText) findViewById(R.id.ed_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mTvForgetPw = (TextView) findViewById(R.id.tv_forgetPw);
        mTvRegisterUser = (TextView) findViewById(R.id.tv_registerUser);
        mTvSinaLogin = (TextView) findViewById(R.id.tv_sinaLogin);
        mTvWeixinLogin = (TextView) findViewById(R.id.tv_weixinLogin);
        mTvQqLogin = (TextView) findViewById(R.id.tv_qqLogin);
        mEdUserPhone.setText(mGetPhone);

        mBtnLogin.setOnClickListener(this);
        mTvForgetPw.setOnClickListener(this);
        mTvRegisterUser.setOnClickListener(this);
        mTvSinaLogin.setOnClickListener(this);
        mTvWeixinLogin.setOnClickListener(this);
        mTvQqLogin.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        phone = mEdUserPhone.getText().toString();
        password = mEdPassword.getText().toString();
        switch (v.getId()){
            case R.id.btn_login:
                mPresenter.LoginUser(phone,password);
                break;
            case R.id.tv_forgetPw:
                startActivity(new Intent(this, FindPwActivity.class));
                break;
            case R.id.tv_registerUser:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.tv_sinaLogin:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.SINA,mListener);
                break;
            case R.id.tv_weixinLogin:
                //TODO 微信UId不一致
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN,mListener);
                break;
            case R.id.tv_qqLogin:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ,mListener);
                break;
        }
    }

    private UMAuthListener mListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            loadingDialog = new LoadingDialog(LoginActivity.this).mLoadingDialog;
            loadingDialog.show();
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, final Map<String, String> map) {
            loadingDialog.cancel();
            String uid = map.get("uid");
            String name = map.get("name");
            String accessToken = map.get("accessToken");
            String des = "休闲阅读";
            mPresenter.LoginIntegra(uid,name,accessToken,des);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            loadingDialog.cancel();
            throwable.printStackTrace();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            loadingDialog.cancel();
        }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    @Override
    public void LoginSuccess(SlienceUser user) {
        SPUtils.saveObj2SP(this,user,getString(R.string.SPUserClass));
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void LoginFailure() {
        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
    }

}
