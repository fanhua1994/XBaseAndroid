package com.hengyi.baseandroiddemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devspark.appmsg.AppMsg;
import com.hengyi.baseandroidcore.base.BaseActivity;
import com.hengyi.baseandroidcore.dialog.CustomAlertDialog;
import com.hengyi.baseandroidcore.dialog.CustomConfirmDialog;
import com.hengyi.baseandroidcore.utils.ConfigUtils;
import com.hengyi.baseandroidcore.utils.ProjectUtils;
import com.hengyi.baseandroidcore.utils.TimerUtils;
import com.hengyi.baseandroidcore.utilscode.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MyBaseActivity {
    @BindView(R.id.cache_admin)Button cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConfigUtils config = ConfigUtils.getInstance(this);
        config.addOrUpdateText("name","繁华");
        toast(config.findStringByKey("name"));
        config.clearConfig();
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
