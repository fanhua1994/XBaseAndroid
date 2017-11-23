package com.hengyi.baseandroidcore.bluetooth;

/**
 * Created by Administrator on 2017/11/22.
 */

public class BluetoothType {

    //客户端相关
    public final static int CLIENT_CONNECTION_BLUETOOTH_SUCCESS = 1;//蓝牙连接成功
    public final static int CLIENT_CONNECTION_BLUETOOTH_ERROR= 2;//蓝牙连接错误
    public final static int CLIENT_BLUETOOTH_CONNECTION_NOT_OPEN = 3;//关闭蓝牙出现的蓝牙未连接
    public final static int CLIENT_BLUETOOTH_CONNECTION_CLOSE_SUCCESS = 4;//蓝牙关闭成gong
    public final static int CLIENT_BLUETOOTH_CONNECTION_CLOSE_ERROR = 5;//蓝牙关闭失败




    //服务器相关
    public final static int SERVER_BLUETOOTH_SERVER_IS_OPEN = 200;//蓝牙服务器连接成功
    public final static int SERVER_BLUETOOTH_CLIENT_CONNECTION_SUCCESS = 201;//有一个新的客户端连进来
    public final static int SERVER_BLUETOOTH_CLIENT_CONNECTION_CLOSE = 202;//客户端关闭连接
    public final static int SERVER_BLUETOOTH_CLIENT_CONNECTION_ERROR = 203;
}
