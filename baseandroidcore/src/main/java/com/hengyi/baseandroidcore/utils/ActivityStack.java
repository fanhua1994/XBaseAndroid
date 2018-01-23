package com.hengyi.baseandroidcore.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by 繁华 on 2017/5/14.
 */

public class ActivityStack {
    private static Stack<Activity> mActivityStack = new Stack<Activity>();
    private static ActivityStack instance = null;

    public static synchronized ActivityStack getInstance() {
        if(instance == null)
            instance = new ActivityStack();
        return instance;
    }
    // 弹出当前activity并销毁
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            mActivityStack.remove(activity);
        }
    }

    public void popActivity(){
        if(mActivityStack.size() != 0){
            Activity ac = mActivityStack.pop();
            ac.finish();
            mActivityStack.remove(ac);
        }
    }

    // 将当前Activity推入栈中
    public void pushActivity(Activity activity) {
        mActivityStack.add(activity);
    }
    // 退出栈中所有Activity
    public void clearAllActivity() {
        while (!mActivityStack.isEmpty()) {
            Activity activity = mActivityStack.pop();
            popActivity(activity);
        }
    }
}