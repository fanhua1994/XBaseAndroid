package com.hengyi.runnable;

/**
 * Created by Administrator on 2017/12/25.
 */

public class Num {
    private static int num = 1;

    public static synchronized  int getNum(){
        num++;
        return num;
    }
}
