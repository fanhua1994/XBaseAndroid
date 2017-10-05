package com.hengyi.baseandroidcore.utils;

import android.hardware.Camera;
import android.os.Build;

/**
 * Created by Administrator on 2017/9/14.
 */

public class CameraUtil {

    private static boolean checkCameraFacing(final int facing) {
        if (getSdkVersion() < Build.VERSION_CODES.GINGERBREAD) {
            return false;
        }
        final int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i,info);
            if (facing == info.facing) {
                return true;
            }
        }
        return false;
    }

    /**
     * 打开相机
     * @param facing   1是前置 0 是后置
     * @return
     */
    public static Camera openCameraFacing(final int facing) {
        if (getSdkVersion() < Build.VERSION_CODES.GINGERBREAD) {
            return null;
        }

        final int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (facing == info.facing) {
                return Camera.open(i);
            }
        }
        return null;
    }

    /**
     * 是否有后置摄像头
     * @return
     */
    public static boolean hasBackFacingCamera() {
        final int CAMERA_FACING_BACK = 0;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    /**
     * 是否有前置摄像头
     * @return
     */
    public static boolean hasFrontFacingCamera() {
        final int CAMERA_FACING_BACK = Camera.CameraInfo.CAMERA_FACING_FRONT;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    /**
     * SDK版本
     * @return
     */
    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

}
