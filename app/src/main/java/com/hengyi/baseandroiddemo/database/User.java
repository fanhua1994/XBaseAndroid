package com.hengyi.baseandroiddemo.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created：2018/10/23
 * Time：22:28
 * Author：dongzp
 * Email：90fanhua@gmail.com
 * Project：XBaseAndroid
 * Use：
 */
@DatabaseTable
public class User {

    @DatabaseField(generatedId = true,columnName = "user_id")
    private int userId;

    @DatabaseField
    private String name;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
