package com.hengyi.db.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017/9/13.
 */

@DatabaseTable
public class Student extends BaseModel{
    @DatabaseField
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField(index = true)
    private String sign_chip;
    @DatabaseField
    private String avatar;
    @DatabaseField
    private int class_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign_chip() {
        return sign_chip;
    }

    public void setSign_chip(String sign_chip) {
        this.sign_chip = sign_chip;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }
}
