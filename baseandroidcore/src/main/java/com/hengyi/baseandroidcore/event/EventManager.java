package com.hengyi.baseandroidcore.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Fanhua on 2017/11/28.
 */

public class EventManager {
    private static MessageEvent messageEvent = null;

    public static void sendMessage(MessageEvent messageEvent){
        EventBus.getDefault().post(messageEvent);
    }

    public static void register(Object subscriber){
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber){
        EventBus.getDefault().unregister(subscriber);
    }

    public static MessageEvent getDefaultMessage(){
        if(messageEvent == null)
            messageEvent = new MessageEvent();
        return messageEvent;
    }
}
