package com.hengyi.baseandroidcore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hengyi.baseandroidcore.listener.DatabaseVersionChangeListener;
import com.hengyi.baseandroidcore.utils.VersionUtils;
import com.hengyi.baseandroidcore.xutils.LogUtils;
import com.hengyi.baseandroidcore.xutils.StringUtils;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fanhua on 2017/10/10.
*/
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static DatabaseHelper instance = null;
    private static List<Class> tables = new ArrayList<>();
    private DatabaseVersionChangeListener listener = null;

    /**
     * 双重加锁检查
     *
     * @param context 上下文
     * @return 单例
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null){
                    instance = new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }

    //Helper单例
    public DatabaseHelper(Context context) {
        super(context,  "xbase.db", null, VersionUtils.getVersionCode(context,1));
    }

    public void addTable(Class table){
        tables.add(table);
    }

    public void setVersionChangeListener(DatabaseVersionChangeListener listener){
        this.listener = listener;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表
        try {
            for(int i = 0;i<tables.size() ;i++){
                TableUtils.createTableIfNotExists(connectionSource,tables.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if(listener != null){
            listener.onChange(oldVersion,newVersion);
        }
    }

    /**
     * 判断表是否存在
     * @param table
     * @return
     */
    public boolean checkTable(Class table){
        try{
            Dao dao = getDao(table);
            long count =dao.queryRawValue("select count(*)  from sqlite_master where type='table' and name = '" + dao.getTableName()  + "'");
            return count > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检查表中某列是否存在
     * @return
     */
    public boolean checkColumn(Class table,String columnName) {
        try {
            Dao dao = getDao(table);
            long count = dao.queryRawValue("select count(*)  from sqlite_master where type='table' and name = '"+ dao.getTableName() + "' and sql like '%"+ columnName +"%'");
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 新增字段
     * @param table         表名
     * @param columnName    字段名
     * @param columnType    字段类型
     * @param defaultValue  默认值
     * @return
     */
    public boolean addColumn(Class table,String columnName,String columnType,String defaultValue){
        try {
            Dao dao = getDao(table);
            if(!StringUtils.isTrimEmpty(defaultValue)){
                if(columnType.toLowerCase().contains("char")){
                    defaultValue = " DEFAULT '" + defaultValue + "'";
                }else {
                    defaultValue = " DEFAULT " + defaultValue;
                }
            }else{
                defaultValue = "";
            }

            int raw = dao.executeRaw("ALTER TABLE "+ dao.getTableName() +" ADD COLUMN "+ columnName +" "+ columnType + defaultValue);
            return raw > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除字段
     * @param table
     * @param columnsList 需要保留的字段
     *                    把需要删除的字段排除在外
     * @return
     */
    public boolean delColumn(Class table,List<String> columnsList){
        try {
            if(columnsList == null || columnsList.size() == 0){
                return false;
            }
            Dao dao = getDao(table);
            int raw1 = dao.executeRaw("create table temp as select "+ StringUtils.listToString(columnsList,',') + " from " + dao.getTableName());
            int raw2 = dao.executeRaw("drop table " + dao.getTableName());
            int raw3 = dao.executeRaw("alter table temp rename to " + dao.getTableName());
            return (raw1 > 0 && raw2 > 0 && raw3 > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 执行SQL  返回影响行数
     * @return
     */
    public int execute(Class table,String sql,String... arguments){
        try {
            Dao dao = getDao(table);
            int raw = dao.executeRaw(sql,arguments);
            return raw;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
