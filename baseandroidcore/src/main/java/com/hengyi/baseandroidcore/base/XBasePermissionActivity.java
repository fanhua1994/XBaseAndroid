package com.hengyi.baseandroidcore.base;

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

    public void requestPermisstion(int requestCode,String[] permisstion){
        PermissionUtils.requestPermissions(this, requestCode, permisstion, new PermissionUtils.OnPermissionListener() {
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
