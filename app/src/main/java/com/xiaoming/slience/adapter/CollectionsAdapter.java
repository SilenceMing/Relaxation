package com.xiaoming.slience.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoming.slience.R;
import com.xiaoming.slience.bean.Collections;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * @author slience
 * @des
 * @time 2017/6/1815:00
 */

public class CollectionsAdapter extends BaseAdapter {

    private List<Collections> mCollectionsList;
    private Context mContext;

    public CollectionsAdapter(Context context, List<Collections> list) {
        mContext = context;
        mCollectionsList = list;
    }

    @Override
    public int getCount() {
        return mCollectionsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCollectionsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collections, null, false);
            viewHolder.CollectionImg = (ImageView) convertView.findViewById(R.id.iv_collectionsImg);
            viewHolder.CollectionTitle = (TextView) convertView.findViewById(R.id.tv_collectionsTitle);
            viewHolder.CollectionCount = (TextView) convertView.findViewById(R.id.tv_collectionCount);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BmobFile collections_img = mCollectionsList.get(position).getCollections_Img();
        String collections_imgUrl = collections_img == null?null:collections_img.getFileUrl();
        Glide.with(mContext).load(collections_imgUrl).error(R.mipmap.vp_bg_default).placeholder(R.mipmap.vp_bg_default).into(viewHolder.CollectionImg);
        viewHolder.CollectionTitle.setText(mCollectionsList.get(position).getCollections_Name());
        return convertView;
    }

    class ViewHolder {
        ImageView CollectionImg;
        TextView CollectionTitle;
        TextView CollectionCount;
    }
}
