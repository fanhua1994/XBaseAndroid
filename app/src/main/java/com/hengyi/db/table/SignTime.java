package com.hengyi.db.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017/9/22.
 */
@DatabaseTable
public class SignTime extends BaseModel{
    @DatabaseField(generatedId = true)
    private int time_id;//时间ID

    @DatabaseField(index = true)
    private int hour;//小时
    @DatabaseField
    private int minute;//分钟

    @DatabaseField
    private boolean status;//是开始还是结束时间

    @DatabaseField
    private String name;//名称

    @DatabaseField
    private int passorout;//1入园 2出园

    public int getPassorout() {
        return passorout;
    }

    public void setPassorout(int passorout) {
        this.passorout = passorout;
    }

    public int getTime_id() {
        return time_id;
    }

    public void setTime_id(int time_id) {
        this.time_id = time_id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
