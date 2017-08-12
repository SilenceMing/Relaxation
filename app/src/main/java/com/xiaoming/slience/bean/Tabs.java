package com.xiaoming.slience.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author slience
 * @des
 * @time 2017/6/2117:56
 */
@Entity
public class Tabs {
    @Id
    private Long id;
    @Property(nameInDb = "TabName")
    private String tabName;
    @Property(nameInDb = "TabId")
    private String tabId;
    @Property(nameInDb = "VISIBLE")
    private String visible;
    @Generated(hash = 1863161359)
    public Tabs() {
    }
    @Generated(hash = 880461421)
    public Tabs(Long id, String tabName, String tabId, String visible) {
        this.id = id;
        this.tabName = tabName;
        this.tabId = tabId;
        this.visible = visible;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }
}
