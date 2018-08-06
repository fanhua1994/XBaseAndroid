package com.hengyi.baseandroiddemo;

import android.os.Bundle;
import android.view.View;

import com.hengyi.baseandroidcore.weight.LoadingLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created：2018/7/6
 * Time：19:39
 * Author：dongzp
 * Email：90fanhua@gmail.com
 * Project：XBaseAndroid
 * Use：
 */
public class LoadingActivity extends BaseActivity {
    @BindView(R.id.loadingLayout)LoadingLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingLayout.setEmptyClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toast("空视图点击事件");
            }
        });

        loadingLayout.setErrorClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                toast("错误视图 刷新事件 ");
            }
        });
    }

    @Override
    public int setContentView() {
        return R.layout.activity_loading;
    }

    @OnClick({R.id.btn_showContent,R.id.btn_showEmpty,R.id.btn_showError,R.id.btn_showLoading})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_showContent:
                toast("显示内容");
                loadingLayout.showContent();
                break;

            case R.id.btn_showEmpty:
                toast("显示空视图");
                loadingLayout.showEmpty("我是空视图",R.drawable.ic_launcher);
                break;

            case R.id.btn_showError:
                toast("显示错误信息");
                loadingLayout.showError("我是错误信息",R.drawable.ic_launcher);
                break;

            case R.id.btn_showLoading:
                toast("显示加载中");
                loadingLayout.showLoading("正在加载数据");
                break;
        }
    }
}
