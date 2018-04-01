package com.hengyi.baseandroidcore.browser;

import android.app.Dialog;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.hengyi.baseandroidcore.dialog.CustomWeiboDialogUtils;
import com.hengyi.baseandroidcore.event.MessageEvent;
import com.hengyi.baseandroidcore.event.EventManager;
import com.hengyi.baseandroidcore.utils.ActivityStack;
import com.hengyi.baseandroidcore.utils.ConfigUtils;
import com.hengyi.baseandroidcore.utils.DiskLruCacheHelper;

/**
 * Created: 2018/3/22 16:31
 * Author:fanhua
 * Email:90fanhua@gmail.com
 * Project:XBaseAndroid
 */

public class XBaseJsMappingAndroid extends Object {
    private Dialog loadingDialog = null;
    private Context context;

    public XBaseJsMappingAndroid(Context context){
        this.context = context;
    }

    @JavascriptInterface
    public void showLoading(String msg){
        loadingDialog = CustomWeiboDialogUtils.createLoadingDialog(context,msg);
    }

    @JavascriptInterface
    public void closeLoading(){
        CustomWeiboDialogUtils.closeDialog(loadingDialog);
    }

    @JavascriptInterface
    public void toast(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void event(int code,String content){
        MessageEvent event = EventManager.getDefaultMessage();
        event.setCode(code);
        event.setContent(content);
        EventManager.sendMessage(event);
    }

    @JavascriptInterface
    public int getIntConfig(String key){
        return ConfigUtils.getInstance(context).findIntByKey(key);
    }

    @JavascriptInterface
    public String getStringConfig(String key){
        return ConfigUtils.getInstance(context).findStringByKey(key);
    }

    @JavascriptInterface
    public Boolean getBooleanConfig(String key){
        return ConfigUtils.getInstance(context).findBoolByKey(key);
    }

    @JavascriptInterface
    public void setIntConfig(String key,int value){
        ConfigUtils.getInstance(context).addOrUpdateIntNumber(key,value);
    }

    @JavascriptInterface
    public void setStringConfig(String key,String value){
        ConfigUtils.getInstance(context).addOrUpdateText(key,value);
    }

    @JavascriptInterface
    public void setBooleanConfig(String key,Boolean value){
        ConfigUtils.getInstance(context).addOrUpdateBoolean(key,value);
    }

    @JavascriptInterface
    public void setCache(String key,String value){
        DiskLruCacheHelper.getInstance(context).put(key,value);
    }

    @JavascriptInterface
    public String getCache(String key){
        return DiskLruCacheHelper.getInstance(context).getAsString(key);
    }

    @JavascriptInterface
    public void close(){
        ActivityStack.getInstance().popActivity();
    }

    @JavascriptInterface
    public void kill(){
        ActivityStack.getInstance().clearAllActivity();
    }
}
