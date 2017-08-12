package com.xiaoming.slience.mvp.mode;

import com.xiaoming.slience.bean.SlienceUser;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * @author slience
 * @des
 * @time 2017/7/812:50
 */

public class UserModelImp implements UserModel{
    @Override
    public void UserAdd(String phone, String name, String password, String des, final UserListener listener) {
        SlienceUser user = new SlienceUser();
        user.setUser_Phone(phone);
        user.setUser_Name(name);
        user.setUser_Password(password);
        user.setUser_Des(des);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    listener.onSuccess();
                }else{
                    listener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void UserFindPw(String phone, final String password, final UserListener listener) {
        BmobQuery<SlienceUser> query = new BmobQuery<>();
        query.addWhereEqualTo("User_Phone",phone);
        query.findObjects(new FindListener<SlienceUser>() {
            @Override
            public void done(List<SlienceUser> list, BmobException e) {
                if(e == null){
                    SlienceUser user = list.get(0);
                    user.setUser_Password(password);
                    listener.onSuccess();
                }else{
                    listener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void UserLogin(String phone, final String password, final UserLoginListener listener) {
        BmobQuery<SlienceUser> query = new BmobQuery<>();
        query.addWhereEqualTo("User_Phone",phone);
        query.findObjects(new FindListener<SlienceUser>() {
            @Override
            public void done(List<SlienceUser> list, BmobException e) {
                if(e == null){
                    SlienceUser user = list.get(0);
                    if(user.getUser_Password().equals(password)){
                        listener.onSuccess(user);
                    }
                }else{
                    listener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void UserLoginIntegra(String phone, String name, String password, String des, final UserLoginListener listener) {
        final SlienceUser user = new SlienceUser();
        user.setUser_Phone(phone);
        user.setUser_Name(name);
        user.setUser_Password(password);
        user.setUser_Des(des);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    listener.onSuccess(user);
                }else{
                    listener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void UserUpdata(final SlienceUser user, final BmobFile file, final String username, final String des, final UserListener listener) {
        if(file == null){
            SlienceUser slienceUser = new SlienceUser();
            slienceUser.setUser_Name(username);
            slienceUser.setUser_Des(des);
            slienceUser.update(user.getObjectId(),new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e == null){
                        listener.onSuccess();
                    }else{
                        listener.onFailure(e);
                    }
                }
            });
        }else{
            file.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e == null){
                        SlienceUser slienceUser = new SlienceUser();
                        slienceUser.setUser_Name(username);
                        slienceUser.setUser_Des(des);
                        slienceUser.setUser_Image(file);
                        slienceUser.update(user.getObjectId(),new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null){
                                    listener.onSuccess();
                                }else{
                                    listener.onFailure(e);
                                }
                            }
                        });
                    }else{
                        listener.onFailure(e);
                    }
                }
            });
        }
    }

    public interface UserListener {
        void onSuccess();
        void onFailure(BmobException e);
    }
    public interface UserLoginListener{
        void onSuccess(SlienceUser user);
        void onFailure(BmobException e);
    }

}
