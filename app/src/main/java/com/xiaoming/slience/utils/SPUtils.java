package com.xiaoming.slience.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author slience
 * @des
 * @time 2017/7/814:06
 */

public class SPUtils {
    /**
     * 把对象已String的形式保存到SP中
     *
     * @param context 上下文
     * @param t       泛型参数
     * @param spName  Sp文件名
     * @param keyName 字段名
     */
    public static void SaveObj2SP(Context context, Object object, String spName, String keyName) {
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        ByteArrayOutputStream bos;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            byte[] bytes = bos.toByteArray();
            String ObjStr = Base64.encodeToString(bytes, Base64.DEFAULT);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(keyName, ObjStr);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 写入文件名和字段名相同的SP
     *
     * @param context
     * @param object
     * @param spName
     */
    public static void saveObj2SP(Context context, Object object, String spName) {
        SaveObj2SP(context, object, spName, spName);
    }

    /**
     * 从sp中读取对象
     *
     * @param context
     * @param spName
     * @param keyNme
     * @param <T>
     * @return
     */
    public static Object getObjFromSp(Context context, String spName, String keyNme) {
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        byte[] bytes = Base64.decode(preferences.getString(keyNme, ""), Base64.DEFAULT);
        ByteArrayInputStream bis;
        ObjectInputStream ois = null;
        Object obj = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    /**
     * 读取文件名和字段名相同的SP对象
     *
     * @param context
     * @param spName
     * @param <T>
     * @return
     */
    public static Object getObjFromSp(Context context, String spName) {
        return getObjFromSp(context, spName, spName);
    }

    /**
     * 删除SP中指定的对象
     * @param context
     * @param spName
     * @param keyName
     */
    public static void RemoveObj2SP(Context context,String spName, String keyName) {
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(keyName);
        editor.commit();
    }

    /**
     * 删除键名和SP名相同的SP对象
     * @param context
     * @param spName
     */
    public static void RemoveObj2sp(Context context,String spName){
        RemoveObj2SP(context,spName,spName);
    }

}
