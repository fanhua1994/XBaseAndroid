package com.hengyi.baseandroidcore.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/9/14.
 */

public class WindowUtil {

    public static int getWndowWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getWndowHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
}
