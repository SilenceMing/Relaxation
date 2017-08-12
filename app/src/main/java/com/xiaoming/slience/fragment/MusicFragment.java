package com.xiaoming.slience.fragment;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gc.materialdesign.views.Slider;
import com.xiaoming.slience.R;
import com.xiaoming.slience.base.BaseFragment;

import java.io.IOException;

/**
 * @author Slience_Manager
 * @time 2017/4/28 9:59
 */

public class MusicFragment extends BaseFragment {

    private TextView mTvTime;
    private Slider mSdBar;
    private TextView mTvTimeCount;
    private Button mBtnPlayer;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        init(view);
        return view;
    }

    private void init(View v) {
        mTvTime = (TextView) v.findViewById(R.id.tv_time);
        mSdBar = (Slider) v.findViewById(R.id.sd_bar);
        mTvTimeCount = (TextView) v.findViewById(R.id.tv_timeCount);
        mBtnPlayer = (Button) v.findViewById(R.id.btn_player);
    }

    @Override
    public void initData() {
        final MediaPlayer mediaPlayer = new MediaPlayer();
        mBtnPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaPlayer.setDataSource("/mnt/sdcard/slience/1.mp3");
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
