package com.xiaoming.slience.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoming.slience.R;
import com.xiaoming.slience.base.BaseActivity;
import com.xiaoming.slience.mvp.Presenter.UserPresenter;
import com.xiaoming.slience.mvp.Presenter.UserPresenterImp;
import com.xiaoming.slience.mvp.view.RegisterView;
import com.xiaoming.slience.utils.BtnUtils;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

/**
 * @author slience
 * @des 注册界面
 * @time 2017/7/513:33
 */

public class RegisterActivity extends BaseActivity implements RegisterView, View.OnClickListener {

    private EditText mEdPhoneOrEmail;
    private EditText mEdUsername;
    private EditText mEdPassword;
    private LinearLayout mLlSendSMS;
    private EditText mEdSms;
    private TextView mTvSendSMS;
    private Button mBtnRegister;
    private UserPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        BmobSMS.initialize(this, "8ece0e1c21f9685ab8ddf4602f14008a");
        mPresenter = new UserPresenterImp(this);
        initView();
        initData();
    }

    private void initView() {
        mEdPhoneOrEmail = (EditText) findViewById(R.id.ed_phoneOrEmail);
        mEdUsername = (EditText) findViewById(R.id.ed_userPhone);
        mEdPassword = (EditText) findViewById(R.id.ed_password);
        mLlSendSMS = (LinearLayout) findViewById(R.id.ll_sendSMS);
        mEdSms = (EditText) findViewById(R.id.ed_sms);
        mTvSendSMS = (TextView) findViewById(R.id.tv_sendSMS);
        mBtnRegister = (Button) findViewById(R.id.btn_register);

        mTvSendSMS.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    private void initData() {
        mEdPhoneOrEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEdPhoneOrEmail.getText().toString().length() >= 11) {
                    mLlSendSMS.setVisibility(View.VISIBLE);
                } else {
                    mLlSendSMS.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        final String phone = mEdPhoneOrEmail.getText().toString();
        final String username = mEdUsername.getText().toString();
        final String password = mEdPassword.getText().toString();
        final String des = "闲读";
        switch (v.getId()) {
            case R.id.btn_register:
                BmobSMS.verifySmsCode(this, phone, mEdSms.getText().toString(), new VerifySMSCodeListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            mPresenter.RegisterUser(phone, username, password, des);
                        } else {
                            Toast.makeText(RegisterActivity.this, "验证码有误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.tv_sendSMS:
                BmobSMS.requestSMSCode(this, phone, "闲读", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            if (BtnUtils.isFastClick()) {
                                mCountDownTimer.start();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }

}
    private CountDownTimer mCountDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mTvSendSMS.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            mTvSendSMS.setText("重新发送");
        }
    };

    @Override
    public void onRegisterFinish() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(getString(R.string.RegisterPhone), mEdPhoneOrEmail.getText().toString());
        startActivity(intent);
    }
}
