package com.hengyi.baseandroidcore.listener;

import java.io.File;

/**
 * Created by fanhua on 18-3-4.
 */

public interface FileDownloadListener{
    public void downloadProgressBar(String progress,int progress2,String speed);
    public void downloadSuccess(File app_path);
    public void downloadStart();
    public void downloadError(String message);
    public void downloadFinish();
    public void cancelDownload();
    public void NoUpdate();
}
