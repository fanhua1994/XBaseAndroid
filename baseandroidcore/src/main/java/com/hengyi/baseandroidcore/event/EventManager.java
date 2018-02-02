package com.hengyi.baseandroidcore.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Fanhua on 2017/11/28.
 */

public class EventManager {
    private static DefaultMessageEvent defaultMessageEvent = null;

    public static void sendDefaultMessage(DefaultMessageEvent defaultMessageEvent){
        EventBus.getDefault().post(defaultMessageEvent);
    }

    public static DefaultMessageEvent getDefaultMessage(){
        if(defaultMessageEvent == null)
            defaultMessageEvent = new DefaultMessageEvent();
        return defaultMessageEvent;
    }
}
