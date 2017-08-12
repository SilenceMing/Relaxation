package com.xiaoming.slience.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaoming.slience.R;
import com.xiaoming.slience.bean.Photos;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1216:50
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosHolder>{

    private List<Photos.DataBean> mDataBean;
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    public void setData(Context context,List<Photos.DataBean> data){
        mContext = context;
        mDataBean = data;
    }

    @Override
    public PhotosAdapter.PhotosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photos, parent, false);
        return new PhotosHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotosAdapter.PhotosHolder holder, int position) {
        Glide.with(mContext).load(mDataBean.get(position).getImage_url()).into(holder.mIvContent);
    }

    @Override
    public int getItemCount() {
        return mDataBean.size();
    }

    public interface OnItemClickListener{
        void OnClick(View v,int position);
    }

    public class PhotosHolder extends RecyclerView.ViewHolder {
        private ImageView mIvContent;
        public PhotosHolder(View itemView) {
            super(itemView);
            mIvContent = (ImageView) itemView.findViewById(R.id.iv_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mItemClickListener!=null){
                        mItemClickListener.OnClick(v,getLayoutPosition());
                    }
                }
            });

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mItemClickListener = listener;
    }

}
