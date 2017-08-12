package com.xiaoming.slience.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoming.slience.R;
import com.xiaoming.slience.bean.Collection;
import com.xiaoming.slience.bean.Likes;
import com.xiaoming.slience.bean.Reads;
import com.xiaoming.slience.utils.TimeUtils;

/**
 * @author slience
 * @des
 * @time 2017/6/2022:19
 */

public class CollectionViewHolder {
    private TextView mTvTitle;
    private TextView mTvDes;
    private RelativeLayout mLl;
    private TextView mTvTime;
    private TextView mTvLike;
    private TextView mTvComment;
    private ImageView mIvView;
    private Context mContext;

    public CollectionViewHolder(View v,Context context) {
        initView(v);
        mContext = context;
    }

    private void initView(View v) {
        mTvTitle = (TextView) v.findViewById(R.id.tv_title);
        mTvDes = (TextView) v.findViewById(R.id.tv_des);
        mLl = (RelativeLayout) v.findViewById(R.id.ll);
        mTvTime = (TextView) v.findViewById(R.id.tv_time);
        mTvLike = (TextView) v.findViewById(R.id.tv_like);
        mTvComment = (TextView) v.findViewById(R.id.tv_comment);
        mIvView = (ImageView) v.findViewById(R.id.iv_view);
    }

    public void bindCollectionData(Collection collection){
        mTvTitle.setText(collection.getCollection_Title());
        mTvDes.setText(collection.getCollection_Des());
        mTvComment.setText(collection.getCollection_CommCount());
        mTvLike.setText(collection.getCollection_LikeCount());
        mTvTime.setText(TimeUtils.getFitTimeSpanByNow(collection.getCollection_Time(),2));
        if(collection.getCollection_ImgUrl() != null){
            mIvView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(collection.getCollection_ImgUrl()).into(mIvView);
        }else{
            mIvView.setVisibility(View.GONE);
        }
    }

    public void bindLikesData(Likes mLikes){
        mTvTitle.setText(mLikes.getLikes_Title());
        mTvDes.setText(mLikes.getLikes_Des());
        mTvComment.setText(mLikes.getLikes_CommCount());
        mTvLike.setText(mLikes.getLikes_LikeCount());
        mTvTime.setText(TimeUtils.getFitTimeSpanByNow(mLikes.getLikes_Time(),2));
        if(mLikes.getLikes_ImgUrl() != null){
            mIvView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(mLikes.getLikes_ImgUrl()).into(mIvView);
        }else{
            mIvView.setVisibility(View.GONE);
        }
    }
    public void bindReadsData(Reads mReads){
        mTvTitle.setText(mReads.getReads_Title());
        mTvDes.setText(mReads.getReads_Des());
        mTvComment.setText(mReads.getReads_CommCount());
        mTvLike.setText(mReads.getReads_LikeCount());
        mTvTime.setText(TimeUtils.getFitTimeSpanByNow(mReads.getReads_Time(),2));
        if(mReads.getReads_ImgUrl() != null){
            mIvView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(mReads.getReads_ImgUrl()).into(mIvView);
        }else{
            mIvView.setVisibility(View.GONE);
        }
    }



}
