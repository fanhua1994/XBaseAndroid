package com.hengyi.baseandroidcore.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.hengyi.baseandroidcore.dialog.WeiboDialogUtils;
import com.hengyi.baseandroidcore.utils.ActivityStack;
import com.hengyi.baseandroidcore.utils.ActivityUtils;
import com.hengyi.baseandroidcore.utils.NetworkUtils;

/**
 * Created by 繁华 on 2017/5/14.
 */

public abstract class XBaseActivity extends AppCompatActivity {
    private Dialog loadingDialog = null;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setBaseContentView());//设置布局文件
        ActivityStack.getInstance().pushActivity(this);//将界面加入堆栈
        context = this;//复制上下文
    }

    public abstract int setBaseContentView();

    public boolean getNetworkStatus(){
        return NetworkUtils.isNetworkConnected(this);
    }

    public Context getContext(){
        return context;
    }

    public void toast(String mess){
        ActivityUtils.toast(this,mess);
    }

    /**
     * 显示正在加载的提示
     * @param message
     */
    public void showLoadingDialog(String message){
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this,message);
    }

    /**
     * 关闭正在显示的提示
     */
    public void closeLoadingDialog(){
        WeiboDialogUtils.closeDialog(loadingDialog);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode){
            ActivityStack.getInstance().popActivity(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getInstance().popActivity(this);
    }
}
