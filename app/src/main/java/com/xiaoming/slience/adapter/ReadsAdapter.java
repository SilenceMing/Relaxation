package com.xiaoming.slience.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xiaoming.slience.R;
import com.xiaoming.slience.adapter.viewholder.CollectionViewHolder;
import com.xiaoming.slience.bean.Reads;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1815:00
 */

public class ReadsAdapter extends BaseAdapter {

    private List<Reads> mReadses;
    private Context mContext;

    public ReadsAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Reads> readses){
        mReadses = readses;
    }

    @Override
    public int getCount() {
        return mReadses.size();
    }

    @Override
    public Object getItem(int position) {
        return mReadses.get(position);
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
        holder.bindReadsData(mReadses.get(position));
        return convertView;
    }


}
