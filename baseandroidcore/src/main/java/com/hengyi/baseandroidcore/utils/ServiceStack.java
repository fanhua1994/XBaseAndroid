package com.hengyi.baseandroidcore.utils;

import android.app.Activity;
import android.app.Service;

import com.hengyi.baseandroidcore.base.XBaseApplication;

import java.util.Stack;

/**
 * Created by 繁华 on 2017/5/14.
 */

public class ServiceStack {
    private static Stack<Class> mActivityStack = new Stack<Class>();
    private static ServiceStack instance = null;

    public static synchronized ServiceStack getInstance() {
        if(instance == null)
            instance = new ServiceStack();
        return instance;
    }
    // 弹出当前activity并销毁
    public void popService(Class service) {
        if (service != null) {
            ServiceUtils.StopService(XBaseApplication.getApplication(),service);
            mActivityStack.remove(service);
        }
    }

    public void popService(){
        if(mActivityStack.size() != 0){
            Class service = mActivityStack.pop();
            popService(service);
        }
    }

    // 将当前Activity推入栈中
    public void pushService(Class service) {
        mActivityStack.add(service);
    }
    // 退出栈中所有Activity
    public void stopAllService() {
        while (!mActivityStack.isEmpty()) {
            Class service = mActivityStack.pop();
            popService(service);
        }
    }
}