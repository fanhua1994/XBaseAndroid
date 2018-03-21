package com.hengyi.baseandroidcore.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by Fanhua on 2018/3/14.
 */

public class AppConfig {
    private static final String CONFIG_NAME = "application.properties";
    private static AppConfig instance = null;
    private static Properties properties = null;


    public static synchronized AppConfig getInstance(){
        synchronized (AppConfig.class){
            if( instance == null) {
                properties = new Properties();
                instance = new AppConfig();
            }
            return instance;
        }
    }

    public AppConfig load(Context context){
        return load(context,CONFIG_NAME);
    }

    public AppConfig load(Context context,String config_name){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(config_name)));
            properties.load(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public AppConfig load(String config_path){
        try {
            properties.load(new FileInputStream(config_path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }

    public Boolean getBooleanValue(String key) {
        Boolean isok = false;
        try {
            isok = Boolean.parseBoolean(getValue(key));
        } catch (Exception e) {
            isok =false;
        }
        return isok;
    }

    public Integer getIntegerValue(String key) {
        return Integer.parseInt(getValue(key));
    }

    public Double getDoubleValue(String key) {
        return Double.parseDouble(getValue(key));
    }

    public Float getFloatValue(String key) {
        return Float.parseFloat(getValue(key));
    }

    public Long getLongValue(String key) {
        return Long.parseLong(getValue(key));
    }

    public String[] getArrayValue(String key) {
        return getValue(key).split(",");
    }

    public String[] getArrayValue(String key,String separator){
        return getValue(key).split(separator);
    }
}
