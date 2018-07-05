package com.hengyi.baseandroidcore.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fanhua on 2017/12/4.
 */
public class ActivityRouter {
    private static ActivityRouter instance = null;
    private static Map<String,Object> params = new HashMap<>();

    public static synchronized ActivityRouter getInstance(){
        synchronized (ActivityRouter.class){
            if(instance == null){
                instance = new ActivityRouter();
                params.clear();
            }
            return instance;
        }
    }

    public ActivityRouter add(String key,Boolean value){
        params.put(key,value);
        return instance;
    }

    public ActivityRouter add(String key,String value){
        params.put(key,value);
        return instance;
    }

    public ActivityRouter add(String key,Long value){
        params.put(key,value);
        return instance;
    }

    public ActivityRouter add(String key,Integer value){
        params.put(key,value);
        return instance;
    }

    public ActivityRouter add(String key,Double value){
        params.put(key,value);
        return instance;
    }

    public ActivityRouter add(String key,Bundle value){
        params.put(key,value);
        return instance;
    }

    public ActivityRouter add(String key,Serializable value){
        params.put(key,value);
        return instance;
    }

    public Integer get(Activity context,String key,Integer default_value){
        return context.getIntent().getIntExtra(key,default_value);
    }

    public Boolean get(Activity context,String key,Boolean default_value){
        return context.getIntent().getBooleanExtra(key,default_value);
    }

    public String getString(Activity context,String key){
        return context.getIntent().getStringExtra(key);
    }

    public Long get(Activity context,String key,Long default_value){
        return context.getIntent().getLongExtra(key,default_value);
    }

    public Serializable get(Activity context,String key){
        return context.getIntent().getSerializableExtra(key);
    }

    public Double get(Activity context,String key,Double default_value){
        return context.getIntent().getDoubleExtra(key,default_value);
    }

    public Bundle getBundle(Activity context,String key){
        return context.getIntent().getBundleExtra(key);
    }

    private Intent getIntent(Context context,Class activity){
        Intent intent = new Intent(context,activity);
        Object obj = null;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            obj = entry.getValue();
            if(obj instanceof String){
                intent.putExtra(entry.getKey(),obj.toString());
            }
            if(obj instanceof Integer){
                intent.putExtra(entry.getKey(),(Integer)obj);
            }

            if(obj instanceof Double){
                intent.putExtra(entry.getKey(),(Double)obj);
            }

            if(obj instanceof Boolean){
                intent.putExtra(entry.getKey(),(Boolean)obj);
            }

            if(obj instanceof Long){
                intent.putExtra(entry.getKey(),(Long)obj);
            }

            if(obj instanceof Bundle){
                intent.putExtra(entry.getKey(),(Bundle)obj);
            }

            if(obj instanceof Serializable) {
                intent.putExtra(entry.getKey(),(Serializable)obj);
            }
        }
        return intent;
    }

    //界面跳转 不带参数
    public void startActivity(Context context,Class activity) {
        Intent intent = getIntent(context,activity);
        context.startActivity(intent);
    }

    //带自定义参数  带回调
    public void startActivity(Activity context, Class activity, int result_code) {
        Intent intent = getIntent(context,activity);
        context.startActivityForResult(intent,result_code);
    }

    //======================引入Flag===============================
    //界面跳转 不带参数
    public void startActivity(Context context,Class activity,int flag) {
        Intent intent = getIntent(context,activity);
        intent.addFlags(flag);
        context.startActivity(intent);
    }

    //带自定义参数  带回调
    public void startActivity(Activity context, Class activity, int flag,int result) {
        Intent intent = getIntent(context,activity);
        intent.addFlags(flag);
        context.startActivityForResult(intent, result);
    }

    //带自定义参数
    public void startActivity(Context context,Class activity,String action) {
        Intent intent = getIntent(context,activity);
        intent.setAction(action);
        context.startActivity(intent);
    }

    //带自定义参数  带回调
    public void startActivity(Activity context,Class activity,String action, int result_code) {
        Intent intent = getIntent(context,activity);
        intent.setAction(action);
        context.startActivityForResult(intent,result_code);
    }

    public void closeAllActivity(){
        ActivityStack.getInstance().clearAllActivity();
    }
}
