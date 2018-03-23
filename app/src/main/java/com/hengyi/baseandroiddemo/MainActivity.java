package com.hengyi.baseandroiddemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hengyi.baseandroidcore.base.XBaseBrowserActivity;
import com.hengyi.baseandroidcore.statusbar.StatusBarCompat;
import com.hengyi.baseandroidcore.utils.ActivityUtils;
import com.hengyi.baseandroidcore.utils.AppConfig;
import com.hengyi.baseandroidcore.utils.ColorUtils;
import com.hengyi.baseandroidcore.utils.CommonUtils;
import com.hengyi.baseandroidcore.utils.SystemUtils;
import com.hengyi.baseandroidcore.utils.VersionUtils;
import com.hengyi.baseandroidcore.weight.EaseTitleBar;
import com.hengyi.baseandroidcore.weight.LoadingLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity{
    @BindView(R.id.titleBar)EaseTitleBar easeTitleBar;
    @BindView(R.id.cid)TextView cid;
    @BindView(R.id.version)TextView version;
    @BindView(R.id.loading_view)LoadingLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarCompat.setStatusBarColor(this, Color.parseColor(ColorUtils.changeColor(this,R.color.main_color)));

        easeTitleBar.setLeftLayoutClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                CommonUtils.kill();
            }
        });

        version.setText("当前版本：" + VersionUtils.getAppVersion(this,"1.0.0.0"));

        cid.setText("您的永久CID：" + SystemUtils.getClientID() +"\n" + "您的临时CID："+SystemUtils.getShortClientID(this)+"\n（重装失效）");

        String name = AppConfig.getInstance().load(this).getValue("name");
        toast("读取到配置：" + name);
    }

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.xbase_home,R.id.xbase_mui,R.id.xbase_sui})
    public void Click(View view){
        switch(view.getId()){
            case R.id.xbase_home:
                ActivityUtils.startActivity(this,XBaseBrowserActivity.class,new String[]{XBaseBrowserActivity.WEB_URL, XBaseBrowserActivity.SHOW_TITLE_BAR,XBaseBrowserActivity.SHOW_REFRESH}, XBaseBrowserActivity.ANDROID_ASSSET_PATH + "template/index.html",true,false);
                break;

            case R.id.xbase_mui:
                ActivityUtils.startActivity(this,XBaseBrowserActivity.class,new String[]{XBaseBrowserActivity.WEB_URL, XBaseBrowserActivity.SHOW_TITLE_BAR}, "http://www.dcloud.io/hellomui/",false);
                break;

            case R.id.xbase_sui:
                ActivityUtils.startActivity(this,XBaseBrowserActivity.class,new String[]{XBaseBrowserActivity.WEB_URL, XBaseBrowserActivity.SHOW_TITLE_BAR}, "http://m.sui.taobao.org/demos/",false);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtils.kill();
    }
}
