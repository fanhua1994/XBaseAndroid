package com.hengyi.baseandroidcore.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/*
 * SharedPreferences  帮助类
 * 作者：繁华
 * csdn:http://blog.csdn.net/dong_18383219470?viewmode=list
 */

public class ConfigUtils {

    private static SharedPreferences sp = null;
    private static SharedPreferences.Editor edit = null;
    private static ConfigUtils instance = null;
    private static Map<String,Object> data_list = null;
    private static String data_list_name = "config";


    public static ConfigUtils getInstance(Context context){
        if(instance == null){
            sp = context.getSharedPreferences(data_list_name,Context.MODE_PRIVATE);
            instance = new ConfigUtils();
            if(data_list == null)
                data_list = new HashMap<String,Object>();
        }
        return instance;
    }

    //设置配置文件的名称
    public void setConfigName(String configName){
        this.data_list_name = configName;
    }

    /*
     * 添加或者更新一个方法
     */
    public boolean addOrUpdateText(String key,String content){
        if(edit == null){
            edit = sp.edit();
        }
        data_list.put(key,content);
        edit.putString(key, content);
        return edit.commit();
    }

    public boolean addOrUpdateIntNumber(String key,int content){
        if(edit == null){
            edit = sp.edit();
        }
        data_list.put(key,content);
        edit.putInt(key, content);
        return edit.commit();
    }

    public boolean addOrUpdateFloatNumber(String key,float content){
        if(edit == null){
            edit = sp.edit();
        }
        data_list.put(key,content);
        edit.putFloat(key, content);
        return edit.commit();
    }

    public boolean addOrUpdateBoolean(String key,boolean content){
        if(edit == null){
            edit = sp.edit();
        }
        data_list.put(key,content);
        edit.putBoolean(key, content);
        return edit.commit();
    }

    /*
     * 查询
     */

    public String findStringByKey(String key){
        if(data_list.containsKey(key))
            return data_list.get(key).toString();
        else
            return sp.getString(key, null);
    }


    public String findStringByKey(String key,String default_value){
        if(data_list.containsKey(key)){
            return data_list.get(key).toString();
        }else{
            return sp.getString(key, default_value);
        }
    }

    public boolean findBoolByKey(String key){
        if(data_list.containsKey(key)){
            return (Boolean)data_list.get(key);
        }else{
            return sp.getBoolean(key,false);
        }
    }

    public boolean findBoolByKey(String key,boolean default_value){
        if(data_list.containsKey(key)){
            return (Boolean)data_list.get(key);
        }else{
            return sp.getBoolean(key,default_value);
        }
    }

    public int findIntByKey(String key){
        if(data_list.containsKey(key)){
            return (Integer)data_list.get(key);
        }else{
            return sp.getInt(key, -1);
        }
    }

    public int findIntByKey(String key,int default_value){
        if(data_list.containsKey(key)){
            return (Integer)data_list.get(key);
        }else{
            return sp.getInt(key, default_value);
        }
    }

    public float findFloatByKey(String key){
        if(data_list.containsKey(key)){
            return (Float)data_list.get(key);
        }else{
            return sp.getFloat(key,-1);
        }
    }

    public float findFloatByKey(String key,float default_value){
        if(data_list.containsKey(key)){
            return (Float)data_list.get(key);
        }else{
            return sp.getFloat(key,default_value);
        }
    }

    /*
     * 删除
     */
    public void deleteByKey(String key){
        if(edit == null){
            edit = sp.edit();
        }
        data_list.remove(key);
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
        data_list.clear();
        edit.clear();
        edit.commit();
    }
}
