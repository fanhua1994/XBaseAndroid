package com.hengyi.baseandroidcore.base;

import android.app.Application;

import com.alipay.euler.andfix.patch.PatchManager;
import com.hengyi.baseandroidcore.utils.VersionUtils;
import com.hengyi.baseandroidcore.utils_ext.CrashUtils;
import com.hengyi.baseandroidcore.utils_ext.Utils;
import com.lzy.okgo.OkGo;

import java.io.File;

/**
 * Created by Administrator on 2017/9/12.
 */

public class XBaseApplication extends Application {
    private static boolean stat_debug = true;
    private static XBaseApplication application = null;
    private static PatchManager patchManager = null;

    public static XBaseApplication getApplication(){
        return application;
    }

    public static PatchManager getPatchManager(){
        return patchManager;
    }

    public static void setDebug(boolean bug){
        stat_debug = bug;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        //热修复
        patchManager = new PatchManager(this);
        patchManager.init(VersionUtils.getAppVersion(this,"1.0.0.0"));//current version
        patchManager.loadPatch();

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
