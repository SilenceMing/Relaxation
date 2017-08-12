package com.xiaoming.slience.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.xiaoming.slience.R;

/**
 * @author slience
 * @des
 * @time 2017/6/159:23
 */

public class FootViewHolder extends RecyclerView.ViewHolder {
    private LinearLayout mLinearLayout;
    public FootViewHolder(View itemView) {
        super(itemView);
        mLinearLayout = (LinearLayout) itemView.findViewById(R.id.ll_loadMore);
    }
}
