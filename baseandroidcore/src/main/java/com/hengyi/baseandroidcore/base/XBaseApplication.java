package com.hengyi.baseandroidcore.base;

import android.app.Application;

import com.hengyi.baseandroidcore.utilscode.CrashUtils;
import com.hengyi.baseandroidcore.utilscode.Utils;
import com.lzy.okgo.OkGo;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2017/9/12.
 */

public class XBaseApplication extends Application {
    private static XBaseApplication application = null;

    public static XBaseApplication getApplication(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Utils.init(this);
        OkGo.getInstance().init(this);
        CrashUtils.init();
        initLeakCanary();
    }

    private void initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
