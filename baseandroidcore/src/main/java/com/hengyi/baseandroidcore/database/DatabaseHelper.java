package com.hengyi.baseandroidcore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by Administrator on 2017/10/10.
*/
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "sc.db";
    //数据库名
    private static final int DB_VERSION = 1;
    //数据库版本
    private static DatabaseHelper instance;

    //Helper单例
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表
        try {
//            TableUtils.createTable(connectionSource, Student.class);
//            TableUtils.createTable(connectionSource, Course.class);
//            TableUtils.createTable(connectionSource, StudentCourse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //更新表
        try {
//            TableUtils.dropTable(connectionSource, StudentCourse.class, true);
//            TableUtils.dropTable(connectionSource, Student.class, true);
//            TableUtils.dropTable(connectionSource, Course.class, true);
            onCreate(database, connectionSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 双重加锁检查
     *
     * @param context 上下文
     * @return 单例
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }

}
