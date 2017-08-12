package com.xiaoming.slience.mvp.mode;

import com.xiaoming.slience.bean.Collections;
import com.xiaoming.slience.bean.SlienceUser;

import java.io.File;

/**
 * @author slience
 * @des
 * @time 2017/6/2010:23
 */

public interface CollectionsDbModel {

    void collectionsAdd(SlienceUser user,String name, String des, File imgFile, CollectionsDbModelImp.CollectionsListener listener);

    void collectionsUpdate(Collections collections, String name, String des, File imgFile, CollectionsDbModelImp.CollectionsListener listener);

}
