package com.hengyi.baseandroidcore.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by Jabez on 2016/6/4.
 */
public class WindowUtils {
    public static int getWindowWidth(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素)
        return width;
    }
    public static int getWindowHeight(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int height = metric.heightPixels;   // 屏幕高度（像素)
        return height;
    }
    public static float getWindowDensity(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5
        return density;
    }
    public static float getWindowDensityDpi(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240
        return densityDpi;
    }
    public static float getPixelFromDip(Activity activity,int dp){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float scale = metrics.density;
        return dp*scale;
    }
}