package com.hengyi.baseandroidcore.base;

import android.app.Application;

import com.hengyi.baseandroidcore.utilscode.CrashUtils;
import com.hengyi.baseandroidcore.utilscode.LogUtils;
import com.hengyi.baseandroidcore.utilscode.Utils;

/**
 * Created by Administrator on 2017/9/12.
 */

public class BaseApplication extends Application {
    private static BaseApplication application = null;

    public static BaseApplication getApplication(){
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
