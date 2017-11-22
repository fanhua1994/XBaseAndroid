package com.hengyi.baseandroidcore.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

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
 */
public class BluetoothUtils {
    // UUID，蓝牙建立链接需要的
    public final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothUtils instance = null;
    private BluetoothAdapter bluetoothAdapter = null;
    private boolean supportBluetooth = false;
    private static Context context;
    private List<BluetoothDevice> scanBluetooth = null;
    private BluetoothConnListener listener = null;

    // 选中发送数据的蓝牙设备，全局变量，否则连接在方法执行完就结束了
    private BluetoothDevice connBluetoothDevice;
    // 获取到选中设备的客户端串口，全局变量，否则连接在方法执行完就结束了
    private BluetoothSocket connBluetoothSocket;
    // 获取到向设备写的输出流，全局变量，否则连接在方法执行完就结束了
    private OutputStream outputStream;

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

    public void setBluetoothListener(BluetoothConnListener bluetoothListener){
        this.listener = bluetoothListener;
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

    public void addScanDevice(BluetoothDevice bluetoothDevice){
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
            Method creMethod = BluetoothDevice.class.getMethod("cancelBondProcess");
            return (boolean) creMethod.invoke(bluetoothDevice);
        }catch(Exception e){
            return false;
        }
    }

    public void connection(BluetoothDevice bluetoothDevice){
        connBluetoothDevice = bluetoothDevice;
        new Thread(){
            public void run(){
                try {
                    connBluetoothSocket = connBluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
                    connBluetoothSocket.connect();
                    outputStream = connBluetoothSocket.getOutputStream();
                    handler.sendEmptyMessage(BluetoothType.CONNECTION_BLUETOOTH_SUCCESS);
                } catch (IOException e) {
                    Message msg = new Message();
                    msg.what = BluetoothType.CONNECTION_BLUETOOTH_ERROR;
                    msg.obj = "连接错误:" + e.getMessage();
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    public void close(){
        if(connBluetoothSocket == null || !connBluetoothSocket.isConnected()){
            Message msg = new Message();
            msg.what = BluetoothType.BLUETOOTH_CONNECTION_NOT_OPEN;
            msg.obj = "蓝牙未连接";
            handler.sendMessage(msg);
            return ;
        }
        try{
            if(outputStream != null){
                outputStream.flush();
                outputStream.close();
                outputStream = null;
            }
            connBluetoothDevice = null;
            connBluetoothSocket.close();
            Message msg = new Message();
            msg.what = BluetoothType.BLUETOOTH_CONNECTION_CLOSE_SUCCESS;
            msg.obj = "蓝牙连接关闭成功";
            handler.sendMessage(msg);
        }catch(Exception e){
            Message msg = new Message();
            msg.what = BluetoothType.BLUETOOTH_CONNECTION_CLOSE_ERROR;
            msg.obj = "蓝牙连接关闭失败";
            handler.sendMessage(msg);
        }
    }

    //=======================ui线程回调============================
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case BluetoothType.CONNECTION_BLUETOOTH_SUCCESS:
                    if(listener != null)
                        listener.connSuccess();
                    break;

                case BluetoothType.CONNECTION_BLUETOOTH_ERROR:
                    if(listener != null) {
                        listener.connError(msg.obj.toString());
                    }
                    break;

                case BluetoothType.BLUETOOTH_CONNECTION_NOT_OPEN:
                    if(listener != null)
                        listener.connClose(false,msg.obj.toString());
                    break;

                case BluetoothType.BLUETOOTH_CONNECTION_CLOSE_SUCCESS:
                    if(listener != null)
                        listener.connClose(true,msg.obj.toString());
                    break;

                case BluetoothType.BLUETOOTH_CONNECTION_CLOSE_ERROR:
                    if(listener != null)
                        listener.connClose(false,msg.obj.toString());
                    break;




            }
        }
    };

    public interface BluetoothConnListener{
        public void connSuccess();
        public void connError(String message);
        public void connClose(boolean status,String message);
    }
}
