package com.hengyi.baseandroidcore.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.hengyi.baseandroidcore.dialog.CustomLoadingDialog;
import com.hengyi.baseandroidcore.utils.ActivityStack;

/**
 * Created by 繁华 on 2017/5/14.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private CustomLoadingDialog loading = null;
    public Context context;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setBaseContentView());//设置布局文件
        ActivityStack.getInstance().pushActivity(this);//将界面加入堆栈
        context = this;//复制上下文
        //ButterKnife.bind(this);//注入控件
    }

    public abstract int setBaseContentView();

    /**
     * 显示正在加载的提示
     * @param message
     */
    public void showLoadingDialog(String message){
        if(loading == null){
            loading = new CustomLoadingDialog(context,message);
        }else{
            loading.SetMessage(message);
        }
        loading.show();
    }

    /**
     * 关闭正在显示的提示
     */
    public void closeLoadingDialog(){
        if(loading != null){
            loading.cancel();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode){
            ActivityStack.getInstance().popActivity(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    //显示toast
    public void toast(String toast_text) {
        Toast.makeText(context, toast_text, Toast.LENGTH_LONG).show();
    }

    //杀死进程
    public void kill(){
        Process.killProcess(Process.myPid());
    }

    //界面跳转 不带参数
    public void StartActivity(Class cla) {
        intent = null;
        intent = new Intent(context, cla);
        startActivity(intent);
    }

    //带自定义参数
    public void StartActivity(Class cla, String[] names, Object... param) {
        intent = null;
        intent = new Intent(context, cla);
        for (int i = 0; i < param.length; i++) {
            if (param[i].getClass().equals(Integer.class)) {
                intent.putExtra(names[i], (Integer) param[i]);
            } else if (param[i].getClass().equals(String.class)) {
                intent.putExtra(names[i], (String) param[i]);
            } else if (param[i].getClass().equals(Boolean.class)) {
                intent.putExtra(names[i], (Boolean) param[i]);
            } else if (param[i].getClass().equals(Float.class)) {
                intent.putExtra(names[i], (Float) param[i]);
            } else if (param[i].getClass().equals(Double.class)) {
                intent.putExtra(names[i], (Double) param[i]);
            }
        }
        startActivity(intent);
    }

    //带自定义参数  带回调
    public void StartActivity(Class cla, int result, String[] names, Object... param) {
        intent = null;
        intent = new Intent(context, cla);
        for (int i = 0; i < param.length; i++) {
            if (param[i].getClass().equals(Integer.class)) {
                intent.putExtra(names[i], (Integer) param[i]);
            } else if (param[i].getClass().equals(String.class)) {
                intent.putExtra(names[i], (String) param[i]);
            } else if (param[i].getClass().equals(Boolean.class)) {
                intent.putExtra(names[i], (Boolean) param[i]);
            } else if (param[i].getClass().equals(Float.class)) {
                intent.putExtra(names[i], (Float) param[i]);
            } else if (param[i].getClass().equals(Double.class)) {
                intent.putExtra(names[i], (Double) param[i]);
            }
        }
        startActivityForResult(intent, result);
    }

    //启动服务
    public void StartService(Class cla){
        intent = null;
        intent = new Intent(context,cla);
        startService(intent);
    }

    public void StartService(Class cla,String[] names, Object... param){
        intent = null;
        intent = new Intent(context,cla);
        for (int i = 0; i < param.length; i++) {
            if (param[i].getClass().equals(Integer.class)) {
                intent.putExtra(names[i], (Integer) param[i]);
            } else if (param[i].getClass().equals(String.class)) {
                intent.putExtra(names[i], (String) param[i]);
            } else if (param[i].getClass().equals(Boolean.class)) {
                intent.putExtra(names[i], (Boolean) param[i]);
            } else if (param[i].getClass().equals(Float.class)) {
                intent.putExtra(names[i], (Float) param[i]);
            } else if (param[i].getClass().equals(Double.class)) {
                intent.putExtra(names[i], (Double) param[i]);
            }
        }
        startService(intent);
    }

    public void CloseService(Class cla){
        intent = null;
        intent = new Intent(context,cla);
        stopService(intent);
    }

    //发送广播
    public void SendBroadCast(Class cla,String[] names, Object... param){
        intent  = null;
        intent = new Intent(context,cla);
        for (int i = 0; i < param.length; i++) {
            if (param[i].getClass().equals(Integer.class)) {
                intent.putExtra(names[i], (Integer) param[i]);
            } else if (param[i].getClass().equals(String.class)) {
                intent.putExtra(names[i], (String) param[i]);
            } else if (param[i].getClass().equals(Boolean.class)) {
                intent.putExtra(names[i], (Boolean) param[i]);
            } else if (param[i].getClass().equals(Float.class)) {
                intent.putExtra(names[i], (Float) param[i]);
            } else if (param[i].getClass().equals(Double.class)) {
                intent.putExtra(names[i], (Double) param[i]);
            }
        }
        sendBroadcast(intent);
    }


    public void SendBroadCast(Class cla) {
        intent = null;
        intent = new Intent(context, cla);
        sendBroadcast(intent);
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
