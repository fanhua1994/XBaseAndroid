package com.hengyi.baseandroiddemo.app;

import com.hengyi.baseandroidcore.base.XBaseApplication;
import com.hengyi.baseandroidcore.database.DatabaseHelper;
import com.hengyi.baseandroidcore.listener.DatabaseVersionChangeListener;
import com.hengyi.baseandroidcore.xutils.ToastUtils;
import com.hengyi.baseandroiddemo.database.User;

public class MyApplication extends XBaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        databaseHelper.addTable(User.class);

        databaseHelper.setVersionChangeListener(new DatabaseVersionChangeListener() {
            @Override
            public void onChange(int oldVersion, int newVersion) {
                ToastUtils.showLong("数据库版本发生变化:" + oldVersion + " > " + newVersion);
            }
        });

    }
}
