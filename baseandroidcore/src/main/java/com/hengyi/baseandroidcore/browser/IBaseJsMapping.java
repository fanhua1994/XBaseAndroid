package com.hengyi.baseandroidcore.browser;

/**
 * Created：2018/8/11
 * Time：0:16
 * Author：dongzp
 * Email：90fanhua@gmail.com
 * Project：XBaseAndroid
 * Use：
 */
public interface IBaseJsMapping {
    void openActivity(String activityName,String params);
    void showLoading(String msg);
    void closeLoading();
    void toast(String msg);
    void sendEvent(int code,String content);
    int getIntConfig(String key);
    String getStringConfig(String key);
    boolean getBooleanConfig(String key);
    void setIntConfig(String key,int value);
    void setStringConfig(String key,String value);
    void setBooleanConfig(String key,boolean value);
    void setCache(String key,String value);
    String getCache(String key);
    //关闭当前界面
    void close();
    //杀死APP
    void kill();
    void doGet(String url, final String tag,final int notifyId);
    void doPost(String url, String params, final String tag, final int notifyId);
    void download(String fileUrl, String saveFilename, final String authorities, boolean showNotification, boolean isCallback, final int notifyId);
    void showNotification(String iconId,String resourceDir,String activityName,String tickerText,String title,String content,int notifyId);
    boolean getNetworkStatus();
    boolean getBooleanExtra(String name,boolean defaultValue);
    int getIntExtra(String name,int defaultValue);
    String getStringExtra(String name);
}
