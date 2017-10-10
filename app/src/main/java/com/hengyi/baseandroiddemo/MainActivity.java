package com.hengyi.baseandroiddemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devspark.appmsg.AppMsg;
import com.hengyi.baseandroidcore.base.BaseActivity;
import com.hengyi.baseandroidcore.dialog.CustomAlertDialog;
import com.hengyi.baseandroidcore.utils.ProjectUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.cache_admin)Button cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);//注册事件

        //弹出提示框
        CustomAlertDialog dialog = new CustomAlertDialog(this).builder();
        dialog.setTitle("温馨提示");
        dialog.setMsg("你好啊");
        dialog.setNegativeButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("点击了确定");
                AppMsg.makeText(MainActivity.this,"点击了确定",AppMsg.STYLE_CONFIRM).show();

            }
        });

        dialog.setPositiveButton("取消",null);
        dialog.show();

       toast(ProjectUtils.getInstance().getWorkDir());
     }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.cache_admin)
    public void Click(View view){
        StartActivity(CacheActivity.class);
    }
}
