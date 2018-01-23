package com.hengyi.baseandroidcore.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.io.File;

/**
 * 获取缓存大小，清理缓存
 * Created by Fanhua on 2018/1/18.
 */
public class CacheAdminUtils {
    private static CacheAdminUtils instance = null;


    public static synchronized CacheAdminUtils getInstance(){
        synchronized (CacheAdminUtils.class){
            if(instance == null)
                instance = new CacheAdminUtils();
            return instance;
        }
    }
    /**
     * 递归获取目录长度
     *
     * @param dir 目录
     * @return 目录长度
     */
    private long getDirLength( File dir) {
        if (!dir.isDirectory()) return -1;
        long len = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    len += getDirLength(file);
                } else {
                    len += file.length();
                }
            }
        }
        return len;
    }

    /**
     * 递归删除文件
     * @param dir
     * @return
     */
    private void deleteDir( File dir) {
        if (!dir.isDirectory()) return ;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDir(file);
                } else {
                    file.delete();
                }
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private String byte2FitMemorySize(final long byteNum) {
        if (byteNum < 0) {
            return "获取错误";
        } else if (byteNum < 1024) {
            return String.format("%.2fB", (double) byteNum);
        } else if (byteNum < 1048576) {
            return String.format("%.2fKB", (double) byteNum / 1024);
        } else if (byteNum < 1073741824) {
            return String.format("%.2fMB", (double) byteNum / 1048576);
        } else {
            return String.format("%.2fGB", (double) byteNum / 1073741824);
        }
    }

    /**
     * 获取外部缓存大小
     */
    public void getCacheSize(final Context context,final Handler handler){
        new Thread(){
            public void run(){
                long total_cache = getDirLength(context.getExternalCacheDir().getParentFile());
                String totalCache =  byte2FitMemorySize(total_cache);
                Message msg = new Message();
                msg.obj = totalCache;
                if(handler != null)
                handler.sendMessage(msg);
            }
        }.start();
    }

    public void deleteCache(final Context context,final Handler handler){
        new Thread(){
            public void run(){
                deleteDir(context.getExternalCacheDir().getParentFile());
                if(handler != null)
                handler.sendEmptyMessage(0);
            }
        }.start();
    }
}
