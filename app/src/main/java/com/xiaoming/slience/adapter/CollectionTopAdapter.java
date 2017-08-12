package com.xiaoming.slience.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xiaoming.slience.R;
import com.xiaoming.slience.activity.EditCollectionsActivity;
import com.xiaoming.slience.bean.Collections;

/**
 * @author slience
 * @des
 * @time 2017/6/2021:12
 */

public class CollectionTopAdapter extends PagerAdapter {

    private Collections mCollections;
    private TextView mTvCollectionTitle;
    private TextView mTvCollectionCount;
    private TextView mTvCollectionTime;
    private Button mBtnEdit;
    private TextView mTvCollectionAbstract;
    private int CollectionCount;
    private Context mContext;

    public CollectionTopAdapter(Context context) {
        mContext = context;
    }

    public void setData(Collections collections, int size) {
        mCollections = collections;
        CollectionCount = size;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = null;
        if(position == 0){
            view = inflater.inflate(R.layout.item_collection_top_one,null);
            initOneView(view);
        }else if(position == 1){
            view = inflater.inflate(R.layout.item_collection_top_two,null);
            initTwoView(view);
        }
        container.addView(view);
        return view;
    }

    private void initTwoView(View view) {
        mTvCollectionAbstract = (TextView) view.findViewById(R.id.tv_collectionAbstract);
        mTvCollectionAbstract.setText(mCollections.getCollections_Des());
    }

    private void initOneView(View view) {
        mTvCollectionTitle = (TextView) view.findViewById(R.id.tv_CollectionTitle);
        mTvCollectionCount = (TextView) view.findViewById(R.id.tv_CollectionCount);
        mTvCollectionTime = (TextView) view.findViewById(R.id.tv_collectionTime);
        mBtnEdit = (Button) view.findViewById(R.id.btn_edit);
        mTvCollectionTitle.setText(mCollections.getCollections_Name());
        mTvCollectionCount.setText(CollectionCount+"");
        mTvCollectionTime.setText(mCollections.getUpdatedAt());
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditCollectionsActivity.class);
                intent.putExtra(mContext.getString(R.string.edit_Collections),mCollections);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
