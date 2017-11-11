package com.hengyi.baseandroidcore.app;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.hengyi.baseandroidcore.R;
import com.hengyi.baseandroidcore.base.XbaseApplication;
import com.hengyi.baseandroidcore.dialog.CustomAlertDialog;
import com.hengyi.baseandroidcore.utils.ProjectUtils;
import com.hengyi.baseandroidcore.utils.VersionUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;

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
    private AppUpdateListener listener = null;

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
     * 下载APK
     */
    private void downloadApk(Context context,UpdateBean updateBean){
        String download_path = ProjectUtils.getInstance().setIdCard(true).setFileType(ProjectUtils.COMMON_TYPE).getWorkGroup("download");
        String download_name = context.getString(R.string.framework_name) + "_" + updateBean.getNew_version() +".apk";

        OkGo.<File>get(updateBean.getDownload_url()).tag(this).execute(new FileCallback(download_path,download_name) {
            @Override
            public void onSuccess(Response<File> response) {
                if(listener != null)
                    listener.downloadSuccess();
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
                if(listener != null)
                    listener.downloadProgressBar(progress.fraction,progress.speed + "");
            }
        });
    }

    public void checkUpdate(final UpdateBean updateBean, final Context context){
         boolean haveUpdate = !VersionUtils.getAppVersion(XbaseApplication.getApplication(),"1.0.0.0").equals(updateBean.getNew_version());
        if(haveUpdate){
            StringBuffer desc = new StringBuffer();
            desc.append(context.getString(R.string.app_update_manager_veriosn_str));
            desc.append(updateBean.getNew_version());
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

            updateDialog.setNegativeButton(context.getString(R.string.app_update_manager_dialog_cancel),new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(updateBean.isForce()){
                        Toast.makeText(context,context.getString(R.string.app_update_manager_isforce_update),Toast.LENGTH_LONG).show();
                        return ;
                    }
                    if(listener != null)
                        listener.cancelDownload();
                }
            });

            updateDialog.show();

        }else{
            if(listener != null)
                listener.NoUpdate();
        }
    }

    public void setAppUpdateListener(AppUpdateListener listener){
        this.listener = listener;
    }


    public interface AppUpdateListener{
        public void downloadProgressBar(float progress,String speed);
        public void downloadSuccess();
        public void downloadStart();
        public void downloadError(String message);
        public void downloadFinish();
        public void cancelDownload();
        public void NoUpdate();
    }

}
