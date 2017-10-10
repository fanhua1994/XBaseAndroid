package com.hengyi.baseandroidcore.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2017/10/10.
 */

public class VersionUtils {

    public static String getAppVersion(Context context,String default_version)
    {
        try
        {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return default_version;
    }
}
