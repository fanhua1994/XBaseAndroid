package com.hengyi.baseandroidcore.base;

import android.app.Application;

import com.easydblib.EasyDBConfig;
import com.hengyi.baseandroidcore.utils.CrashHandler;
import com.hengyi.baseandroidcore.utils.FileUtil;

/**
 * Created by Administrator on 2017/9/12.
 */

public class BaseApplication extends Application {
    private static BaseApplication application;
    private static boolean DEBUG = true;//设置为true才会向服务器提交BUG日志

    public static BaseApplication getApplication(){
        return application;
    }

    public static boolean getDeBUG(){
        return DEBUG;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        EasyDBConfig.init()
                .showDBLog(true)//显示数据库操作日志
                .setLogTAG("EASY_DB")//日志显示标识
                .build();

        if(DEBUG) {
            CrashHandler handler = CrashHandler.getInstance();
            handler.init(this);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
