package com.hengyi.baseandroidcore.base;

import android.app.Application;

import com.easydblib.EasyDBConfig;
import com.hengyi.baseandroidcore.utilscode.CrashUtils;

/**
 * Created by Administrator on 2017/9/12.
 */

public class BaseApplication extends Application {
    private static BaseApplication application;

    public static BaseApplication getApplication(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        EasyDBConfig.init()
                .showDBLog(true)//显示数据库操作日志
                .setLogTAG("EASY_DB")//日志显示标识
                .build();

        CrashUtils.init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
