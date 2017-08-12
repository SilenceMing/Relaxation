package com.xiaoming.slience.bean;

import cn.bmob.v3.BmobObject;

/**
 * @author slience
 * @des
 * @time 2017/6/2018:00
 */

public class Reads extends BmobObject {
    private String Reads_Title;
    private String Reads_LikeCount;
    private String Reads_Time;
    private String Reads_Des;
    private String Reads_CommCount;
    private String Reads_Url;
    private String Reads_ImgUrl;
    private SlienceUser mUser;

    public SlienceUser getUser() {
        return mUser;
    }

    public void setUser(SlienceUser user) {
        mUser = user;
    }

    public String getReads_Title() {
        return Reads_Title;
    }

    public void setReads_Title(String reads_Title) {
        Reads_Title = reads_Title;
    }

    public String getReads_LikeCount() {
        return Reads_LikeCount;
    }

    public void setReads_LikeCount(String reads_LikeCount) {
        Reads_LikeCount = reads_LikeCount;
    }

    public String getReads_Time() {
        return Reads_Time;
    }

    public void setReads_Time(String reads_Time) {
        Reads_Time = reads_Time;
    }

    public String getReads_Des() {
        return Reads_Des;
    }

    public void setReads_Des(String reads_Des) {
        Reads_Des = reads_Des;
    }

    public String getReads_CommCount() {
        return Reads_CommCount;
    }

    public void setReads_CommCount(String reads_CommCount) {
        Reads_CommCount = reads_CommCount;
    }

    public String getReads_Url() {
        return Reads_Url;
    }

    public void setReads_Url(String reads_Url) {
        Reads_Url = reads_Url;
    }

    public String getReads_ImgUrl() {
        return Reads_ImgUrl;
    }

    public void setReads_ImgUrl(String reads_ImgUrl) {
        Reads_ImgUrl = reads_ImgUrl;
    }
}
