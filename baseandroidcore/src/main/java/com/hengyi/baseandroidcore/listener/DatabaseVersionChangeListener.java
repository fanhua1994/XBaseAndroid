package com.hengyi.baseandroidcore.listener;

/**
 * Created by Administrator on 2017/10/26.
 */

public interface DatabaseVersionChangeListener {
    void onChange(int oldVersion,int newVersion);
}
