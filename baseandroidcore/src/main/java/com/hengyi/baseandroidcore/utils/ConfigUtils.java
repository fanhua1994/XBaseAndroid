package com.hengyi.baseandroidcore.utils;

import android.content.Context;
import android.content.SharedPreferences;

/*
 * SharedPreferences  帮助类
 * 作者：繁华
 * csdn:http://blog.csdn.net/dong_18383219470?viewmode=list
 */

public class ConfigUtils {

    private static SharedPreferences sp = null;
    private static SharedPreferences.Editor edit = null;
    private static ConfigUtils instance = null;


    public static ConfigUtils getInstance(Context context){
        if(instance == null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
            instance = new ConfigUtils();
        }
        return instance;
    }

    public static ConfigUtils getInstance(String config, Context context){
        if(instance == null){
            sp = context.getSharedPreferences(config,Context.MODE_PRIVATE);
            instance = new ConfigUtils();
        }
        return instance;
    }

    /*
     * 添加或者更新一个方法
     */
    public boolean addOrUpdateText(String key,String content){
        if(edit == null){
            edit = sp.edit();
        }
        edit.putString(key, content);
        return edit.commit();
    }

    public boolean addOrUpdateIntNumber(String key,int content){
        if(edit == null){
            edit = sp.edit();
        }
        edit.putInt(key, content);
        return edit.commit();
    }

    public boolean addOrUpdateFloatNumber(String key,float content){
        if(edit == null){
            edit = sp.edit();
        }
        edit.putFloat(key, content);
        return edit.commit();
    }

    public boolean addOrUpdateBoolean(String key,boolean content){
        if(edit == null){
            edit = sp.edit();
        }
        edit.putBoolean(key, content);
        return edit.commit();
    }

    /*
     * 查询
     */

    public String findStringByKey(String key){
        return sp.getString(key, null);
    }


    public String findStringByKey(String key,String default_value){
        return sp.getString(key, default_value);
    }

    public boolean findBoolByKey(String key){
        return sp.getBoolean(key,false);
    }

    public boolean findBoolByKey(String key,boolean default_value){
        return sp.getBoolean(key,default_value);
    }

    public int findIntByKey(String key){
        return sp.getInt(key, -1);
    }

    public int findIntByKey(String key,int default_value){
        return sp.getInt(key, default_value);
    }

    public float findFloatByKey(String key){
        return sp.getFloat(key,-1);
    }

    public float findFloatByKey(String key,float default_value){
        return sp.getFloat(key,default_value);
    }

    /*
     * 删除
     */
    public void deleteByKey(String key){
        if(edit == null){
            edit = sp.edit();
        }
        edit.remove(key);
        edit.commit();
    }

    /*
     * 清空
     */
    public void clearConfig(){
        if(edit == null){
            edit = sp.edit();
        }
        edit.clear();
        edit.commit();
    }
}
