package com.xiaoming.slience.global;

/**
 * @author slience
 * @des
 * @time 2017/6/1019:28
 */

public class Global_Url {
    /**
     * 服务器地址
     */
    public static final String SERVER = "https://moment.douban.com/api/";
    /**
     * 分组
     */
    public static final String COLUMNS = SERVER + "columns";
    /**
     * 栏目文章及翻页
     */
    public static String GETCOLUMNPOSTS = SERVER + "column/{columnId}/posts?max_id={maxId}";
    /**
     * 图片
     */
    public static String PHOTOS = "http://image.baidu.com/channel/listjson?pn=0&rn=30&tag1=%E7%BE%8E%E5%A5%B3";
}
