package com.xiaoming.slience.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1019:57
 */

public class Columns implements Serializable{

    private List<ColumnsBean> columns;

    public List<ColumnsBean> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnsBean> columns) {
        this.columns = columns;
    }

    public static class ColumnsBean implements Serializable {

        private int id;
        private String icon;
        private String description;
        private String name;
        private int post_total;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPost_total() {
            return post_total;
        }

        public void setPost_total(int post_total) {
            this.post_total = post_total;
        }
    }
}
