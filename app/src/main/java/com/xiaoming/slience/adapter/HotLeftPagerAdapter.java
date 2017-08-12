package com.xiaoming.slience.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoming.slience.R;
import com.xiaoming.slience.bean.CloumnsPosts;
import com.xiaoming.slience.utils.TimeUtils;

import org.xutils.x;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1111:42
 */

public class HotLeftPagerAdapter extends RecyclerView.Adapter {

    private static final int FOOTVIEW = 0;
    private static final int CONTENTVIEW = 1;
    private List<CloumnsPosts.PostsBean> mPosts;
    private OnItemClickListener mOnItemClickListener;

    public void setData(List<CloumnsPosts.PostsBean> posts) {
        mPosts = posts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view;
        if (viewType == FOOTVIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footview, parent, false);
            holder = new FootViewHolder(view);
        } else if (viewType == CONTENTVIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotleft_content, parent, false);
            holder = new ContentViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
        } else if (holder instanceof ContentViewHolder) {
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            CloumnsPosts.PostsBean postsBean = mPosts.get(position);
            contentViewHolder.mTvTitle.setText(postsBean.getTitle());
            contentViewHolder.mTvDes.setText(postsBean.getAbstractX());
            contentViewHolder.mTvTime.setText(TimeUtils.getFitTimeSpanByNow(postsBean.getPublished_time(),2));
            contentViewHolder.mTvLike.setText("喜欢 |"+postsBean.getLike_count());
            contentViewHolder.mTvComment.setText("评论 |"+postsBean.getComments_count());
            if (postsBean.getThumbs() != null && postsBean.getThumbs().size() > 0) {
                contentViewHolder.mView.setVisibility(View.VISIBLE);
                x.image().bind(contentViewHolder.mView, postsBean.getThumbs().get(0).getSmall().getUrl());
            } else {
                contentViewHolder.mView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mPosts.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return isFootView(position) ? FOOTVIEW : CONTENTVIEW;
    }

    private boolean isFootView(int position) {
        return position == mPosts.size();
    }

    public void setOnItemClickListener( OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private TextView mTvDes;
        private RelativeLayout mLl;
        private TextView mTvTime;
        private TextView mTvLike;
        private TextView mTvComment;
        private ImageView mView;

        public ContentViewHolder(View itemView) {
            super(itemView);
            init(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener!=null){
                        mOnItemClickListener.OnItemClick(v,getLayoutPosition());
                    }
                }
            });
        }

        private void init(View v) {
            mTvTitle = (TextView) v.findViewById(R.id.tv_title);
            mTvDes = (TextView) v.findViewById(R.id.tv_des);
            mLl = (RelativeLayout) v.findViewById(R.id.ll);
            mTvTime = (TextView) v.findViewById(R.id.tv_time);
            mTvLike = (TextView) v.findViewById(R.id.tv_like);
            mTvComment = (TextView) v.findViewById(R.id.tv_comment);
            mView = (ImageView) v.findViewById(R.id.iv_view);
        }
    }


    class FootViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLinearLayout;

        public FootViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.ll_loadMore);
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(View v,int position);
    }
}
