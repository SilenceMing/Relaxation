package com.xiaoming.slience.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoming.slience.bean.Tabs;
import com.xiaoming.slience.pager.HotLeftPager;
import com.xiaoming.slience.utils.ACache;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1017:16
 */

public class HotLeftAdapter extends PagerAdapter {

    private List<Tabs> mTabsList;
    private Activity mActivity;
    private ACache mACache;

    public HotLeftAdapter(Activity activity, ACache ACache) {
        mActivity = activity;
        mACache = ACache;
    }

    public void setData(List<Tabs> tabs){
        mTabsList = tabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabsList.get(position).getTabName();
    }

    @Override
    public int getCount() {
        return mTabsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        HotLeftPager pager = new HotLeftPager(mActivity,mTabsList.get(position),mACache);
        View view = pager.view;
        container.addView(view);
        pager.initData();
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
