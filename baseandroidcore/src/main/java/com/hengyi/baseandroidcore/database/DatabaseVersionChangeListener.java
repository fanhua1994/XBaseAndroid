package com.hengyi.baseandroidcore.database;

/**
 * Created by Administrator on 2017/10/26.
 */

public interface DatabaseVersionChangeListener {
    public void onChange(int oldVersion,int newVersion);
}
