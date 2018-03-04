package com.hengyi.baseandroidcore.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Administrator on 2017/10/10.
 */

public class BroadcastUtils {
    //发送广播
    public static void sendBroadcast(Context mContext,String action,String[] names, Object... param){
        Intent intent = new Intent();
        intent.setAction(action);
        for (int i = 0; i < param.length; i++) {
            if (param[i].getClass().equals(Integer.class)) {
                intent.putExtra(names[i], (Integer) param[i]);
            } else if (param[i].getClass().equals(String.class)) {
                intent.putExtra(names[i], (String) param[i]);
            } else if (param[i].getClass().equals(Boolean.class)) {
                intent.putExtra(names[i], (Boolean) param[i]);
            } else if (param[i].getClass().equals(Float.class)) {
                intent.putExtra(names[i], (Float) param[i]);
            } else if (param[i].getClass().equals(Double.class)) {
                intent.putExtra(names[i], (Double) param[i]);
            }
        }
        mContext.sendBroadcast(intent);
    }


    public static void sendBroadcast(Context mContext, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        mContext.sendBroadcast(intent);
    }

    //动态注册广播
    public static void registerBroadcast(Activity activity, BroadcastReceiver broadcastReceiver,String action){
        registerBroadcast(activity,broadcastReceiver,new String[]{action});
    }

    public static void registerBroadcast(Activity activity, BroadcastReceiver broadcastReceiver,String[] actions){
        IntentFilter filter = new IntentFilter();
        for(String action : actions)
            filter.addAction(action);
        activity.registerReceiver(broadcastReceiver,filter);
    }

    //动态注销广播
    public static void unregisterBroadcast(Activity activity,BroadcastReceiver broadcastReceiver){
        activity.unregisterReceiver(broadcastReceiver);
    }
}
