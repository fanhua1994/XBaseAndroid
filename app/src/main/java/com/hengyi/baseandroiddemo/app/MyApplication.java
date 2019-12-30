package com.hengyi.baseandroiddemo.app;

import com.hengyi.baseandroidcore.base.XBaseApplication;
import com.hengyi.baseandroidcore.database.DatabaseHelper;
import com.hengyi.baseandroidcore.listener.DatabaseVersionChangeListener;
import com.hengyi.baseandroidcore.xutils.LogUtils;
import com.hengyi.baseandroidcore.xutils.ToastUtils;
import com.hengyi.baseandroiddemo.database.User;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends XBaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        databaseHelper.addTable(User.class);

        ToastUtils.showLong("数据库加载成功，数据库大小：" + databaseHelper.getDatabaseSize());

        databaseHelper.setVersionChangeListener(new DatabaseVersionChangeListener() {
            @Override
            public void onChange(int oldVersion, int newVersion) {
                ToastUtils.showLong("数据库版本发生变化:" + oldVersion + " > " + newVersion);

                /**
                 * 这里的i即当前版本。比如你想从1升级到2   2版本会新增一个字段。那么你就在case 1：里面写新增字段的逻辑代码
                 */
                for (int i = oldVersion; i < newVersion; i++) {
                    switch (i) {
                        case 1:
                            //新增了sex字段
                            databaseHelper.addColumn(User.class,"sex","varchar","未知");
                            LogUtils.d("MyApplication","字段添加成功");
                            break;

                        case 2:
                            //删除了字段sex

                            //吧需要保留的字段放到List中  不是实体类的字段名
                            List<String> list = new ArrayList<>();
                            list.add("user_id");
                            list.add("name");
                            databaseHelper.delColumn(User.class,list);
                            LogUtils.d("MyApplication","字段删除成功");
                            break;
                    }
                }
            }
        });

    }
}
