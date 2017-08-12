package com.xiaoming.slience.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xiaoming.slience.R;
import com.xiaoming.slience.adapter.viewholder.CollectionViewHolder;
import com.xiaoming.slience.bean.Collection;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1815:00
 */

public class CollectionAdapter extends BaseAdapter {

    private List<Collection> mCollectionList;
    private Context mContext;

    public CollectionAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Collection> collections) {
        mCollectionList = collections;
    }

    @Override
    public int getCount() {
        return mCollectionList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCollectionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CollectionViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection,parent,false);
            holder = new CollectionViewHolder(convertView,mContext);
            convertView.setTag(holder);
        }else{
            holder = (CollectionViewHolder) convertView.getTag();
        }
        holder.bindCollectionData(mCollectionList.get(position));
        return convertView;
    }


}
