package com.hengyi.baseandroidcore.request;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;

/**
 * Created：2018/7/10
 * Time：16:03
 * Author：dongzp
 * Email：90fanhua@gmail.com
 * Project：XBaseAndroid
 * Use：网络请求
 */
public class Request {
    private static Request instance = null;

    /**
     * 单列模式
     * @return
     */
    public static synchronized Request getInstance(){
        synchronized (Request.class){
            if(instance == null){
                instance = new Request();
            }
            return  instance;
        }
    }
}
