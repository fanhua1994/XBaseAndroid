package com.hengyi.baseandroidcore.event;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by fanhua on 17-11-18.
 */
public class EventMessage implements Serializable{
    private int code;
    private int status;
    private int mint1;
    private int min2;
    private int type;
    private float mfloat1;
    private float mfloat2;
    private double mdouble1;
    private double mdouble2;
    private long mlong1;
    private long mlong2;
    private String content;
    private String message;
    private String text;

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

    public int getMint1() {
        return mint1;
    }

    public void setMint1(int mint1) {
        this.mint1 = mint1;
    }

    public int getMin2() {
        return min2;
    }

    public void setMin2(int min2) {
        this.min2 = min2;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getMfloat1() {
        return mfloat1;
    }

    public void setMfloat1(float mfloat1) {
        this.mfloat1 = mfloat1;
    }

    public float getMfloat2() {
        return mfloat2;
    }

    public void setMfloat2(float mfloat2) {
        this.mfloat2 = mfloat2;
    }

    public double getMdouble1() {
        return mdouble1;
    }

    public void setMdouble1(double mdouble1) {
        this.mdouble1 = mdouble1;
    }

    public double getMdouble2() {
        return mdouble2;
    }

    public void setMdouble2(double mdouble2) {
        this.mdouble2 = mdouble2;
    }

    public long getMlong1() {
        return mlong1;
    }

    public void setMlong1(long mlong1) {
        this.mlong1 = mlong1;
    }

    public long getMlong2() {
        return mlong2;
    }

    public void setMlong2(long mlong2) {
        this.mlong2 = mlong2;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
