package com.hengyi.baseandroidcore.event;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by fanhua on 17-11-18.
 */
public class DefaultMessageEvent implements Serializable{
    private Object obj;
    private int code;
    private int status;
    private int mint;
    private int type;
    private float mfloat;
    private double mdouble;
    private long mlong;
    private String content;
    private Map<String,Object> map;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMint() {
        return mint;
    }

    public void setMint(int mint) {
        this.mint = mint;
    }

    public float getMfloat() {
        return mfloat;
    }

    public void setMfloat(float mfloat) {
        this.mfloat = mfloat;
    }

    public double getMdouble() {
        return mdouble;
    }

    public void setMdouble(double mdouble) {
        this.mdouble = mdouble;
    }

    public long getMlong() {
        return mlong;
    }

    public void setMlong(long mlong) {
        this.mlong = mlong;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
