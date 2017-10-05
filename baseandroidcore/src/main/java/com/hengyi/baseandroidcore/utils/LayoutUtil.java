package com.hengyi.baseandroidcore.utils;

import android.view.SurfaceView;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/9/14.
 */

public class LayoutUtil {

    public static void getLayout(SurfaceView surfaceView,int width,int height){
        ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
        lp.height = height;
        lp.width = width;
        surfaceView.setLayoutParams(lp);
    }
}
