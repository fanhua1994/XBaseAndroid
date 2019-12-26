package com.hengyi.baseandroiddemo;

import android.Manifest;
import android.os.Bundle;

import com.hengyi.baseandroidcore.base.XBaseActivity;

/**
 * Created: 2018/3/21 9:41
 * Author:fanhua
 * Email:90fanhua@gmail.com
 * Project:XBaseAndroid
 */

public class TestActivity extends XBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(200,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_CONTACTS});
    }

    @Override
    public void onPermissionSuccess() {
        super.onPermissionSuccess();
        toast("申请成功");
    }

    @Override
    public void onPermissionDenied(String[] deniedPermissions) {
        super.onPermissionDenied(deniedPermissions);
        toast("权限申请失败");
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_test;
    }

    @Override
    public void init() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
