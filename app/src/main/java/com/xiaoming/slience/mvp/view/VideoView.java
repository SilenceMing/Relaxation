package com.xiaoming.slience.mvp.view;

import com.xiaoming.slience.base.BaseView;
import com.xiaoming.slience.bean.MV;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1316:45
 */

public interface VideoView extends BaseView{
    void onRequestData(List<MV> mvList,boolean isLoadMore);
}
