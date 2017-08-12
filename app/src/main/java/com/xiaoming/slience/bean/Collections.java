package com.xiaoming.slience.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author slience
 * @des  收藏集对象
 * @time 2017/6/209:26
 */

public class Collections extends BmobObject {

    private String Collections_Name;
    private String Collections_Des;
    private BmobFile Collections_Img;
    private SlienceUser mUser;

    public SlienceUser getUser() {
        return mUser;
    }

    public void setUser(SlienceUser user) {
        mUser = user;
    }

    public String getCollections_Name() {
        return Collections_Name;
    }

    public void setCollections_Name(String collections_Name) {
        Collections_Name = collections_Name;
    }

    public String getCollections_Des() {
        return Collections_Des;
    }

    public void setCollections_Des(String collections_Des) {
        Collections_Des = collections_Des;
    }

    public BmobFile getCollections_Img() {
        return Collections_Img;
    }

    public void setCollections_Img(BmobFile collections_Img) {
        Collections_Img = collections_Img;
    }
}
