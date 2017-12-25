package com.hengyi.baseandroidcore.utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/12/25.
 */

public class ColorUtils {

    public static String changeColor(Context context,int id){
        StringBuffer stringBuffer = new StringBuffer();
        int color = context.getResources().getColor(id);
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);

        stringBuffer.append("#");
        stringBuffer.append(Integer.toHexString(red));
        stringBuffer.append(Integer.toHexString(green));
        stringBuffer.append(Integer.toHexString(blue));
        return stringBuffer.toString();
    }
}
