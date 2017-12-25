package com.hengyi.runnable;

import com.hengyi.baseandroidcore.utilscode.LogUtils;

/**
 * 模拟登陆线程
 * Created by Administrator on 2017/12/25.
 */

public class LoginThread implements Runnable {
    @Override
    public void run() {
        try {
            //模拟线程执行
            LogUtils.d(Num.getNum() + "号线程已经执行");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
