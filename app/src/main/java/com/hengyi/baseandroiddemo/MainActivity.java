package com.hengyi.baseandroiddemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import com.hengyi.baseandroidcore.base.XBaseWebActivity;
import com.hengyi.baseandroidcore.statusbar.StatusBarCompat;
import com.hengyi.baseandroidcore.utils.ActivityUtils;
import com.hengyi.baseandroidcore.utils.ColorUtils;
import com.hengyi.baseandroidcore.weight.EaseTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity{
    @BindView(R.id.titleBar)EaseTitleBar easeTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarCompat.setStatusBarColor(this, Color.parseColor(ColorUtils.changeColor(this,R.color.main_color)));
    }

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.xbase_home,R.id.xbase_demo,R.id.xbase_csdn_blog})
    public void Click(View view){
        switch(view.getId()){
            case R.id.xbase_home:
                ActivityUtils.StartActivity(this,XBaseWebActivity.class,new String[]{XBaseWebActivity.WEB_URL_PARAM, XBaseWebActivity.WEB_SHOW_TITLE_BAR}, "https://github.com/fanhua1994/XBaseAndroid",true);
                break;

            case R.id.xbase_demo:
                ActivityUtils.StartActivity(this,XBaseWebActivity.class,new String[]{XBaseWebActivity.WEB_URL_PARAM, XBaseWebActivity.WEB_SHOW_TITLE_BAR}, "https://github.com/fanhua1994/XBaseAndroidDemo",true);
                break;

            case R.id.xbase_csdn_blog:
                ActivityUtils.StartActivity(this,XBaseWebActivity.class,new String[]{XBaseWebActivity.WEB_URL_PARAM, XBaseWebActivity.WEB_SHOW_TITLE_BAR}, "http://blog.csdn.net/dong_18383219470?viewmode=list",true);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.kill();
    }
}
