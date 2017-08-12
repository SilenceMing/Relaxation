package com.xiaoming.slience.utils;

import com.bumptech.glide.Glide;

import cn.finalteam.galleryfinal.PauseOnScrollListener;

/**
 * @author slience
 * @des
 * @time 2017/6/1920:28
 */

public class GlidePauseOnScrollListener extends PauseOnScrollListener {
    public GlidePauseOnScrollListener(boolean pauseOnScroll, boolean pauseOnFling) {
        super(pauseOnScroll, pauseOnFling);
    }

    @Override
    public void resume() {
        Glide.with(getActivity()).resumeRequests();
    }

    @Override
    public void pause() {
        Glide.with(getActivity()).pauseRequests();
    }
}
