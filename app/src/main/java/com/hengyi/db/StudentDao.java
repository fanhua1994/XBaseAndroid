package com.hengyi.db;

import android.content.Context;

import com.hengyi.baseandroidcore.database.BaseDao;
import com.hengyi.baseandroidcore.database.BaseDaoImpl;

import java.sql.SQLException;
import java.util.List;

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
            e.printStackTrace();
            return 0;
        }
    }

    public long count(){
        try {
            return studentDao.count();
        } catch (SQLException e) {
            return -1;
        }
    }

    public List<Student> getAll(){
        try {
            return studentDao.queryAll();
        } catch (SQLException e) {
            return null;
        }
    }
}
