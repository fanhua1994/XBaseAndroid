package com.hengyi.baseandroidcore.tools;

import android.content.Context;
import android.content.Intent;

import com.hengyi.baseandroidcore.R;
import com.hengyi.baseandroidcore.listener.FileDownloadListener;
import com.hengyi.baseandroidcore.utils.NotificationUtils;
import com.hengyi.baseandroidcore.utils.ProjectUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by fanhua on 18-3-4.
 */

public class FileDownloader {
    private static  FileDownloader instance = null;
    private FileDownloadListener listener = null;
    private NotificationUtils notifacation = null;

    public static synchronized FileDownloader getInstance(){
        synchronized (FileDownloader.class){
            if(instance == null){
                instance = new FileDownloader();
            }
            return instance;
        }
    }

    /**
     * 进度条格式化
     * @param progressbar
     * @return
     */
    private String formatProgress(float progressbar){
        DecimalFormat decimalFormat=new DecimalFormat("##0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(progressbar);//format 返回的是字符串
        return p;
    }

    private String formatSpeed(long speed){
        String down_speed = null;
        //KB计算
        if(speed < 1024 * 1024){
            down_speed = formatProgress((float)speed / 1024.0f) + "kb/s";
        }else{
            //MB计算
            down_speed = formatProgress((float)speed / 1024.0f / 1024.0f) + "mb/s";
        }
        return down_speed;
    }

    public void setDownloadListener(FileDownloadListener listener){
        this.listener = listener;
    }

    /**
     *
     * @param context                   上下文
     * @param downurl                   下载地址
     * @param savepath                  保存路径
     * @param filename                  保存文件名
     * @param show_notification         是否现实通知栏
     * @param image_icon                通知栏图标
     * @param notify_id                 通知ID
     * @param tickerText                小标题
     * @param title                     显示标题
     * @param content                   显示内容
     * @param intent                    意图
     */
    public void download(Context context, String downurl, String savepath, String filename,final boolean show_notification,int image_icon,int notify_id,String tickerText,String title,String content,Intent intent){
        if(show_notification) {
            notifacation = new NotificationUtils(context);
            notifacation.createProgressNotify(image_icon, notify_id, tickerText, title, content, intent);//创建进度条通知栏
        }
        OkGo.<File>get(downurl).tag(this).execute(new FileCallback(savepath,filename) {
            @Override
            public void onSuccess(Response<File> response) {
                if(show_notification)
                    notifacation.cancelNotify();
                if(listener != null)
                    listener.downloadSuccess(response.body());
            }

            @Override
            public void onStart(Request<File, ? extends Request> request) {
                super.onStart(request);
                if(listener != null)
                    listener.downloadStart();
            }

            @Override
            public void onError(Response<File> response) {
                super.onError(response);
                if(listener != null)
                    listener.downloadError(response.message());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if(listener != null)
                    listener.downloadFinish();
            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                String progress1 = formatProgress(progress.fraction * 100);
                int progress2 = (int)(progress.fraction * 100.0f);
                if(show_notification)
                    notifacation.showProgressNotify(progress2,"已下载：" + progress1 +"%" + "，当前网速：" + formatSpeed(progress.speed));
                if(listener != null) {
                    listener.downloadProgressBar(progress1,progress2,formatSpeed(progress.speed));
                }
            }
        });
    }


    public void download(Context context, String downurl, String savepath, String filename, final boolean show_notification){
        download(context,downurl,savepath,filename,true,R.drawable.ic_launcher,1899,"正在加载中","文件下载器","文件正在准备下载...",new Intent());
    }

    public String getDefaultPath(){
        return ProjectUtils.getInstance().setFileType(ProjectUtils.COMMON_TYPE).setIdCard(true).getWorkGroup("downloader");
    }

    public String getDefaultFilename(String url){
        return url.substring(url.lastIndexOf("/"));
    }
}
