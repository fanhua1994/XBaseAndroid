package com.zhiweism.youerplatformparent.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by Administrator on 2017/12/5.
 */

public class ClipboardUtils {
    private static ClipboardManager.OnPrimaryClipChangedListener listener = null;
    private static ClipboardUtils instance = null;


    public static synchronized ClipboardUtils getInstance(){
        synchronized (ClipboardUtils.class){
            if(instance == null){
                instance = new ClipboardUtils();
            }
            return instance;
        }
    }


    public void copy(Context context, String text){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null, text);
        clipboard.setPrimaryClip(clipData);
    }


    public String paste(Context context){
        String result = null;
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboard.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            // 从数据集中获取（粘贴）第一条文本数据
            CharSequence text = clipData.getItemAt(0).getText();
            result = text.toString();
        }else{
            result = null;
        }

        return result;
    }

    public void setListener(Context context , ClipboardManager.OnPrimaryClipChangedListener m_listener){
        if(m_listener != null){
            listener = m_listener;
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.addPrimaryClipChangedListener(listener);
        }
    }

    public void removeListener(Context context){
        if(listener != null) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.removePrimaryClipChangedListener(listener);
        }
    }
}
