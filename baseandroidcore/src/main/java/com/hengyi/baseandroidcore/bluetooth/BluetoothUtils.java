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
    public final UUID MY_UUID = UUID.fromString("db764ac8-4b08-7f25-aafe-59d03c27bae3");
    private static BluetoothUtils instance = null;
    private BluetoothAdapter bluetoothAdapter = null;
    private boolean supportBluetooth = false;
    private static Context context;
    private List<BluetoothDevice> scanBluetooth = null;

    private boolean bluetoothServerSwitch = false;
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

    public void startBluetoothServer(){
        bluetoothServerSwitch = true;
        AcceptThread acceptThread = new AcceptThread();
        acceptThread.start();
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

    public boolean connection(BluetoothDevice bluetoothDevice){
        connBluetoothDevice = bluetoothDevice;
        try {
            connBluetoothSocket = connBluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
            connBluetoothSocket.connect();
            outputStream = connBluetoothSocket.getOutputStream();
            if(outputStream!= null){
                outputStream.write("hello".getBytes());
                outputStream.close();
            }
            connBluetoothSocket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    // 创建handler，因为我们接收是采用线程来接收的，在线程中无法操作UI，所以需要handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    // 服务端接收信息线程
    private class AcceptThread extends Thread {
        private BluetoothServerSocket serverSocket;// 服务端接口
        private BluetoothSocket socket;// 获取到客户端的接口
        private InputStream is;// 获取到输入流
        private OutputStream os;// 获取到输出流

        public AcceptThread() {
            try {
                // 通过UUID监听请求，然后获取到对应的服务端接口
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord("Sulwhasoo", MY_UUID);
            } catch (Exception e) {
            }
        }

        public void run() {
            try {
                // 接收其客户端的接口
                socket = serverSocket.accept();
                // 获取到输入流
                is = socket.getInputStream();
                // 获取到输出流
                os = socket.getOutputStream();

                // 无线循环来接收数据
                while (bluetoothServerSwitch) {
                    // 创建一个128字节的缓冲
                    byte[] buffer = new byte[128];
                    // 每次读取128字节，并保存其读取的角标
                    int count = is.read(buffer);
                    // 创建Message类，向handler发送数据
                    Message msg = new Message();
                    // 发送一个String的数据，让他向上转型为obj类型
                    msg.obj = new String(buffer, 0, count, "utf-8");
                    // 发送数据
                    handler.sendMessage(msg);
                }

                if(os != null)
                    os.close();
                if(is != null)
                    is.close();
                if(socket != null && socket.isConnected()){
                    socket.close();
                }

                if(serverSocket != null)
                    serverSocket.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
    }

}
