package com.hengyi.baseandroidcore.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/12/4.
 */

public class ActivityUtils {
    private static Intent intent = null;

    //显示toast
    public static void toast(Context context, String toast_text) {
        Toast.makeText(context, toast_text, Toast.LENGTH_LONG).show();
    }

    //杀死进程
    public static void kill(){
        Process.killProcess(Process.myPid());
    }

    //界面跳转 不带参数
    public static void StartActivity(Context context,Class cla) {
        intent = null;
        intent = new Intent(context, cla);
        context.startActivity(intent);
    }

    //带自定义参数
    public  static void StartActivity(Context context,Class cla, String[] names, Object... param) {
        intent = null;
        intent = new Intent(context, cla);
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
        context.startActivity(intent);
    }

    //带自定义参数  带回调
    public static void StartActivity(Activity context, Class cla, int result, String[] names, Object... param) {
        intent = null;
        intent = new Intent(context,cla);
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
        context.startActivityForResult(intent, result);
    }
}
