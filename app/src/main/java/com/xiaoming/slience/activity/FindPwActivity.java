package com.xiaoming.slience.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoming.slience.R;
import com.xiaoming.slience.base.BaseActivity;
import com.xiaoming.slience.mvp.Presenter.UserPresenter;
import com.xiaoming.slience.mvp.Presenter.UserPresenterImp;
import com.xiaoming.slience.mvp.view.FindPwView;
import com.xiaoming.slience.utils.BtnUtils;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

/**
 * @author slience
 * @des
 * @time 2017/7/516:24
 */

public class FindPwActivity extends BaseActivity implements FindPwView,View.OnClickListener{

    private EditText mTvUserPhone;
    private EditText mEdSms;
    private TextView mTvSendSMS;
    private EditText mTvNewPw;
    private Button mBtnFinish;
    private UserPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobSMS.initialize(this,"8ece0e1c21f9685ab8ddf4602f14008a");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mPresenter = new UserPresenterImp(this);
        initView();
    }

    private void initView() {
        mTvUserPhone = (EditText) findViewById(R.id.tv_userPhone);
        mEdSms = (EditText) findViewById(R.id.ed_sms);
        mTvSendSMS = (TextView) findViewById(R.id.tv_sendSMS);
        mTvNewPw = (EditText) findViewById(R.id.tv_newPw);
        mBtnFinish = (Button) findViewById(R.id.btn_finish);

        mTvSendSMS.setOnClickListener(this);
        mBtnFinish.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_findpw;
    }

    @Override
    public void onClick(View v) {
        final String phone = mTvUserPhone.getText().toString();
        final String password = mTvNewPw.getText().toString();
        String smsCode = mEdSms.getText().toString();
        switch (v.getId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.tv_sendSMS:
                BmobSMS.requestSMSCode(this, phone, "闲读", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if(e == null){
                            if(BtnUtils.isFastClick()){
                                mCountDownTimer.start();
                            }
                        }else{
                            Toast.makeText(FindPwActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.btn_finish:
                BmobSMS.verifySmsCode(this, phone, smsCode, new VerifySMSCodeListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            mPresenter.FindPw(phone,password);
                        }else{
                            Toast.makeText(FindPwActivity.this, "验证码有误，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    private CountDownTimer mCountDownTimer = new CountDownTimer(60000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mTvSendSMS.setText(millisUntilFinished/1000+"秒");
        }

        @Override
        public void onFinish() {
            mTvSendSMS.setText("重新发送");
        }
    };

    @Override
    public void onFindPwFinish() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.putExtra(getString(R.string.RegisterPhone),mTvUserPhone.getText().toString());
        startActivity(intent);
    }
}
