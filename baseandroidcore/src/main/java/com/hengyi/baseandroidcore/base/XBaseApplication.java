package com.hengyi.baseandroidcore.base;

import android.app.Application;

import com.hengyi.baseandroidcore.utilscode.CrashUtils;
import com.hengyi.baseandroidcore.utilscode.Utils;
import com.lzy.okgo.OkGo;

/**
 * Created by Administrator on 2017/9/12.
 */

public class XBaseApplication extends Application {
    private static boolean stat_debug = true;
    private static XBaseApplication application = null;

    public static XBaseApplication getApplication(){
        return application;
    }

    public static void setDebug(boolean bug){
        stat_debug = bug;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Utils.init(this);
        OkGo.getInstance().init(this);
        if(!stat_debug)
            CrashUtils.init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
