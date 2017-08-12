package com.xiaoming.slience.bean;

import cn.bmob.v3.BmobObject;

/**
 * @author slience
 * @des
 * @time 2017/7/916:15
 */

public class Likes extends BmobObject{
    private String Likes_Title;
    private String Likes_LikeCount;
    private String Likes_Time;
    private String Likes_Des;
    private String Likes_CommCount;
    private String Likes_Url;
    private String Likes_ImgUrl;
    private SlienceUser mUser;

    public String getLikes_Title() {
        return Likes_Title;
    }

    public void setLikes_Title(String likes_Title) {
        Likes_Title = likes_Title;
    }

    public String getLikes_LikeCount() {
        return Likes_LikeCount;
    }

    public void setLikes_LikeCount(String likes_LikeCount) {
        Likes_LikeCount = likes_LikeCount;
    }

    public String getLikes_Time() {
        return Likes_Time;
    }

    public void setLikes_Time(String likes_Time) {
        Likes_Time = likes_Time;
    }

    public String getLikes_Des() {
        return Likes_Des;
    }

    public void setLikes_Des(String likes_Des) {
        Likes_Des = likes_Des;
    }

    public String getLikes_CommCount() {
        return Likes_CommCount;
    }

    public void setLikes_CommCount(String likes_CommCount) {
        Likes_CommCount = likes_CommCount;
    }

    public String getLikes_Url() {
        return Likes_Url;
    }

    public void setLikes_Url(String likes_Url) {
        Likes_Url = likes_Url;
    }

    public String getLikes_ImgUrl() {
        return Likes_ImgUrl;
    }

    public void setLikes_ImgUrl(String likes_ImgUrl) {
        Likes_ImgUrl = likes_ImgUrl;
    }

    public SlienceUser getUser() {
        return mUser;
    }

    public void setUser(SlienceUser user) {
        mUser = user;
    }
}
