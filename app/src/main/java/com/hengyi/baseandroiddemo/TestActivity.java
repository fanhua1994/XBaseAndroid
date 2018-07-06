package com.hengyi.baseandroiddemo;

import android.Manifest;
import android.os.Bundle;

import com.hengyi.baseandroidcore.base.XBasePermissionActivity;

/**
 * Created: 2018/3/21 9:41
 * Author:fanhua
 * Email:90fanhua@gmail.com
 * Project:XBaseAndroid
 */

public class TestActivity extends XBasePermissionActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermisstion(200,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_CONTACTS});
    }

    @Override
    public void onPermissionSuccess() {
        toast("权限申请成功");
    }

    @Override
    public void onPermissionError(String[] deniedPermissions) {
        toast("权限申请失败");
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_test;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
