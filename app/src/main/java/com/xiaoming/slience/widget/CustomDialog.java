package com.xiaoming.slience.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @author slience
 * @des
 * @time 2017/6/2417:20
 */

public class CustomDialog {

    private Context mContext;

    public CustomDialog(Context context) {
        mContext = context;
    }

    public Object[] Custom(int themeResId,int resourceId,int gravity) {
        Dialog mBottomDialog = new Dialog(mContext, themeResId);
        View contentView = LayoutInflater.from(mContext).inflate(resourceId, null);
        mBottomDialog.setContentView(contentView);
        mBottomDialog.getWindow().setGravity(gravity);
        mBottomDialog.setCanceledOnTouchOutside(true);
        mBottomDialog.show();
        return new Object[]{mBottomDialog,contentView};
    }
}
