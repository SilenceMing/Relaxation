package com.xiaoming.slience.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.xiaoming.slience.bean.Tabs;
import com.xiaoming.slience.greendao.gen.DaoMaster;
import com.xiaoming.slience.greendao.gen.DaoSession;
import com.xiaoming.slience.greendao.gen.TabsDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * @author slience
 * @des
 * @time 2017/6/2118:04
 */

public class DBManager {
    private final static String dbName = "tab_db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();

        return db;
    }

    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }


    private void DropDatabase(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TabsDao userDao = daoSession.getTabsDao();
        DaoMaster.dropAllTables(userDao.getDatabase(),true);
    }

    /**
     * 插入用户集合
     *
     * @param tabs
     */
    public void insertTabsList(List<Tabs> tabs) {
        if (tabs == null || tabs.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TabsDao userDao = daoSession.getTabsDao();
        userDao.insertInTx(tabs);
    }

    public List<Tabs> queryTabsList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TabsDao tabsDao = daoSession.getTabsDao();
        QueryBuilder<Tabs> qb = tabsDao.queryBuilder();
        List<Tabs> list = qb.list();
        return list;
    }

    public List<Tabs> queryTabsVisibleList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TabsDao tabsDao = daoSession.getTabsDao();
        List<Tabs> list = tabsDao.queryBuilder().where(TabsDao.Properties.Visible.eq("1")).orderAsc(TabsDao.Properties.Id).build().list();
        return list;
    }


    public void updateTabs(Tabs tabs) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TabsDao tabsDao = daoSession.getTabsDao();
        tabsDao.update(tabs);
    }


}
