package com.hengyi.baseandroidcore.utils;

import android.os.CountDownTimer;

/**
 * Created by 繁华 on 2017/5/14.
 * 倒计时工具类
 */

public class CountDownUtil{
    private setOnCountDownListener listener = null;
    private CountDownTimer timer = null;

    public CountDownUtil(int CountSecond,int IntervalSecond){
        timer = new CountDownTimer(CountSecond * 1000,IntervalSecond * 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                if(listener != null)
                listener.onTick((int)(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                if(listener != null)
                listener.onFinish();
            }
        };
    }

    public void start(setOnCountDownListener listener){
        this.listener = listener;
        timer.start();
    }

    public void stop(){
        timer.cancel();
    }

    public interface setOnCountDownListener{
        public void onTick(int second);
        public void onFinish();
    }
}
