package com.hengyi.baseandroidcore.utils;

import android.os.CountDownTimer;

/**
 * Created by 繁华 on 2017/5/14.
 * 倒计时工具类
 */

public class CountdownUtils {
    private static CountdownUtils instance = null;
    private setOnCountDownListener listener = null;
    private CountDownTimer timer = null;

    public static synchronized  CountdownUtils getInstance(){
        if(instance == null){
            synchronized (CountdownUtils.class){
                if(instance == null)
                    instance = new CountdownUtils();
            }
        }
        return  instance;
    }

    public void start(int CountSecond, int IntervalSecond){
        timer = new CountDownTimer(CountSecond,IntervalSecond){
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
        timer.start();

    }

    public void setCountdownListener(setOnCountDownListener listener){
        this.listener = listener;
    }

    public void stop(){
        timer.cancel();
    }

    public interface setOnCountDownListener{
        public void onTick(int second);
        public void onFinish();
    }
}
