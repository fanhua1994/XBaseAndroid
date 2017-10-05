package com.hengyi.baseandroidcore.utils;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 繁华 on 2017/5/14.
 */

public class TimerUtil {
    private static TimerUtil instance = null;
    private TimerTaskCallbackListener listener;
    private Timer timer = new Timer();
    private int timerInterval = 0;
    private int timer_id = 0;
    private int timer_count = 0;

    public static TimerUtil getInstance(){
        if(instance == null)
            instance = new TimerUtil();
        return instance;
    }

    public void startTimer(int timerInterval){
        this.timerInterval = timerInterval * 1000;
        timer.schedule(task,this.timerInterval,this.timerInterval);
    }

    public void stopTimer(){
        timer.cancel();
    }

    public void setTimer_id(int timer_id){
        this.timer_id = timer_id;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            timer_count ++ ;
            listener.Timing(timer_id,timer_count);
        }
    };


    private TimerTask task = new TimerTask() {
        public void run() {
           handler.sendEmptyMessage(0);
        }
    };

    public void setTimerListener(TimerTaskCallbackListener timer){
        this.listener = timer;
    }

    //接口回调
   public interface TimerTaskCallbackListener{
        public void Timing(int timer_id, int timer_count);
    }
}
