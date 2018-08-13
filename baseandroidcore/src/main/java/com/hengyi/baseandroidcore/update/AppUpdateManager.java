package com.hengyi.baseandroidcore.update;

import android.content.Context;
import android.view.View;

import com.hengyi.baseandroidcore.R;
import com.hengyi.baseandroidcore.base.XBaseApplication;
import com.hengyi.baseandroidcore.dialog.CustomAlertDialog;
import com.hengyi.baseandroidcore.listener.FileDownloadListener;
import com.hengyi.baseandroidcore.utils.ProjectUtils;
import com.hengyi.baseandroidcore.utils.VersionUtils;
import com.hengyi.baseandroidcore.xutils.AppUtils;
import com.hengyi.baseandroidcore.xutils.EncryptUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/10/10.
 * 更新管理，这里不包括热更新。
 * 1.init初始化
 * 2.checkUpdate
 * 3.addDownloadManager
 * 4.autoInstall
 */

public class AppUpdateManager {
    private static AppUpdateManager instance = null;
    private FileDownloadListener listener = null;

    /**
     * 双重枷锁
     * @return
     */
    public static synchronized AppUpdateManager getInstance(){
        synchronized(AppUpdateManager.class){
            if(instance == null){
                instance = new AppUpdateManager();
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

    /**
     * 下载APK
     */
    private void downloadApk(Context context, final UpdateBean updateBean){
        String download_path = ProjectUtils.getInstance().setIdCard(true).setFileType(ProjectUtils.COMMON_TYPE).getWorkGroup("download");
        String download_name = context.getString(R.string.framework_name) + "_" + updateBean.getVersionName() +".apk";

        OkGo.<File>get(updateBean.getDownloadUrl()).tag(this).execute(new FileCallback(download_path,download_name) {
            @Override
            public void onSuccess(Response<File> response) {
                if(listener != null)
                    listener.downloadSuccess(response.body());

                String file_md5 = EncryptUtils.encryptMD5File2String(response.body()).toLowerCase();

                if(updateBean.getMd5Code() != null){
                    if(!updateBean.getMd5Code().equals(file_md5)){
                        return ;
                    }
                }

                if(AppUtils.isAppRoot()){
                    AppUtils.installAppSilent(response.body().getAbsolutePath());
                }else{
                    AppUtils.installApp(response.body(),updateBean.getAuthority());
                }
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
                if(listener != null) {
                    listener.downloadProgressBar(formatProgress(progress.fraction * 100),(int)(progress.fraction * 100.0f),formatSpeed(progress.speed));
                }
            }
        });
    }

    /**
     *
     * @param updateBean            升级实体
     * @param versionType           1 为已版本名为更新判断标识，2为已版本编码w为判断标准
     * @param fine                  是否精细化升级。false 只要是与当前版本不同则更新。true必须大于当前版本
     * @param context
     */
    public void checkUpdate(final UpdateBean updateBean, int versionType,boolean fine,final Context context){
        boolean haveUpdate = false;
         if(versionType == 1){
             if(fine)
                 haveUpdate = VersionUtils.checkVersion(VersionUtils.getVersionName(XBaseApplication.getApplication(),"1.0.0.0"),updateBean.getVersionName()) == 1;
             else
                 haveUpdate = !VersionUtils.getVersionName(XBaseApplication.getApplication(), "1.0.0.0").equals(updateBean.getVersionName());

         }else{
             if(fine)
                 haveUpdate = updateBean.getVersionCode() > VersionUtils.getVersionCode(XBaseApplication.getApplication(),0);
             else
                haveUpdate = updateBean.getVersionCode() != VersionUtils.getVersionCode(XBaseApplication.getApplication(),0);
         }

        if(haveUpdate){
            StringBuffer desc = new StringBuffer();
            desc.append(context.getString(R.string.app_update_manager_veriosn_str));
            desc.append(updateBean.getVersionName());
            desc.append("\n");
            desc.append(context.getString(R.string.app_update_manager_description_str));
            desc.append(updateBean.getDescription());

            CustomAlertDialog updateDialog = new CustomAlertDialog(context).builder();
            updateDialog.setCancelable(!updateBean.isForce());
            updateDialog.setMsg(desc.toString());
            updateDialog.setTitle(updateBean.getTitle());

            updateDialog.setPositiveButton(context.getString(R.string.app_update_manager_dialog_download),new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    downloadApk(context,updateBean);
                }
            });

            if(!updateBean.isForce()) {
                updateDialog.setNegativeButton(context.getString(R.string.app_update_manager_dialog_cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null)
                            listener.cancelDownload();
                    }
                });
            }

            updateDialog.show();

        }else{
            if(listener != null)
                listener.NoUpdate();
        }
    }

    public void setAppUpdateListener(FileDownloadListener listener){
        this.listener = listener;
    }

}
