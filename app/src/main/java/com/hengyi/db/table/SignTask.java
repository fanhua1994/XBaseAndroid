package com.hengyi.db.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017/9/13.
 */
@DatabaseTable
public class SignTask {

    @DatabaseField(generatedId = true)
    private int task_id;
    @DatabaseField
    private String number;
    @DatabaseField
    private int student_id;
    @DatabaseField
    private String sign_path;
    @DatabaseField
    private String sign_tiwen;
    @DatabaseField
    private int type;//1入园2出园

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getSign_path() {
        return sign_path;
    }

    public void setSign_path(String sign_path) {
        this.sign_path = sign_path;
    }

    public String getSign_tiwen() {
        return sign_tiwen;
    }

    public void setSign_tiwen(String sign_tiwen) {
        this.sign_tiwen = sign_tiwen;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}