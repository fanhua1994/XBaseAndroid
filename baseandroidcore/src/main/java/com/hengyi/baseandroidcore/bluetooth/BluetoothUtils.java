package com.hengyi.baseandroidcore.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hengyi.baseandroidcore.utilscode.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Administrator on 2017/11/22.
 * 如何区分什么蓝牙服务器和客户端
 * 这个是相对的概念，其实手机之间的低位是平等的。
 * 我主动向你请求连接的话，你就是服务器，我是客户端。
 * 反之，你主动向我请求连接的话，你就是客户端了。
 * 串口通信服务：00001101-0000-1000-8000-00805F9B34FB
 * 信息同步服务：00001104-0000-1000-8000-00805F9B34FB
 * 文件传输服务：00001106-0000-1000-8000-00805F9B34FB
 */
public class BluetoothUtils {
    public enum CONNECION{
        NONE,
        CLIENT,
        SERVER
    }
    // UUID，蓝牙建立链接需要的
    public UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothUtils instance = null;
    private BluetoothAdapter bluetoothAdapter = null;
    private boolean supportBluetooth = false;
    private static Context context;
    private List<BluetoothDevice> scanBluetooth = null;
    private CONNECION connecionType = CONNECION.NONE;
    private boolean listenerCallback = true;

    //服务器
    private BluetoothServerSocket bluetoothServerSocket;
    private boolean serverThreadSwitch = false;//用于服务器的操作
    private boolean readThreadSwitch = false;//用户读取消息的线程
    public BluetoothServerConnListener serverListener = null;

    //公用
    private BluetoothSocket bluetoothSocket;
    private OutputStream blueOutputStream;
    private InputStream blueInputStream;

    //客户端
    private BluetoothDevice clientBluetoothDevice;
    private BluetoothClientConnListener clientListener = null;

    public synchronized static BluetoothUtils getInstance(Context con){
        synchronized (BluetoothUtils.class){
            context = con;
            if(instance == null) {
                instance = new BluetoothUtils();
            }
            return instance;
        }
    }

    public void init(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null)
            supportBluetooth = false;
        else
            supportBluetooth = true;
    }

    public void init(String uuid){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null)
            supportBluetooth = false;
        else
            supportBluetooth = true;
        MY_UUID = UUID.fromString(uuid);
    }


    /**
     * 发送一个蓝牙的消息
     * @param buffer
     * @return
     */
    public boolean sendBluetoothMessage(byte[] buffer){
        try{
            blueOutputStream.write(buffer);
            blueOutputStream.flush();
            return true;
        }catch(Exception e) {
            return false;
        }
    }


    private void sendMessage(int what,Object obj,int arg1,int arg2){
        if(listenerCallback) {
            Message msg = handler.obtainMessage();
            msg.what = what;
            msg.obj = obj;
            msg.arg1 = arg1;
            msg.arg2 = arg2;
            handler.sendMessage(msg);
        }
    }

    private void sendBundleMessage(int what, Object obj, Bundle bundle){
        if(listenerCallback) {
            Message msg = handler.obtainMessage();
            msg.what = what;
            msg.obj = obj;
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    }

    /**
     * 是否支持蓝牙
     * @return
     */
    public boolean isSupportBluetooth(){
        return supportBluetooth;
    }

    public void setBluetoothClientListener(BluetoothClientConnListener bluetoothListener){
        this.clientListener = bluetoothListener;
    }

    public void setBluetoothServerListener(BluetoothServerConnListener bluetoothServerListener){
        this.serverListener = bluetoothServerListener;
    }

    public BluetoothAdapter getBluetoothAdapter(){
        return bluetoothAdapter;
    }

    public boolean isEnabled(){
        return getBluetoothAdapter().isEnabled();
    }

    public String getAddress(){
        return getBluetoothAdapter().getAddress();
    }

    public String getName(){
        return getBluetoothAdapter().getName();
    }

    public boolean setName(String name){
        return getBluetoothAdapter().setName(name);
    }

    /**
     * 打开蓝牙  开启扫描
     */
    public void openBluetooth(){
        if(!getBluetoothAdapter().isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            context.startActivity(intent);
        }
    }

    /**
     * 关闭蓝牙
     */
    public void closeBluetooth(){
        if(getBluetoothAdapter().isEnabled()){
            getBluetoothAdapter().disable();
        }
    }

    public void openBluetoothServer(){
        if(bluetoothSocket != null){
            listenerCallback = false;
            close();//关闭连接
            listenerCallback = true;
        }
        ServerThread serverThread = new ServerThread();
        serverThread.start();
        connecionType = CONNECION.SERVER;
    }

    /**
     * 获取已经配对的连接设备
     * @return
     */
    public List<BluetoothDevice> getPairConnList(){
        Set<BluetoothDevice> devices = getBluetoothAdapter().getBondedDevices();
        List<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();
        for(int i=0; i<devices.size(); i++) {
            BluetoothDevice device = devices.iterator().next();
            deviceList.add(device);
        }
        return deviceList;
    }

    /**
     * 开始扫描
     */
    public void startDiscovery(){
        if(scanBluetooth == null)
            scanBluetooth = new ArrayList<>();
        scanBluetooth.clear();
        if(getBluetoothAdapter().isDiscovering())
            cancelDiscovery();
        getBluetoothAdapter().startDiscovery();
    }

    /**
     * 添加扫描到的驱动
     * @param bluetoothDevice
     */
    public void addScanDevice(BluetoothDevice bluetoothDevice){
        if(!scanBluetooth.contains(bluetoothDevice))
            scanBluetooth.add(bluetoothDevice);
    }

    /**
     * 获取扫描列表
     * @return
     */
    public List<BluetoothDevice> getScanBluetoothList(){
        if(scanBluetooth == null)
            scanBluetooth = new ArrayList<>();
        return scanBluetooth;
    }

    /**
     * 清空搜索列表
     */
    public void clearScanBluetoothList(){
        if(scanBluetooth != null)
            scanBluetooth.clear();
    }

    /**
     * 取消搜索
     */
    public void cancelDiscovery(){
        getBluetoothAdapter().cancelDiscovery();
    }

    /**
     * 创建配对
     * @param bluetoothDevice
     * @return
     */
    public boolean createBond(BluetoothDevice bluetoothDevice){
        try{
            Method creMethod = BluetoothDevice.class.getMethod("createBond");
            return (boolean) creMethod.invoke(bluetoothDevice);
        }catch(Exception e){
            return false;
        }
    }

    /**
     * 取消配对
     * @param bluetoothDevice
     * @return
     */
    public boolean cancelBond(BluetoothDevice bluetoothDevice){
        try{
            Method creMethod = BluetoothDevice.class.getMethod("removeBond");
            return (boolean) creMethod.invoke(bluetoothDevice);
        }catch(Exception e){
            return false;
        }
    }

    public void connection(BluetoothDevice bluetoothDevice){
        clientBluetoothDevice = bluetoothDevice;
        if(bluetoothSocket != null){
            listenerCallback = false;
            close();//关闭连接
            listenerCallback = true;
        }
        new Thread(){
            public void run(){
                try {
                    bluetoothSocket = clientBluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
                    bluetoothSocket.connect();
                    blueOutputStream = bluetoothSocket.getOutputStream();
                    blueInputStream = bluetoothSocket.getInputStream();
                    connecionType = CONNECION.CLIENT;
                    sendMessage(BluetoothType.CLIENT_CONNECTION_BLUETOOTH_SUCCESS,null,0,0);
                } catch (IOException e) {
                    sendMessage(BluetoothType.CLIENT_CONNECTION_BLUETOOTH_ERROR,e.getMessage(),0,0);
                }
            }
        }.start();
    }

    public void close(){
        if(bluetoothSocket == null || !bluetoothSocket.isConnected()){
            sendMessage(BluetoothType.CLIENT_BLUETOOTH_CONNECTION_NOT_OPEN,"蓝牙未连接",0,0);
            return ;
        }
        try{
            if(blueOutputStream != null){
                blueOutputStream.flush();
                blueOutputStream.close();
                blueOutputStream = null;
            }
            if(blueInputStream != null){
                blueInputStream.close();
                blueInputStream = null;
            }

            clientBluetoothDevice = null;
            bluetoothSocket.close();
            bluetoothSocket = null;
            readThreadSwitch = false;
            serverThreadSwitch = false;//关闭一切通信线程
            sendMessage(BluetoothType.CLIENT_BLUETOOTH_CONNECTION_CLOSE_SUCCESS,"蓝牙连接关闭成功",0,0);

        }catch(Exception e){
            sendMessage( BluetoothType.CLIENT_BLUETOOTH_CONNECTION_CLOSE_ERROR,"蓝牙连接关闭失败",0,0);
            bluetoothSocket = null;
        }
    }

    //=======================ui线程回调============================
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case BluetoothType.CLIENT_CONNECTION_BLUETOOTH_SUCCESS:
                    if(clientListener != null)
                        clientListener.connSuccess();
                    break;

                case BluetoothType.CLIENT_CONNECTION_BLUETOOTH_ERROR:
                    if(clientListener != null) {
                        clientListener.connError(msg.obj.toString());
                    }
                    break;

                case BluetoothType.CLIENT_BLUETOOTH_CONNECTION_NOT_OPEN:
                    if(clientListener != null)
                        clientListener.connClose(false,msg.obj.toString());
                    break;

                case BluetoothType.CLIENT_BLUETOOTH_CONNECTION_CLOSE_SUCCESS:
                    if(clientListener != null)
                        clientListener.connClose(true,msg.obj.toString());
                    break;

                case BluetoothType.CLIENT_BLUETOOTH_CONNECTION_CLOSE_ERROR:
                    if(clientListener != null)
                        clientListener.connClose(false,msg.obj.toString());
                    break;

                case BluetoothType.SERVER_BLUETOOTH_SERVER_IS_OPEN:
                    if(serverListener != null)
                        serverListener.serverOpen();
                    break;

                case BluetoothType.SERVER_BLUETOOTH_CLIENT_CONNECTION_SUCCESS:
                    if(serverListener != null)
                        serverListener.connectionSuccess();
                    break;
                case BluetoothType.SERVER_BLUETOOTH_CLIENT_CONNECTION_CLOSE:
                    if(serverListener != null)
                        serverListener.connectionClose();
                    break;
                case BluetoothType.SERVER_BLUETOOTH_CLIENT_CONNECTION_ERROR:
                    if(serverListener != null)
                        serverListener.connError(msg.obj.toString());
                    break;
            }
        }
    };
    //===============服务器相关的服务======================
    //开启服务器
    private class ServerThread extends Thread {
        @Override
        public void run() {
            try {
                bluetoothServerSocket = getBluetoothAdapter().listenUsingRfcommWithServiceRecord(getBluetoothAdapter().getName(),MY_UUID);
                LogUtils.d("server", "wait cilent connect...");
               sendMessage(BluetoothType.SERVER_BLUETOOTH_SERVER_IS_OPEN,null,0,0);
                bluetoothSocket = bluetoothServerSocket.accept();
                blueInputStream = bluetoothSocket.getInputStream();
                blueOutputStream = bluetoothSocket.getOutputStream();
                LogUtils.d("server", "accept success !");
                sendMessage(BluetoothType.SERVER_BLUETOOTH_CLIENT_CONNECTION_SUCCESS,null,0,0);
            } catch (IOException e) {
                sendMessage(BluetoothType.SERVER_BLUETOOTH_CLIENT_CONNECTION_ERROR,e.getMessage(),0,0);
            }
        }
    };

    //开启消息接收
    private class ReadThread extends Thread{
        public void run(){
            try{
                //...
            }catch(Exception e){

            }
        }
    };

    //客户端回调接口
    public interface BluetoothClientConnListener{
        public void connSuccess();
        public void connError(String message);
        public void connClose(boolean status,String message);
    }

    public interface BluetoothServerConnListener{
        public void serverOpen();
        public void connectionSuccess();
        public void connError(String message);
        public void connectionClose();
    }
}
