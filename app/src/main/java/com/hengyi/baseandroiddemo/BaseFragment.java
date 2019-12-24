package com.hengyi.baseandroiddemo;

import android.os.Bundle;
import android.view.View;

import com.hengyi.baseandroidcore.base.XBaseFragment;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Fanhua on 2017/10/10.
 * 由于ButterKnife不能再lib中bind。所以必须继承BaseFragment进行二次封装
 */

public abstract class BaseFragment extends XBaseFragment {
    private Unbinder unbinder = null;

    public abstract int setContentView();
    public abstract void initView();

    @Override
    public int setBaseContentView() {
        return setContentView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this,view);
    }

    @Override
    public void init() {
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
