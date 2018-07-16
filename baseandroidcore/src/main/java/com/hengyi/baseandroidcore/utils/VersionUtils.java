package com.hengyi.baseandroidcore.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2017/10/10.
 */

public class VersionUtils {
    /**
     * old version：1.2.0.0  老版本
     * now version：1.2.0.7  最新版本
     * 如果old_version 更大 返回-1  new_version 更大 返回1 相等返回 0   出现错误返回-2
     */
    public static int checkVersion(String oldVersion,String newVersion) {
        return check(parseVersion(oldVersion),parseVersion(newVersion));
    }

    public static String getVersionName(Context context,String defaultVersion) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return defaultVersion;
    }

    public static int getVersionCode(Context context,int defaultVersion) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return defaultVersion;
    }

    private static int parseInt(String num) {
        return Integer.parseInt(num);
    }

    private static int[] parseVersion(String version) {
        String[] ver_arr = version.split("\\.");
        int[] version_code = new int[ver_arr.length];
        for(int i = 0;i<ver_arr.length;i++) {
            version_code[i] = parseInt(ver_arr[i]);
        }
        return version_code;
    }

    /**
     * 如果old_version 更大 返回-1  new_version 更大 返回1 相等返回 0   出现错误返回-2
     * @param old_version
     * @param new_version
     * @return
     */
    private static int check(int[] old_version,int[] new_version) {
        if(old_version.length != new_version.length) {
            return -2;
        }

        int o = 0,n = 0;
        for(int i = 0;i < old_version.length;i++) {
            o = old_version[i];
            n = new_version[i];

            //如果是最后一位 并且相等  返回0
            if(i == old_version.length - 1 && o == n) {
                return 0;
            }

            if(o > n) {
                return -1;
            }else if(o < n){
                return 1;
            }

        }
        return -2;
    }
}
