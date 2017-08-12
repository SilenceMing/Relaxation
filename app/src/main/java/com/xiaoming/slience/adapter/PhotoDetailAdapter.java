package com.xiaoming.slience.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiaoming.slience.mvp.Presenter.PhotoDetailPresenterImp;
import com.xiaoming.slience.bean.Photos;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * @author slience
 * @des
 * @time 2017/6/1221:29
 */

public class PhotoDetailAdapter extends PagerAdapter {

    private List<Photos.DataBean> mDataBeen;
    private PhotoDetailPresenterImp mPresenterImp;
    private Activity mActivity;
    private OnDownloadListener mOnDownloadListener;
    public PhotoDetailAdapter(Activity activity,PhotoDetailPresenterImp presenterImp){
        mActivity = activity;
        mPresenterImp = presenterImp;
    }

    public void setData(List<Photos.DataBean> data){
        mDataBeen = data;
    }
    @Override
    public int getCount() {
        return mDataBeen.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        PhotoView mPhotoView = new PhotoView(container.getContext());
        Glide.with(mActivity).load(mDataBeen.get(position).getDownload_url()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mPhotoView);
        container.addView(mPhotoView);
        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });

        return mPhotoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    public interface OnDownloadListener{
        void OnDownload(View v,String Url);
    }
    public void setOnDownloadListener(OnDownloadListener listener){
        mOnDownloadListener = listener;
    }




}
