package com.hengyi.baseandroiddemo;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hengyi.baseandroidcore.base.XBaseActivity;
import com.hengyi.baseandroidcore.database.BaseDao;

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
        requestPermission(200,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_CONTACTS});
    }

    @Override
    public void onPermissionSuccess() {
        super.onPermissionSuccess();
        toast("申请成功");
    }

    @Override
    public void onPermissionError(String[] deniedPermissions) {
        super.onPermissionError(deniedPermissions);
        toast("申请失败");
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
