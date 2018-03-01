package com.hengyi.baseandroiddemo;

import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;

/**
 * Created by ZW-2 on 2018/3/1.
 */

public class PatchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_patch)
    public void onClick(View view){
        showToast();
    }

    private void showToast(){
        toast("我是修复版本，我已经打了补丁啦");
    }

    @Override
    public int setContentView() {
        return R.layout.activity_patch;
    }
}
