package com.hengyi.baseandroidcore.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Fanhua on 2017/11/28.
 */

public class EventManager {
    private static DefaultMessageEvent defaultMessageEvent = null;

    public static void sendMessage(DefaultMessageEvent defaultMessageEvent){
        EventBus.getDefault().post(defaultMessageEvent);
    }

    public static void register(Object subscriber){
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber){
        EventBus.getDefault().unregister(subscriber);
    }

    public static DefaultMessageEvent getDefaultMessage(){
        if(defaultMessageEvent == null)
            defaultMessageEvent = new DefaultMessageEvent();
        return defaultMessageEvent;
    }
}
