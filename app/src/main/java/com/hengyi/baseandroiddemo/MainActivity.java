package com.hengyi.baseandroiddemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devspark.appmsg.AppMsg;
import com.hengyi.baseandroidcore.base.BaseActivity;
import com.hengyi.baseandroidcore.dialog.CustomAlertDialog;
import com.hengyi.baseandroidcore.dialog.CustomConfirmDialog;
import com.hengyi.baseandroidcore.utils.ActivityStack;
import com.hengyi.baseandroidcore.utils.ConfigUtils;
import com.hengyi.baseandroidcore.utils.ProjectUtils;
import com.hengyi.baseandroidcore.utils.TimerUtils;
import com.hengyi.baseandroidcore.utilscode.LogUtils;
import com.hengyi.baseandroidcore.weight.EaseTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MyBaseActivity {
    @BindView(R.id.cache_admin)Button cache;
    @BindView(R.id.titleBar)EaseTitleBar easeTitleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProjectUtils p = ProjectUtils.getInstance();
        p.setIdCard(true);
        p.setFileType(ProjectUtils.COMMON_TYPE);
        p.createWorkGroup("繁华");
        p.writeWorkGroup("dongzhiping.txt","繁华","你好啊");
        
        easeTitleBar.setLeftLayoutClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ActivityStack.getInstance().popActivity();
            }
        });
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
