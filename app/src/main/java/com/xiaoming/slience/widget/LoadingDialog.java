package com.xiaoming.slience.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.xiaoming.slience.R;

/**
 * @author slience
 * @des
 * @time 2017/6/2412:34
 */

public class LoadingDialog {

    public Dialog mLoadingDialog;

    public LoadingDialog(Context context) {
        mLoadingDialog = CustomDialog(context);
    }

    private Dialog CustomDialog(Context mContext) {
        Dialog mDialog = new Dialog(mContext, R.style.BottomDialog);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_loading_dialog, null);
        mDialog.setContentView(contentView);
//        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
//        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
//        layoutParams.height = mContext.getResources().getDisplayMetrics().heightPixels / 3;
//        contentView.setLayoutParams(layoutParams);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        return mDialog;
    }
}
