package com.hengyi.baseandroidcore.browser;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.hengyi.baseandroidcore.event.DefaultMessageEvent;
import com.hengyi.baseandroidcore.event.EventManager;
import com.hengyi.baseandroidcore.utils.ConfigUtils;

/**
 * Created: 2018/3/22 16:31
 * Author:fanhua
 * Email:90fanhua@gmail.com
 * Project:XBaseAndroid
 */

public class XBaseJsMappingAndroid extends Object {
    private Context context;

    public XBaseJsMappingAndroid(Context context){
        this.context = context;
    }

    @JavascriptInterface
    public void toast(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void event(int code,String content){
        DefaultMessageEvent event = EventManager.getDefaultMessage();
        event.setCode(code);
        event.setContent(content);
        EventManager.sendDefaultMessage(event);
    }

    @JavascriptInterface
    public int getIntConfig(String key){
        return ConfigUtils.getInstance(context).findIntByKey(key);
    }

    @JavascriptInterface
    public String getStringConfig(String key){
        return ConfigUtils.getInstance(context).findStringByKey(key);
    }
}
