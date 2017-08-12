package com.xiaoming.slience.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.widgets.ProgressDialog;
import com.xiaoming.slience.utils.ACache;
import com.xiaoming.slience.utils.FileUtils;

/**
 * @author Slience_Manager
 * @time 2017/4/27 21:08
 */

public abstract class BaseFragment extends Fragment{
    public Activity mActivity;
    public ACache mACache;
    private ProgressDialog mDialog;

    //fragment创建时
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        FileUtils fileUtils = new FileUtils(mActivity);
        mACache = ACache.get(fileUtils.mkCacheDir());
    }

    //初始化Fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = initView(inflater,container);
        return view;
    }

    //fragment所依赖的Activity的Oncreate()方法执行完毕后
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 初始化布局
     *
     * @return
     */
    public abstract View initView(LayoutInflater inflater,ViewGroup container);

    /**
     * 初始化数据
     */
    public abstract void initData();

}
