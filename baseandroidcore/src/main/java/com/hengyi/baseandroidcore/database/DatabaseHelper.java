package com.hengyi.baseandroidcore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
*/
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static String DB_NAME = "base.db";
    //数据库名
    private static int DB_VERSION = 1;
    //数据库版本
    private static DatabaseHelper instance;
    private static List<Class> tables;

    //Helper单例
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static void setDatabase(String db_name,int db_version){
        DB_NAME = db_name + ".db";
        DB_VERSION = db_version;
    }

    public static void setTables(List<Class> m_tables){
        tables = m_tables;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表
        try {
            for(int i = 0;i<tables.size();i++){
                TableUtils.createTable(connectionSource,tables.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //更新表
        try {
            for(int i = 0;i<tables.size();i++){
                TableUtils.dropTable(connectionSource,tables.get(i),true);
            }
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
