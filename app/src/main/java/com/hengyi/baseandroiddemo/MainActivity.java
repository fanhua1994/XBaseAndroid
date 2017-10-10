package com.hengyi.baseandroiddemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devspark.appmsg.AppMsg;
import com.hengyi.baseandroidcore.base.BaseActivity;
import com.hengyi.baseandroidcore.dialog.CustomAlertDialog;
import com.hengyi.baseandroidcore.dialog.CustomConfirmDialog;
import com.hengyi.baseandroidcore.utils.ProjectUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MyBaseActivity {
    @BindView(R.id.cache_admin)Button cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //弹出输入框
        CustomConfirmDialog dialog = new CustomConfirmDialog(this).builder();
        dialog.setTitle("温馨提示");
        dialog.setInputNumber(false);
        dialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dialog.setPositiveButton("取消",new CustomConfirmDialog.OnPostListener(){

            @Override
            public void OnPost(String value) {
                toast(value);
            }
        });
        dialog.show();

     }

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.cache_admin)
    public void Click(View view){
        StartActivity(CacheActivity.class);
    }
}
