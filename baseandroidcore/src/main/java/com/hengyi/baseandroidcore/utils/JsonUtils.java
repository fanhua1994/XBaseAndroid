package com.hengyi.baseandroidcore.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtils {
    static {
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }
	
	//转为字符串
	public static String toString(Object obj) {
		return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
	}
	
	// 转换为JsonObejct
	public static JSONObject toJsonObject(String jsonText){
		return JSON.parseObject(jsonText);
	}
	
	// 转换为对象
	public static <T> T toBean(String text, Class<T> clazz) {  
        return JSON.parseObject(text, clazz);  
    }  
	
	 // 转换为数组  
    public static <T> Object[] toArray(String text) {  
        return toArray(text, null);  
    }  
  
    // 转换为数组  
    public static <T> Object[] toArray(String text, Class<T> clazz) {  
        return JSON.parseArray(text, clazz).toArray();  
    }  
  
    // 转换为List  
    public static <T> List<T> toList(String text, Class<T> clazz) {  
        return JSON.parseArray(text, clazz);  
    }  
  

}
