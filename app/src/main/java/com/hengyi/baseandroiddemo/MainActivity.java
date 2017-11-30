package com.hengyi.baseandroiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hengyi.baseandroidcore.app.AppUpdateManager;
import com.hengyi.baseandroidcore.app.UpdateBean;
import com.hengyi.baseandroidcore.base.XbaseWebActivity;
import com.hengyi.baseandroidcore.event.EventManager;
import com.hengyi.baseandroidcore.utils.NotifacationUtils;
import com.hengyi.baseandroidcore.utilscode.PermissionUtils;
import com.hengyi.baseandroidcore.weight.EaseTitleBar;

import com.hengyi.baseandroidcore.event.DefaultMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.cache_admin)Button cache;
    @BindView(R.id.titleBar)EaseTitleBar easeTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
     }

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.cache_admin,R.id.web,R.id.bluetooth,R.id.requestPermission,R.id.checkUpdate,R.id.post})
    public void Click(View view){
        switch(view.getId()){
            case R.id.post:
                DefaultMessageEvent defaultMessageEvent = EventManager.getDefaultMessage();
                defaultMessageEvent.setContent("我是EventBus");
                EventManager.sendDefaultMessage(defaultMessageEvent);
                break;
            case R.id.cache_admin:
                StartActivity(CacheActivity.class);
                break;

            case R.id.web:
                StartActivity(XbaseWebActivity.class,new String[]{XbaseWebActivity.WEB_URL_PARAM, XbaseWebActivity.WEB_SHOW_TITLE_BAR},"file:///android_asset/index.html",false);
                break;

            case R.id.bluetooth:
                StartActivity(BluetoothActivity.class);
                break;

            case R.id.requestPermission:
                toast("request permission now!");
                PermissionUtils.requestPermissions(this,200,new String[]{"android.permission.READ_CONTACTS"},new PermissionUtils.OnPermissionListener(){
                    @Override
                    public void onPermissionGranted() {
                        toast("权限申请成功");
                    }

                    @Override
                    public void onPermissionDenied(String[] deniedPermissions) {
                        toast("权限申请失败");
                    }
                });
                break;

            case R.id.checkUpdate:
                final NotifacationUtils notifacation = new NotifacationUtils(this);
                UpdateBean updateBean = new UpdateBean();
                updateBean.setDescription("今日更新了XBaseAndroid框架的更新管理器。");
                updateBean.setDownload_url("http://file.cleveriip.com:88/group2/M00/00/03/rBJbXVnlcPCATMAtAtnNwW8wwRs625.apk");
                updateBean.setForce(false);//是否强制更新 或 静默安装
                updateBean.setAuthority("com.hengyi.xbaseandroid.fileProvider");//兼容安卓7.0 安装
                updateBean.setMd5_code(null);
                updateBean.setNew_version("1.0.0.1");

                updateBean.setTitle("新版本来啦，立即更新吧");
                AppUpdateManager appUpdateManager = AppUpdateManager.getInstance();
                appUpdateManager.checkUpdate(updateBean,this);
                appUpdateManager.setAppUpdateListener(new AppUpdateManager.AppUpdateListener() {

                    @Override
                    public void downloadProgressBar(String progress, int progress2,String speed) {
                        notifacation.showProgressNotify(progress2,"当前下载网速" + speed);
                        Log.d("AppUpdateManager","进度条：" + progress + "  p2:"+ progress2 +"   下载速度："  + speed);
                    }

                    @Override
                    public void downloadSuccess(File app_path) {
                        Log.d("AppUpdateManager","下载成功    路径如下：" + app_path.getAbsolutePath());
                    }

                    @Override
                    public void downloadStart() {
                        Log.d("AppUpdateManager","下载开始");
                        notifacation.createProgressNotify(R.drawable.ic_launcher,200,"正在下载中","APP更新","App正在准备下载",new Intent());
                    }

                    @Override
                    public void downloadError(String message) {
                        Log.d("AppUpdateManager","下载错误");
                    }

                    @Override
                    public void downloadFinish() {
                        Log.d("AppUpdateManager","下载结束");
                    }

                    @Override
                    public void cancelDownload() {
                        Log.d("AppUpdateManager","取消下载");
                    }

                    @Override
                    public void NoUpdate() {
                        Log.d("AppUpdateManager","没有更新");
                    }
                });
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DefaultMessageEvent event) {
        toast("收到回调:" + event.getContent());
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
