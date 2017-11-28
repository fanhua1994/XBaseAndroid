package com.hengyi.baseandroidcore.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/11/28.
 */

public class EventManager {

    public static void sendDefaultMessage(DefaultMessageEvent defaultMessageEvent){
        EventBus.getDefault().post(defaultMessageEvent);
    }
}
