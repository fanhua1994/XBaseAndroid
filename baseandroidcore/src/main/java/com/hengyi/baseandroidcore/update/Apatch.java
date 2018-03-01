package com.hengyi.baseandroidcore.update;

/**
 * Created by ZW-2 on 2018/3/1.
 */

public class Apatch {
    private int ptach_id;
    private String path;
    private int build_type;//支持release/debug
    private String md5;

    public int getPtach_id() {
        return ptach_id;
    }

    public void setPtach_id(int ptach_id) {
        this.ptach_id = ptach_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getBuild_type() {
        return build_type;
    }

    public void setBuild_type(int build_type) {
        this.build_type = build_type;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
