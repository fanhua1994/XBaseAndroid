package com.hengyi.baseandroiddemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hengyi.baseandroidcore.base.XBaseWebActivity;
import com.hengyi.baseandroidcore.dialog.CustomConfirmDialog;
import com.hengyi.baseandroidcore.statusbar.StatusBarCompat;
import com.hengyi.baseandroidcore.update.Apatch;
import com.hengyi.baseandroidcore.update.AppUpdateManager;
import com.hengyi.baseandroidcore.update.BuildType;
import com.hengyi.baseandroidcore.update.PatchBean;
import com.hengyi.baseandroidcore.utils.ActivityUtils;
import com.hengyi.baseandroidcore.utils.ColorUtils;
import com.hengyi.baseandroidcore.utils.CommonUtils;
import com.hengyi.baseandroidcore.utils.SystemUtils;
import com.hengyi.baseandroidcore.utils.VersionUtils;
import com.hengyi.baseandroidcore.weight.EaseTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity{
    @BindView(R.id.titleBar)EaseTitleBar easeTitleBar;
    @BindView(R.id.cid)TextView cid;
    @BindView(R.id.version)TextView version;

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
    }

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.xbase_jianshu_blog,R.id.xbase_home,R.id.xbase_demo,R.id.xbase_csdn_blog,R.id.xbase_patch,R.id.xbase_open_patch})
    public void Click(View view){
        switch(view.getId()){
            case R.id.xbase_home:
                ActivityUtils.startActivity(this,XBaseWebActivity.class,new String[]{XBaseWebActivity.WEB_URL_PARAM, XBaseWebActivity.WEB_SHOW_TITLE_BAR}, "https://github.com/fanhua1994/XBaseAndroid",true);
                break;

            case R.id.xbase_demo:
                ActivityUtils.startActivity(this,XBaseWebActivity.class,new String[]{XBaseWebActivity.WEB_URL_PARAM, XBaseWebActivity.WEB_SHOW_TITLE_BAR}, "https://github.com/fanhua1994/XBaseAndroidDemo",true);
                break;

            case R.id.xbase_csdn_blog:
                ActivityUtils.startActivity(this,XBaseWebActivity.class,new String[]{XBaseWebActivity.WEB_URL_PARAM, XBaseWebActivity.WEB_SHOW_TITLE_BAR}, "http://blog.csdn.net/dong_18383219470?viewmode=list",true);
                break;

            case R.id.xbase_jianshu_blog:
                ActivityUtils.startActivity(this,XBaseWebActivity.class,new String[]{XBaseWebActivity.WEB_URL_PARAM, XBaseWebActivity.WEB_SHOW_TITLE_BAR}, "https://www.jianshu.com/u/50c9e5f00da3",true);
                break;
            case R.id.xbase_patch:
                CustomConfirmDialog dialog = new CustomConfirmDialog(this).builder();
                dialog.setTitle("补丁下载路径");
                dialog.setHintText("请输入补丁下载地址");
                dialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                dialog.setPositiveButton("打补丁",new CustomConfirmDialog.OnPostListener(){

                    @Override
                    public void OnPost(String value) {
                        PatchBean patchBean = new PatchBean();
                        patchBean.setMsg("OK");
                        patchBean.setResult(true);
                        List<Apatch> patch = new ArrayList<>();
                        Apatch apatch = new Apatch();
                        apatch.setBuild_type(BuildType.DEBUG);
                        apatch.setMd5(null);
                        apatch.setPath(value);
                        patch.add(apatch);
                        patchBean.setData(patch);

                        AppUpdateManager appUpdateManager = AppUpdateManager.getInstance();
                        appUpdateManager.loadPatch(patchBean,BuildType.DEBUG);
                    }
                });
                dialog.show();
                break;
            case R.id.xbase_open_patch:
                ActivityUtils.startActivity(MainActivity.this,PatchActivity.class);
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtils.kill();
    }
}
