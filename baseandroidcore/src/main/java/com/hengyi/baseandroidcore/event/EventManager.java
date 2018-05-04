package com.hengyi.baseandroidcore.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Fanhua on 2017/11/28.
 */

public class EventManager {
    private static EventMessage eventMessage = null;

    public static void sendMessage(EventMessage messageEvent){
        EventBus.getDefault().post(messageEvent);
    }

    public static void register(Object subscriber){
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber){
        EventBus.getDefault().unregister(subscriber);
    }

    public static EventMessage getMessage(){
        if(eventMessage == null)
            eventMessage = new EventMessage();
        return eventMessage;
    }
}
