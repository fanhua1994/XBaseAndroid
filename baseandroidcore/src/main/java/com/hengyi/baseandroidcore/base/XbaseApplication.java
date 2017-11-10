package com.hengyi.baseandroidcore.base;

import android.app.Application;

import com.hengyi.baseandroidcore.utilscode.CrashUtils;
import com.hengyi.baseandroidcore.utilscode.Utils;

/**
 * Created by Administrator on 2017/9/12.
 */

public class XbaseApplication extends Application {
    private static XbaseApplication application = null;

    public static XbaseApplication getApplication(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        Utils.init(this);
        CrashUtils.init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
