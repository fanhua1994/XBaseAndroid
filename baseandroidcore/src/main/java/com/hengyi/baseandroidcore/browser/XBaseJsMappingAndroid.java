package com.hengyi.baseandroidcore.browser;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.io.Serializable;

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
    public void test(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

}
