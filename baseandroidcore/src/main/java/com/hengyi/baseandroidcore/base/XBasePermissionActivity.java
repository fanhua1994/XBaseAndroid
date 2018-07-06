package com.hengyi.baseandroidcore.base;

import android.os.Bundle;

import com.hengyi.baseandroidcore.xutils.PermissionUtils;

/**
 * Created：2018/7/5
 * Time：14:10
 * Author：dongzp
 * Email：90fanhua@gmail.com
 * Project：XBaseAndroid
 * Use：
 */
public abstract class XBasePermissionActivity extends XBaseActivity {

    public abstract void onPermissionSuccess();
    public abstract void onPermissionError(String[] deniedPermissions);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void requestPermisstion(final int requestCode,final String[] permisstion){
        PermissionUtils.requestPermissions(getContext(), requestCode, permisstion, new PermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                onPermissionSuccess();
            }

            @Override
            public void onPermissionDenied(String[] deniedPermissions) {
                onPermissionError(deniedPermissions);
            }
        });
    }
}
