package com.hengyi.baseandroiddemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hengyi.baseandroidcore.browser.XBaseBrowserActivity;
import com.hengyi.baseandroidcore.statusbar.StatusBarCompat;
import com.hengyi.baseandroidcore.utils.ActivityRouter;
import com.hengyi.baseandroidcore.utils.ColorUtils;
import com.hengyi.baseandroidcore.utils.CommonUtils;
import com.hengyi.baseandroidcore.utils.SystemUtils;
import com.hengyi.baseandroidcore.utils.VersionUtils;
import com.hengyi.baseandroidcore.weight.LoadingLayout;
import com.hengyi.baseandroidcore.weight.XBaseTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity{
    @BindView(R.id.titleBar)XBaseTitleBar easeTitleBar;
    @BindView(R.id.cid)TextView cid;
    @BindView(R.id.version)TextView version;
    @BindView(R.id.loading_view)LoadingLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarCompat.setStatusBarColor(this, Color.parseColor(ColorUtils.changeColor(this,R.color.my_main_color)));
        easeTitleBar.setBackgroundColor(getResources().getColor(R.color.my_main_color));

        easeTitleBar.setLeftLayoutClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                CommonUtils.kill();
            }
        });
        easeTitleBar.hideLeftLayout();
        easeTitleBar.hideRightLayout();

        version.setText("当前版本：" + VersionUtils.getVersionName(this,"1.0.0.0"));

        cid.setText("您的永久CID：" + SystemUtils.getClientID() +"\n" + "您的临时CID："+SystemUtils.getShortClientID(this)+"\n（重装失效）");

        OkGo.
    }


    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.xbase_home,R.id.xbase_mui,R.id.xbase_sui,R.id.xbase_youku,R.id.xbase_loading,R.id.xbase_permission})
    public void Click(View view){
        switch(view.getId()){
            case R.id.xbase_home:
                ActivityRouter.getInstance()
                        .add(XBaseBrowserActivity.WEB_URL,XBaseBrowserActivity.ANDROID_ASSSET_PATH + "template/index.html")//加载本地asset加上XBaseBrowserActivity.ANDROID_ASSSET_PATH + 你的html路径。
                        .add(XBaseBrowserActivity.SHOW_TITLE_BAR,true)//是否显示标题栏
                        .add(XBaseBrowserActivity.SHOW_REFRESH,false)//是否可以下拉刷新
                        .startActivity(this,XBaseBrowserActivity.class);
            break;

            case R.id.xbase_mui:
                ActivityRouter.getInstance()
                        .add(XBaseBrowserActivity.WEB_URL,"http://www.dcloud.io/hellomui/list.html?v=1")
                        .add(XBaseBrowserActivity.SHOW_TITLE_BAR,true)
                        .startActivity(this,XBaseBrowserActivity.class);
                break;

            case R.id.xbase_sui:
                ActivityRouter.getInstance()
                        .add(XBaseBrowserActivity.WEB_URL,"http://m.sui.taobao.org/demos/")
                        .add(XBaseBrowserActivity.SHOW_TITLE_BAR,true)
                        .startActivity(this,XBaseBrowserActivity.class);
                break;
            case R.id.xbase_youku:
                ActivityRouter.getInstance()
                        .add(XBaseBrowserActivity.WEB_URL,"https://www.youku.com/")
                        .add(XBaseBrowserActivity.SHOW_TITLE_BAR,true)
                        .startActivity(this,XBaseBrowserActivity.class);
                break;

            case R.id.xbase_loading:
                ActivityRouter.getInstance().startActivity(this,LoadingActivity.class);
                break;

            case R.id.xbase_permission:
                ActivityRouter.getInstance().startActivity(this,TestActivity.class);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtils.kill();
    }

}
