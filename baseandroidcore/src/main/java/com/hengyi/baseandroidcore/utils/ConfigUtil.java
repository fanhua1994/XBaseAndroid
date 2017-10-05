package com.hengyi.baseandroidcore.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 繁华 on 2017/5/14.
 */

public class ConfigUtil {
    /*
     * SharedPreferences  帮助类
     * 作者：繁华
     * csdn:http://blog.csdn.net/dong_18383219470?viewmode=list
     */
    private static SharedPreferences sp = null;
    private static SharedPreferences.Editor edit = null;
    private static ConfigUtil instance = null;


    public static ConfigUtil getInstance(Context context){
        if(instance == null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
            instance = new ConfigUtil();
        }
        return instance;
    }

    public static ConfigUtil getInstance(String config,Context context){
        if(instance == null){
            sp = context.getSharedPreferences(config,Context.MODE_PRIVATE);
            instance = new ConfigUtil();
        }
        return instance;
    }

    /*
     * 添加或者更新一个方法
     */
    public boolean AddOrUpdateText(String key,String content){
        if(edit == null){
            edit = sp.edit();
        }
        edit.putString(key, content);
        return edit.commit();
    }

    public boolean AddOrUpdateIntNumber(String key,int content){
        if(edit == null){
            edit = sp.edit();
        }
        edit.putInt(key, content);
        return edit.commit();
    }

    public boolean AddOrUpdateFloatNumber(String key,float content){
        if(edit == null){
            edit = sp.edit();
        }
        edit.putFloat(key, content);
        return edit.commit();
    }

    public boolean AddOrUpdateBoolean(String key,boolean content){
        if(edit == null){
            edit = sp.edit();
        }
        edit.putBoolean(key, content);
        return edit.commit();
    }

    /*
     * 查询
     */

    public String FindStringByKey(String key){
        return sp.getString(key, null);
    }

    public boolean FindBoolByKey(String key){
        return sp.getBoolean(key,false);
    }

    public int FindIntByKey(String key){
        return sp.getInt(key, -1);
    }

    /*
     * 删除
     */
    public void DeleteByKey(String key){
        if(edit == null){
            edit = sp.edit();
        }
        edit.remove(key);
        edit.commit();
    }

    /*
     * 清空
     */
    public void ClearShared(){
        if(edit == null){
            edit = sp.edit();
        }
        edit.clear();
        edit.commit();
    }
}
