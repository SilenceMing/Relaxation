package com.xiaoming.slience.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1216:22
 */

public class Photos implements Serializable{

    public List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{

        private String image_url;
        private String download_url;
        private String thumbnail_url;
        private String thumb_large_url;

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public String getThumbnail_url() {
            return thumbnail_url;
        }

        public void setThumbnail_url(String thumbnail_url) {
            this.thumbnail_url = thumbnail_url;
        }

        public String getThumb_large_url() {
            return thumb_large_url;
        }

        public void setThumb_large_url(String thumb_large_url) {
            this.thumb_large_url = thumb_large_url;
        }
    }
}
