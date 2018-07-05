package com.hengyi.baseandroidcore.utils;

import com.google.gson.Gson;

/**
 * Created by 繁华 on 2017/5/16.
 */

 /*
  * 封装的GSON解析工具类，提供泛型参数
 */
public class GsonUtils {
    private static Gson gson = null;
    //将Json数据解析成相应的映射对象
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        if(gson == null)
            gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    public String toString(Object o){
        if(gson == null)
            gson = new Gson();
        return gson.toJson(o);
    }
}
