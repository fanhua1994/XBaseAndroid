package com.hengyi.baseandroiddemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hengyi.baseandroidcore.base.WebEngineActivity;
import com.hengyi.baseandroidcore.utils.ActivityStack;
import com.hengyi.baseandroidcore.weight.EaseTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends MyBaseActivity {
    @BindView(R.id.cache_admin)Button cache;
    @BindView(R.id.titleBar)EaseTitleBar easeTitleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //showLoadingDialog("正在加载");
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

    @OnClick({R.id.cache_admin,R.id.web})
    public void Click(View view){
        switch(view.getId()){
            case R.id.cache_admin:
                StartActivity(CacheActivity.class);
                break;

            case R.id.web:
                StartActivity(WebEngineActivity.class,new String[]{"url"},"https://yunqi.aliyun.com/?open_id=5a5a2d8b-e185-4efa-8722-4a841b72c7f4--1199333720&open_cid=3483#/video/detail1106");
                break;
        }

    }
}
