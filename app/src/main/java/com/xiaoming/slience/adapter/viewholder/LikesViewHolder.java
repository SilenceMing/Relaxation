package com.xiaoming.slience.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoming.slience.R;
import com.xiaoming.slience.bean.Collection;
import com.xiaoming.slience.utils.TimeUtils;

/**
 * @author slience
 * @des
 * @time 2017/6/2022:19
 */

public class LikesViewHolder {
    private TextView mTvTitle;
    private TextView mTvDes;
    private RelativeLayout mLl;
    private TextView mTvTime;
    private TextView mTvLike;
    private TextView mTvComment;
    private ImageView mIvView;
    private Context mContext;

    public LikesViewHolder(View v, Context context) {
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

    public void bindData(Collection collection){
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



}
