package com.hengyi.baseandroidcore.update;

/**
 * Created by fanhua on 17-11-11.
 */
public class UpdateBean {
    private String new_version;//版本号
    private String description;//描述
    private String title;//更新提示标题
    private boolean isForce = false;//是否强制更新，不更新允许运行。
    private String download_url;//下载地址
    private String md5_code;//apk文件的MD5
    private String authority;//安卓7.0适配

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getNew_version() {
        return new_version;
    }

    public void setNew_version(String new_version) {
        this.new_version = new_version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean force) {
        isForce = force;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getMd5_code() {
        return md5_code;
    }

    public void setMd5_code(String md5_code) {
        this.md5_code = md5_code;
    }
}
