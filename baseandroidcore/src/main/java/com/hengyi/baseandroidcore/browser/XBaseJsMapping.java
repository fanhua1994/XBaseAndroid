package com.hengyi.baseandroidcore.browser;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.hengyi.baseandroidcore.dialog.CustomWeiboDialogUtils;
import com.hengyi.baseandroidcore.event.EventMessage;
import com.hengyi.baseandroidcore.event.EventManager;
import com.hengyi.baseandroidcore.listener.FileDownloadListener;
import com.hengyi.baseandroidcore.tools.FileDownloader;
import com.hengyi.baseandroidcore.utils.ActivityRouter;
import com.hengyi.baseandroidcore.utils.ActivityStack;
import com.hengyi.baseandroidcore.utils.ConfigUtils;
import com.hengyi.baseandroidcore.utils.DiskLruCacheHelper;
import com.hengyi.baseandroidcore.utils.GsonUtils;
import com.hengyi.baseandroidcore.utils.NetworkUtils;
import com.hengyi.baseandroidcore.utils.NotificationUtils;
import com.hengyi.baseandroidcore.xutils.AppUtils;
import com.hengyi.baseandroidcore.xutils.FileUtils;
import com.hengyi.baseandroidcore.xutils.RegexUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.util.Map;

/**
 * Created: 2018/3/22 16:31
 * Author:fanhua
 * Email:90fanhua@gmail.com
 * Project:XBaseAndroid
 */

public class XBaseJsMapping extends Object implements IBaseJsMapping {
    private Dialog loadingDialog = null;
    private Activity context;
    private WebView webView;
    private String packageName;

    public XBaseJsMapping(Activity context, WebView webView, String packageName){
        this.context = context;
        this.webView = webView;
        this.packageName = packageName;
    }

    /**
     * 执行JS回调方法
     * webView.loadUrl("javascript:httpCallback(true,'"+ tag +"',"+ notifyId +",'"+ response.body() +"')");
     * @param methodName
     * @param params
     */
    private void executeJsFunction(String methodName,Object... params){
        StringBuilder sb = new StringBuilder();
        sb.append("javascript:");
        sb.append(methodName);
        sb.append("(");
        for(int i = 0;i<params.length;i++){
            if(params[i] instanceof String){
                sb.append("'");
                sb.append((String)params[i]);
                sb.append("'");
            }
            if(params[i] instanceof Boolean){
                sb.append((Boolean)params[i]);
            }
            if(params[i] instanceof Integer){
                sb.append((Integer)params[i]);
            }
            if(i != params.length - 1)
                sb.append(",");
        }
        sb.append(")");
        webView.loadUrl(sb.toString());
    }

    /**
     * 打开一个Activity
     * @param activityName
     * @param params
     */
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
                        if(value == null || value.length != 2)
                            continue;
                        if(RegexUtils.isDigit(value[1])){
                            router.add(value[0],Integer.parseInt(value[1]));
                        }else{
                            router.add(value[0],value[1]);
                        }

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
    public boolean getBooleanConfig(String key){
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
    public void setBooleanConfig(String key,boolean value){
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
                    executeJsFunction("httpCallback",true,tag,notifyId,response.body());
                }
            });
        } catch (Exception e) {
            executeJsFunction("httpCallback",false,tag,notifyId,e.getMessage());
        }
    }

    /**
     * 发起post请求
     */
    @JavascriptInterface
    public void doPost(String url, String params, final String tag, final int notifyId) {
        try{
            Map<String, Object> map = GsonUtils.parseJsonWithGson(params, Map.class);
            final PostRequest request = OkGo.<String>post(url).tag(tag);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                request.params(entry.getKey(), entry.getValue().toString());
            }
            request.execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    executeJsFunction("httpCallback",true,tag,notifyId,response.body());
                }
            });
        }catch (Exception e){
            executeJsFunction("httpCallback",false,tag,notifyId,e.getMessage());
        }
    }

    /**
     * 发起文件下载
     * @param fileUrl   下载文件的地址
     * @param saveFilename  保存的文件名
     * @param showNotification  是否显示通知
     * @param isCallback    是否回调到js
     */
    @JavascriptInterface
    public void download(String fileUrl, String saveFilename, final String authorities, boolean showNotification, boolean isCallback, final int notifyId){
        final FileDownloader downloader = new FileDownloader();
        if(saveFilename == null || saveFilename.length()  == 0)
            saveFilename = downloader.getDefaultFilename(fileUrl);
        downloader.download(context,fileUrl,downloader.getDefaultPath(),saveFilename,showNotification);
        if(isCallback ){
            downloader.setDownloadListener(new FileDownloadListener() {
                @Override
                public void downloadProgressBar(String progress, int progress2, String speed) {
                    executeJsFunction("downloadProgressBar",progress,progress2,speed,notifyId);
                }

                @Override
                public void downloadSuccess(File filePath) {
                    if(authorities != null && authorities.length() > 0) {
                        if (FileUtils.getFileExtension(filePath).equals("apk")) {
                            AppUtils.installApp(filePath, "authorities");
                        }
                    }
                    executeJsFunction("downloadSuccess",filePath.getAbsolutePath(),notifyId);
                }

                @Override
                public void downloadStart() {
                    executeJsFunction("downloadStart",notifyId);
                }

                @Override
                public void downloadError(String message) {
                    executeJsFunction("downloadError",message,notifyId);
                }

                @Override
                public void downloadFinish() {
                    executeJsFunction("downloadFinish",downloader.getDefaultPath(),notifyId);
                }

                @Override
                public void cancelDownload() {

                }

                @Override
                public void NoUpdate() {
                    //此方法不使用。
                }
            });
        }
    }

    /**
     * 发起通知
     */
    @JavascriptInterface
    public void showNotification(String iconId,String resourceDir,String activityName,String tickerText,String title,String content,int notifyId){
        try {
            NotificationUtils notifacation = new NotificationUtils(context);//实例化通知栏
            Intent intent = null;
            if(activityName != null && !activityName.equals("null") && activityName.length() > 0) {
                Class clazz = Class.forName(activityName);
                intent = new Intent(context, clazz);
            }
            int icon = context.getResources().getIdentifier(iconId,resourceDir,packageName);
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

    /**
     * 获取Intent参数值
     * @param name
     * @param defaultValue
     * @return
     */
    @JavascriptInterface
    public boolean getBooleanExtra(String name,boolean defaultValue){
        return context.getIntent().getBooleanExtra(name,defaultValue);
    }

    /**
     * 获取Intent参数值
     * @param name
     * @param defaultValue
     * @return
     */
    @JavascriptInterface
    public int getIntExtra(String name,int defaultValue){
        return context.getIntent().getIntExtra(name,defaultValue);
    }

    /**
     * 获取Intent参数值
     * @param name
     * @return
     */
    @JavascriptInterface
    public String getStringExtra(String name){
        return context.getIntent().getStringExtra(name);
    }
}
