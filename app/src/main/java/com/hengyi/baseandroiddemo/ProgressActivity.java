package com.hengyi.baseandroiddemo;

import android.os.Bundle;

import com.hengyi.baseandroidcore.utils.CountDownUtils;
import com.hengyi.baseandroidcore.weight.NumberProgressBar;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/4.
 */

public class ProgressActivity extends BaseActivity {
    @BindView(R.id.numberbar)NumberProgressBar numberProgressBar;

    @Override
    public int setContentView() {
        return R.layout.activity_progress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CountDownUtils countDownUtils = new CountDownUtils(100000,1000);
        countDownUtils.start(new CountDownUtils.setOnCountDownListener() {
            @Override
            public void onTick(int second) {
                numberProgressBar.setProgress(100 - second);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
