package com.hengyi.baseandroiddemo;

import android.os.Bundle;

import com.hengyi.baseandroidcore.base.BaseActivity;
import com.hengyi.baseandroidcore.dialog.CustomAlertDialog;
import com.hengyi.baseandroidcore.utils.ActivityStack;
import com.hengyi.baseandroidcore.utils.CountDownUtil;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //弹出提示框
        /*
        CustomAlertDialog dialog = new CustomAlertDialog(this).builder();
        dialog.setTitle("温馨提示");
        dialog.setMsg("你好啊");
        dialog.show();*/

        CountDownUtil cd = new CountDownUtil(5000,1000);
        cd.start(new CountDownUtil.setOnCountDownListener() {
            @Override
            public void onTick(int second) {

            }

            @Override
            public void onFinish() {
                ActivityStack.getInstance().clearAllActivity();
                kill();
            }
        });
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_main;
    }
}
