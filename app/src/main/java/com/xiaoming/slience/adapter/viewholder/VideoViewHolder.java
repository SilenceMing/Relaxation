package com.xiaoming.slience.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerController;
import com.xiaoming.slience.R;
import com.xiaoming.slience.bean.MV;

/**
 * @author slience
 * @des
 * @time 2017/6/158:42
 */

public class VideoViewHolder extends RecyclerView.ViewHolder {

    private NiceVideoPlayer mNiceVideoPlayer;
    private NiceVideoPlayerController mController;

    public VideoViewHolder(View itemView) {
        super(itemView);
        mNiceVideoPlayer = (NiceVideoPlayer) itemView.findViewById(R.id.nice_video_player);
    }

    public void setController(NiceVideoPlayerController controller){
        mController = controller;
    }
    public void bindData(final MV mv){
        mController.setTitle(mv.title);
        mController.setImage(mv.imgUrl);
        mNiceVideoPlayer.setController(mController);
        mNiceVideoPlayer.setUp(mv.mvUrl, null);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.PLAYER_TYPE_IJK);
    }


}
