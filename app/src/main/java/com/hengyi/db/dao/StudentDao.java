package com.hengyi.db.dao;

import com.easydblib.dao.BaseDao;
import com.easydblib.info.WhereInfo;
import com.hengyi.db.table.Student;
import com.hengyi.db.util.EasyDBHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class StudentDao {
    private BaseDao<Student> student_dao = null;

    public StudentDao(){
        student_dao = EasyDBHelper.getInstance().dao(Student.class);
    }

    public boolean addStudentList(List<Student> student_list){
        int res = student_dao.add(student_list);
        return res > 0;
    }

    public boolean addStudent(Student s){
        int res = student_dao.add(s);
        return res > 0;
    }

    public void clearTable(){
        student_dao.clearTable();
    }

    public Student getStudent(String sign_number){
        List<Student> studentList = student_dao.query(WhereInfo.get().equal("sign_chip",sign_number).limit(1));//仅仅获取一条数据
        if(studentList == null || studentList.size() == 0){
            return null;
        }else{
            return studentList.get(0);
        }
    }

    public long count(){
        return student_dao.countOf();
    }
}
