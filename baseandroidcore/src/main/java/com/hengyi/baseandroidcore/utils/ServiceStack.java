package com.hengyi.baseandroidcore.utils;

import android.app.Activity;
import android.app.Service;

import com.hengyi.baseandroidcore.base.XBaseApplication;

import java.util.Stack;

/**
 * Created by 繁华 on 2017/5/14.
 */

public class ServiceStack {
    private static Stack<Service> mActivityStack = new Stack<Service>();
    private static ServiceStack instance = null;

    public static synchronized ServiceStack getInstance() {
        if(instance == null)
            instance = new ServiceStack();
        return instance;
    }
    // 弹出当前activity并销毁
    public void popService(Service service) {
        if (service != null) {
            ServiceUtils.CloseService(XBaseApplication.getApplication(),service.getClass());
            mActivityStack.remove(service);
        }
    }

    public void popService(){
        if(mActivityStack.size() != 0){
            Service service = mActivityStack.pop();
            popService(service);
        }
    }

    // 将当前Activity推入栈中
    public void pushService(Service service) {
        mActivityStack.add(service);
    }
    // 退出栈中所有Activity
    public void stopAllService() {
        while (!mActivityStack.isEmpty()) {
            Service service = mActivityStack.pop();
            popService(service);
        }
    }
}