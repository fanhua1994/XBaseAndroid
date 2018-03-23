package com.hengyi.baseandroiddemo;

import android.os.Bundle;

import com.hengyi.baseandroidcore.base.XBaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Fanhua on 2017/10/10.
 * 由于ButterKnife不能再lib中bind。所以必须继承BaseActivity进行二次封装
 */

public abstract class BaseActivity extends XBaseActivity {
    private Unbinder unbind = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbind = ButterKnife.bind(this);
    }

    @Override
    public int setBaseContentView() {
        return setContentView();
    }

    public abstract int setContentView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind.unbind();
    }
}
