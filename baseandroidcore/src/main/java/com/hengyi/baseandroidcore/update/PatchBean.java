package com.hengyi.baseandroidcore.update;

import java.util.List;

/**
 * Created by ZW-2 on 2018/2/28.
 */

public class PatchBean {
    private int statuCode;
    private String msg;
    private boolean result;
    private List<Apatch> data;

    public int getStatuCode() {
        return statuCode;
    }

    public void setStatuCode(int statuCode) {
        this.statuCode = statuCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<Apatch> getData() {
        return data;
    }

    public void setData(List<Apatch> data) {
        this.data = data;
    }
}
