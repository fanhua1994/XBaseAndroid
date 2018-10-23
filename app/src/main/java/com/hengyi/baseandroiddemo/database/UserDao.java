package com.hengyi.baseandroiddemo.database;

import android.content.Context;

import com.hengyi.baseandroidcore.database.BaseDao;
import com.hengyi.baseandroidcore.database.BaseDaoImpl;

import java.sql.SQLException;

/**
 * Created：2018/10/23
 * Time：22:36
 * Author：dongzp
 * Email：90fanhua@gmail.com
 * Project：XBaseAndroid
 * Use：
 */
public class UserDao {
    private BaseDao<User,Integer> userDao;//数据库操作对象

    public UserDao(Context context){
        userDao = new BaseDaoImpl<>(context,User.class);
    }

    public int add(User s){
        try {
            return userDao.save(s);
        } catch (SQLException e) {
            return -1;
        }
    }
}
