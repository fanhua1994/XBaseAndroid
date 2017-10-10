package com.hengyi.baseandroidcore.database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/10.
 */
public class BaseDaoImpl<T,Integer> extends BaseDao<T,Integer> {
    private Class<T> clazz;
    private Map<Class<T>,Dao<T,Integer>> mDaoMap=new HashMap<Class<T>,Dao<T,Integer>>();
    //缓存泛型Dao
    public BaseDaoImpl(Context context, Class<T> clazz) {
        super(context);
        this.clazz=clazz;
    }

    @Override
    public Dao<T, Integer> getDao() throws SQLException {
        Dao<T,Integer> dao=mDaoMap.get(clazz);
        if (null==dao){
            dao=mDatabaseHelper.getDao(clazz);
            mDaoMap.put(clazz,dao);
        }
        return dao;
    }
}
