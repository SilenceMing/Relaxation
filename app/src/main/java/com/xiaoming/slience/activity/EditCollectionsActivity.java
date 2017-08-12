package com.xiaoming.slience.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xiaoming.slience.R;
import com.xiaoming.slience.base.BaseActivity;
import com.xiaoming.slience.bean.Collections;
import com.xiaoming.slience.mvp.Presenter.CollectionsPresenter;
import com.xiaoming.slience.mvp.Presenter.CollectionsPresenterImp;
import com.xiaoming.slience.mvp.view.CreateCollectionsView;

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

public class EditCollectionsActivity extends BaseActivity implements CreateCollectionsView,View.OnClickListener {

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private EditText mEdName;
    private EditText mEdDescribe;
    private ImageView mIvUploadImg;
    private File mImgFile;
    private CollectionsPresenter mPresenter;
    private TextView mCamera, mGallery;
    private Dialog dialog;
    private Collections mCollections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "8ece0e1c21f9685ab8ddf4602f14008a");
        getSupportActionBar().setTitle(R.string.activity_createCollections);
        mCollections = (Collections) getIntent().getSerializableExtra(getString(R.string.edit_Collections));
        mPresenter = new CollectionsPresenterImp(this);
        initView();
    }

    private void initView() {
        mEdName = (EditText) findViewById(R.id.ed_name);
        mEdDescribe = (EditText) findViewById(R.id.ed_describe);
        mIvUploadImg = (ImageView) findViewById(iv_uploadImg);
        mIvUploadImg.setOnClickListener(this);

        mEdName.setHint(mCollections.getCollections_Name());
        mEdDescribe.setHint(mCollections.getCollections_Des());
        String collectionsUrl = mCollections.getCollections_Img()==null?null:mCollections.getCollections_Img().getFileUrl();
        Glide.with(this).load(collectionsUrl).error(R.mipmap.vp_bg_default).placeholder(R.mipmap.vp_bg_default).into(mIvUploadImg);
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
        dialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.item_img_bottom_dialog,null);
        mCamera = (TextView) contentView.findViewById(R.id.tv_Camera);
        mGallery = (TextView) contentView.findViewById(R.id.tv_Gallery);
        mCamera.setOnClickListener(this);
        mGallery.setOnClickListener(this);
        dialog.setContentView(contentView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        initImageLoader(this);
    }

    /**
     * 图片选择回调
     */
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                Glide.with(EditCollectionsActivity.this).load(resultList.get(0).getPhotoPath())
                        .error(R.mipmap.vp_bg_default).placeholder(R.mipmap.vp_bg_default)
                        .into(mIvUploadImg);
                mImgFile = new File(resultList.get(0).getPhotoPath());
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(EditCollectionsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };
    private void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        ImageLoader.getInstance().init(config.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editcollections, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_editCollections:
                //修改收藏集
                mPresenter.UpdateCollections(mCollections,mEdName.getText().toString(),
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
//        EventBus.getDefault().post(getString(R.string.updateFinish));
        startActivity(new Intent(this,CollectionsActivity.class));
        finish();
    }

}
