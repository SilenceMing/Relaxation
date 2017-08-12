package com.xiaoming.slience.adapter;

import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.xiaoming.slience.R;
import com.xiaoming.slience.bean.Tabs;
import com.xiaoming.slience.utils.DBManager;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/229:33
 */

public class TabSelectAdapter extends BaseAdapter {

    private List<Tabs> mTabsList;
    private DBManager mDbManager;

    public TabSelectAdapter(DBManager DBManager) {
        mDbManager = DBManager;
    }

    public void setData(List<Tabs> queryTabsList){
        mTabsList = queryTabsList;
    }

    @Override
    public int getCount() {
        return mTabsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTabsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab_select,null);
            holder = new ViewHolder();
            holder.mTvContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.mSwCheck = (SwitchCompat) convertView.findViewById(R.id.sw_check);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvContent.setText(mTabsList.get(position).getTabName());
        if(mTabsList.get(position).getVisible().equals("1")){
            holder.mSwCheck.setChecked(true);
        }else{
            holder.mSwCheck.setChecked(false);
        }
        holder.mSwCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {

                buttonView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(position+"----"+isChecked);
                        Tabs tabs = mTabsList.get(position);
                        if(isChecked){
                            tabs.setVisible("1");
                        }else{
                            tabs.setVisible("0");
                        }
                        mDbManager.updateTabs(tabs);
                    }
                });
            }
        });
        return convertView;
    }

}
class ViewHolder{
    TextView mTvContent;
    SwitchCompat mSwCheck;
}
