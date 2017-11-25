package com.hengyi.event;

import java.io.Serializable;

/**
 * Created by fanhua on 17-11-18.
 */

public class MessageEvent implements Serializable{
    private Object obj;
    private String string_str;

    private int int_num1;
    private int int_num2;
    private float float_num1;
    private float float_num2;
    private double double_num1;
    private double double_num2;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getString_str() {
        return string_str;
    }

    public void setString_str(String string_str) {
        this.string_str = string_str;
    }

    public int getInt_num1() {
        return int_num1;
    }

    public void setInt_num1(int int_num1) {
        this.int_num1 = int_num1;
    }

    public int getInt_num2() {
        return int_num2;
    }

    public void setInt_num2(int int_num2) {
        this.int_num2 = int_num2;
    }

    public float getFloat_num1() {
        return float_num1;
    }

    public void setFloat_num1(float float_num1) {
        this.float_num1 = float_num1;
    }

    public float getFloat_num2() {
        return float_num2;
    }

    public void setFloat_num2(float float_num2) {
        this.float_num2 = float_num2;
    }

    public double getDouble_num1() {
        return double_num1;
    }

    public void setDouble_num1(double double_num1) {
        this.double_num1 = double_num1;
    }

    public double getDouble_num2() {
        return double_num2;
    }

    public void setDouble_num2(double double_num2) {
        this.double_num2 = double_num2;
    }
}
