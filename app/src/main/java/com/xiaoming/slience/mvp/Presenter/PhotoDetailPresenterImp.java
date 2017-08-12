package com.xiaoming.slience.mvp.Presenter;


import android.app.Activity;
import android.os.Environment;

import com.gc.materialdesign.widgets.SnackBar;
import com.xiaoming.slience.utils.FileUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * @author slience
 * @des
 * @time 2017/6/1310:17
 */

public class PhotoDetailPresenterImp {

    private Activity mActivity;

    public PhotoDetailPresenterImp(Activity activity) {
        mActivity = activity;
    }

    public void DownloadImg(final String Url){
        String fileName = Url.substring(Url.lastIndexOf("/"));
        if(Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)){
            String filePath = FileUtils.PhotoDownloadPath +"/"+fileName;
            ImgData(filePath,Url);
        }else{
            //TODO  手机内部存储路径设置
            String filePath = "";
            ImgData(filePath,Url);
        }


    }

    private void ImgData(String filePath,String Url) {
        RequestParams params = new RequestParams(Url);
        params.setSaveFilePath(filePath);
        x.http().get(params, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                SnackBar snackBar = new SnackBar(mActivity,"finish");
                snackBar.show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
}
