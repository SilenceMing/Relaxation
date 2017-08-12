package com.xiaoming.slience.mvp.view;

import com.xiaoming.slience.bean.Columns;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1021:04
 */

public interface HotLeftView{
    void onRequestComplete(List<Columns.ColumnsBean> columnsList);
}
