package com.xiaoming.slience.mvp.mode;

import com.xiaoming.slience.bean.Collections;
import com.xiaoming.slience.bean.SlienceUser;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * @author slience
 * @des
 * @time 2017/6/2010:11
 */

public class CollectionsDbModelImp implements CollectionsDbModel {

    @Override
    public void collectionsAdd(final SlienceUser user, final String name, final String des, final File imgFile, final CollectionsListener listener) {
        final BmobFile bmobFile = new BmobFile(imgFile);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                Collections collections = new Collections();
                collections.setCollections_Name(name);
                collections.setCollections_Des(des);
                collections.setUser(user);
                if (e == null) {
                    collections.setCollections_Img(bmobFile);
                }else{
                    listener.onFailure(e);
                }
                collections.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            listener.onSuccess();
                        }else{
                            listener.onFailure(e);
                        }
                    }
                });
            }
        });
    }

   @Override
    public void collectionsUpdate(final Collections collections, final String name, final String des, File imgFile, final CollectionsListener listener) {
        final BmobFile bmobFile = new BmobFile(imgFile);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                collections.setCollections_Name(name);
                collections.setCollections_Des(des);
                if (e == null) {
                    collections.setCollections_Img(bmobFile);
                }else{
                    listener.onFailure(e);
                }
                collections.update(collections.getObjectId(),new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            listener.onSuccess();
                        }else{
                            listener.onFailure(e);
                        }
                    }
                });
            }
        });
    }

    public interface CollectionsListener {
        void onSuccess();

        void onFailure(BmobException e);
    }
}
