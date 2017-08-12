package com.xiaoming.slience.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xiaoming.slience.R;
import com.xiaoming.slience.base.BaseActivity;
import com.xiaoming.slience.bean.SlienceUser;
import com.xiaoming.slience.mvp.Presenter.CollectionsPresenter;
import com.xiaoming.slience.mvp.Presenter.CollectionsPresenterImp;
import com.xiaoming.slience.mvp.view.CreateCollectionsView;
import com.xiaoming.slience.utils.SPUtils;
import com.xiaoming.slience.widget.CustomDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

import static com.xiaoming.slience.R.id.iv_uploadImg;

/**
 * @author slience
 * @des
 * @time 2017/6/1815:10
 */

public class CreateCollectionsActivity extends BaseActivity implements CreateCollectionsView,View.OnClickListener {

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private EditText mEdName;
    private EditText mEdDescribe;
    private ImageView mIvUploadImg;
    private File mImgFile;
    private CollectionsPresenter mPresenter;
    private TextView mCamera, mGallery;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "8ece0e1c21f9685ab8ddf4602f14008a");
        getSupportActionBar().setTitle(R.string.activity_createCollections);
        mPresenter = new CollectionsPresenterImp(this);
        initView();
    }

    private void initView() {
        mEdName = (EditText) findViewById(R.id.ed_name);
        mEdDescribe = (EditText) findViewById(R.id.ed_describe);
        mIvUploadImg = (ImageView) findViewById(iv_uploadImg);
        mIvUploadImg.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_createcollections;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_uploadImg) {
            showCustomDialog();
            dialog.show();
        } else if (v.getId() == R.id.tv_Camera) {
            dialog.cancel();
            GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
        } else if (v.getId() == R.id.tv_Gallery) {
            dialog.cancel();
            GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
        }
    }

    private void showCustomDialog() {
        CustomDialog customDialog = new CustomDialog(this);
        Object[] objects = customDialog.Custom(R.style.BottomDialog, R.layout.item_img_bottom_dialog, Gravity.CENTER);
        dialog= (Dialog) objects[0];
        View mContentView = (View) objects[1];
        mCamera = (TextView) mContentView.findViewById(R.id.tv_Camera);
        mGallery = (TextView) mContentView.findViewById(R.id.tv_Gallery);
        mCamera.setOnClickListener(this);
        mGallery.setOnClickListener(this);
    }

    /**
     * 图片选择回调
     */
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                Glide.with(CreateCollectionsActivity.this).load(resultList.get(0).getPhotoPath())
                        .error(R.drawable.ic_gf_default_photo)
                        .placeholder(R.drawable.ic_gf_default_photo)
                        .into(mIvUploadImg);
                mImgFile = new File(resultList.get(0).getPhotoPath());
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(CreateCollectionsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_createcollections, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_createCollections:
                //创建收藏集
                mPresenter.CreateCollections((SlienceUser)SPUtils.getObjFromSp(this,getString(R.string.SPUserClass)),mEdName.getText().toString(),
                        mEdDescribe.getText().toString(), mImgFile);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void CreateFinish() {
        EventBus.getDefault().post(getString(R.string.eventBus_createFinish));
        finish();
    }

    @Override
    public void UpdateFinish() {
    }

}
