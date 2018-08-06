package com.hengyi.baseandroidcore.listener;

import java.io.File;

/**
 * Created by fanhua on 18-3-4.
 */

public interface FileDownloadListener{
    void downloadProgressBar(String progress,int progress2,String speed);
    void downloadSuccess(File appPath);
    void downloadStart();
    void downloadError(String message);
    void downloadFinish();
    void cancelDownload();
    void NoUpdate();
}
