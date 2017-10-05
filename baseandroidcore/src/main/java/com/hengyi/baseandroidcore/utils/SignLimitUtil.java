package com.hengyi.baseandroidcore.utils;

/**
 * Created by Administrator on 2017/9/15.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 为了防止家长在短时间内重复刷卡。特意添加该类控制
 */
public class SignLimitUtil {
    private static SignLimitUtil instance = null;
    private static Map<String,Integer> list = null;
    private static final int time_out = 120;//秒

    private int time = 0;

    public static SignLimitUtil getInstance(){
        if(instance == null){
            instance = new SignLimitUtil();
            list = new HashMap<String,Integer>();
        }

        return instance;
    }

    private int gettimestamp(){
        return (int)(System.currentTimeMillis() / 1000);
    }

    public void add(String number){
        list.put(number,gettimestamp());
    }

    /**
     * 是否可以正常刷卡
     * @param number
     * @return
     */
    public boolean check(String number){
        if(!list.containsKey(number)){
            return true;
        }else{
            time = gettimestamp() - list.get(number);
            if(time < time_out){
                return false;
            }else{
                return true;
            }
        }
    }
}
