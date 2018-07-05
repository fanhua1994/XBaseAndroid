package com.hengyi.baseandroiddemo;

import android.os.Bundle;

import com.hengyi.baseandroidcore.base.XBaseActivity;
import com.hengyi.baseandroidcore.base.XBasePermissionActivity;

/**
 * Created: 2018/3/21 9:41
 * Author:fanhua
 * Email:90fanhua@gmail.com
 * Project:XBaseAndroid
 */

public class TestActivity extends XBasePermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermisstion(0,new String[]{});
    }

    @Override
    public int setBaseContentView() {
        return 0;
    }

    @Override
    public void onPermissionSuccess() {

    }

    @Override
    public void onPermissionDenied(String[] deniedPermissions) {

    }
}
