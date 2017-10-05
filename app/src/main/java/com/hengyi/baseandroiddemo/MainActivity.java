package com.hengyi.baseandroiddemo;

import android.os.Bundle;
import android.view.View;

import com.hengyi.baseandroidcore.base.BaseActivity;
import com.hengyi.baseandroidcore.dialog.CustomAlertDialog;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //弹出提示框

        CustomAlertDialog dialog = new CustomAlertDialog(this).builder();
        dialog.setTitle("温馨提示");
        dialog.setMsg("你好啊");
        dialog.setNegativeButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("点击了确定");
            }
        });

        dialog.setPositiveButton("取消",null);
        dialog.show();
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_main;
    }
}
