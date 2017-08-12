package com.xiaoming.slience.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiao.nicevideoplayer.NiceVideoPlayerController;
import com.xiaoming.slience.R;
import com.xiaoming.slience.adapter.viewholder.FootViewHolder;
import com.xiaoming.slience.adapter.viewholder.VideoViewHolder;
import com.xiaoming.slience.bean.MV;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1317:09
 */

public class VideoAdapter extends RecyclerView.Adapter {

    private static final int FOOTVIEW = 0;
    private static final int CONTENTVIEW = 1;
    private List<MV> mMvs;
    private Context mContext;

    public VideoAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<MV> mvs){
        mMvs = mvs;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == FOOTVIEW){
            View view = inflater.inflate(R.layout.item_footview,parent,false);
            return new FootViewHolder(view);
        }else if(viewType == CONTENTVIEW){
            View view = inflater.inflate(R.layout.item_video, parent, false);
            VideoViewHolder viewHolder = new VideoViewHolder(view);
            NiceVideoPlayerController controller = new NiceVideoPlayerController(mContext);
            viewHolder.setController(controller);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VideoViewHolder){
            VideoViewHolder viewHolder = (VideoViewHolder) holder;
            MV mv = mMvs.get(position);
            viewHolder.bindData(mv);
        }
    }

    @Override
    public int getItemCount() {
        return mMvs.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return isFootView(position) ? FOOTVIEW : CONTENTVIEW;
    }

    private boolean isFootView(int position) {
        return position == mMvs.size();
    }
}
