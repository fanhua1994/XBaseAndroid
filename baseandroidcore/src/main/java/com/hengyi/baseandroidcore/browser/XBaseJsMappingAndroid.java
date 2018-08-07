package com.hengyi.baseandroidcore.browser;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.hengyi.baseandroidcore.dialog.CustomWeiboDialogUtils;
import com.hengyi.baseandroidcore.event.EventMessage;
import com.hengyi.baseandroidcore.event.EventManager;
import com.hengyi.baseandroidcore.utils.ActivityRouter;
import com.hengyi.baseandroidcore.utils.ActivityStack;
import com.hengyi.baseandroidcore.utils.ConfigUtils;
import com.hengyi.baseandroidcore.utils.DiskLruCacheHelper;
import com.hengyi.baseandroidcore.utils.GsonUtils;
import com.hengyi.baseandroidcore.utils.NetworkUtils;
import com.hengyi.baseandroidcore.utils.NotificationUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import java.util.Map;


/**
 * Created: 2018/3/22 16:31
 * Author:fanhua
 * Email:90fanhua@gmail.com
 * Project:XBaseAndroid
 */

public class XBaseJsMappingAndroid extends Object {
    private Dialog loadingDialog = null;
    private Context context;
    private WebView webView;
    private String packageName;

    public XBaseJsMappingAndroid(Context context, WebView webView,String packageName){
        this.context = context;
        this.webView = webView;
        this.packageName = packageName;
    }

   @JavascriptInterface
   public void openActivity(String activityName,String params){
        try{
            if(activityName != null && !activityName.equals("null") && activityName.length() > 0) {
                Class clazz = Class.forName(activityName);
                ActivityRouter router = ActivityRouter.getInstance();
                if(params != null && params.length() > 0){
                    String[] p = params.split("&");
                    String[] value = null;
                    for(String param : p){
                        value = param.split("=");
                        router.add(value[0],value[1]);
                    }
                }
                router.startActivity(context,clazz);
            }
        }catch (Exception e){
            toast(e.getMessage());
        }

   }

    /**
     * 显示Loading
     * @param msg
     */
    @JavascriptInterface
    public void showLoading(String msg){
        loadingDialog = CustomWeiboDialogUtils.createLoadingDialog(context,msg);
    }

    /**
     * 关闭Loading
     */
    @JavascriptInterface
    public void closeLoading(){
        CustomWeiboDialogUtils.closeDialog(loadingDialog);
    }

    /**
     * 显示toast
     * @param msg
     */
    @JavascriptInterface
    public void toast(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    /**
     * 发送事件
     * @param code
     * @param content
     */
    @JavascriptInterface
    public void sendEvent(int code,String content){
        EventMessage event = EventManager.getMessage();
        event.setCode(code);
        event.setContent(content);
        EventManager.sendMessage(event);
    }

    /**
     * 获取int配置
     * @param key
     * @return
     */
    @JavascriptInterface
    public int getIntConfig(String key){
        return ConfigUtils.getInstance(context).findIntByKey(key);
    }

    /**
     * 获取配置
     * @param key
     * @return
     */
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

    /**
     * 设置缓存
     * @param key
     * @param value
     */
    @JavascriptInterface
    public void setCache(String key,String value){
        DiskLruCacheHelper.getInstance(context).put(key,value);
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    @JavascriptInterface
    public String getCache(String key){
        return DiskLruCacheHelper.getInstance(context).getAsString(key);
    }

    /**
     * 关闭当前界面
     */
    @JavascriptInterface
    public void close(){
        ActivityStack.getInstance().popActivity();
    }

    /**
     * 关闭整个应用
     */
    @JavascriptInterface
    public void kill(){
        ActivityStack.getInstance().clearAllActivity();
    }

    /**
     * 发起get请求
     */
    @JavascriptInterface
    public void doGet(String url, final String tag,final int notifyId){
        try {
            OkGo.<String>get(url).tag(tag).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    webView.loadUrl("javascript:httpCallback(true,'"+ tag +"',"+ notifyId +",'"+ response.body() +"')");
                }
            });
        } catch (Exception e) {
            webView.loadUrl("javascript:httpCallback(false,'"+ tag +"',"+ notifyId +",'"+ e.getMessage() +"')");
        }
    }

    /**
     * 发起post请求
     */
    @JavascriptInterface
    public void doPost(String url, String params, final String tag, final int notifyId) {
        try{
            Map<String, Object> map = GsonUtils.parseJsonWithGson(params, Map.class);
            PostRequest request = OkGo.<String>post(url).tag(tag);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                request.params(entry.getKey(), entry.getValue().toString());
            }
            request.execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    webView.loadUrl("javascript:httpCallback(true,'"+ tag +"',"+ notifyId +",'"+ response.body() +"')");
                }
            });
        }catch (Exception e){
            webView.loadUrl("javascript:httpCallback(false,'"+ tag +"',"+ notifyId +",'"+ e.getMessage() +"')");
        }
    }

    /**
     * 发起文件下载
     */
    @JavascriptInterface
    public void download(){

    }

    /**
     * 发起通知
     */
    @JavascriptInterface
    public void showNotification(String imageId,String activityName,String tickerText,String title,String content,int notifyId){
        try {
            NotificationUtils notifacation = new NotificationUtils(context);//实例化通知栏
            Intent intent = null;
            if(activityName != null && !activityName.equals("null") && activityName.length() > 0) {
                Class clazz = Class.forName(activityName);
                intent = new Intent(context, clazz);
            }
            int icon = context.getResources().getIdentifier(imageId,"drawable",packageName);
            notifacation.createNotify(icon, tickerText, title, content, intent, notifyId);
        }catch (Exception e){
            e.printStackTrace();
            toast(e.getMessage());
        }
    }

    /**
     * 判断当前网络状态
     */
    @JavascriptInterface
    public boolean getNetworkStatus(){
        return NetworkUtils.isNetworkConnected(context);
    }

}
