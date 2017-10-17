package com.hengyi.baseandroidcore.app;

import com.hengyi.baseandroidcore.base.BaseApplication;
import com.hengyi.baseandroidcore.utils.VersionUtils;

/**
 * Created by Administrator on 2017/10/10.
 * 更新管理，这里不包括热更新。
 * 1.init初始化
 * 2.checkUpdate
 * 3.addDownloadManager
 * 4.autoInstall
 */

public class AppUpdate {

    public boolean checkUpdate(String new_version){
        return !VersionUtils.getAppVersion(BaseApplication.getApplication(),"1.0.0.0").equals(new_version);
    }

    

}
