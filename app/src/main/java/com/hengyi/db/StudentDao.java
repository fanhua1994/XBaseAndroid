package com.hengyi.db;

import android.content.Context;

import com.hengyi.baseandroidcore.database.BaseDao;
import com.hengyi.baseandroidcore.database.BaseDaoImpl;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/10/11.
 */

public class StudentDao {
    private BaseDao<Student,Integer> studentDao;

    public StudentDao(Context context){
        studentDao = new BaseDaoImpl<>(context,Student.class);
    }

    public int add(Student s){
        try {
            return studentDao.save(s);
        } catch (SQLException e) {
            return 0;
        }
    }
}
