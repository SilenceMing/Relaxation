package com.xiaoming.slience.bean;

import cn.bmob.v3.BmobObject;

/**
 * @author slience
 * @des 收藏对象
 * @time 2017/6/20 9:35
 */

public class Collection extends BmobObject {

    private String Collection_Title;
    private String Collection_LikeCount;
    private String Collection_Time;
    private String Collection_Des;
    private String Collection_CommCount;
    private String Collection_Url;
    private String Collection_ImgUrl;

    public String getCollection_ImgUrl() {
        return Collection_ImgUrl;
    }

    public void setCollection_ImgUrl(String collection_ImgUrl) {
        Collection_ImgUrl = collection_ImgUrl;
    }

    public String getCollection_Des() {
        return Collection_Des;
    }

    public void setCollection_Des(String collection_Des) {
        Collection_Des = collection_Des;
    }

    public String getCollection_CommCount() {
        return Collection_CommCount;
    }

    public void setCollection_CommCount(String collection_CommCount) {
        Collection_CommCount = collection_CommCount;
    }

    public String getCollection_Url() {
        return Collection_Url;
    }

    public void setCollection_Url(String collection_Url) {
        Collection_Url = collection_Url;
    }

    private Collections mCollections;

    public String getCollection_Title() {
        return Collection_Title;
    }

    public void setCollection_Title(String collection_Title) {
        Collection_Title = collection_Title;
    }

    public String getCollection_LikeCount() {
        return Collection_LikeCount;
    }

    public void setCollection_LikeCount(String collection_LikeCount) {
        Collection_LikeCount = collection_LikeCount;
    }

    public String getCollection_Time() {
        return Collection_Time;
    }

    public void setCollection_Time(String collection_Time) {
        Collection_Time = collection_Time;
    }

    public Collections getCollections() {
        return mCollections;
    }

    public void setCollections(Collections collections) {
        mCollections = collections;
    }
}
