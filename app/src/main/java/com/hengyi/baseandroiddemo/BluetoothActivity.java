package com.hengyi.baseandroiddemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hengyi.adapter.BluetoothsAdapter;
import com.hengyi.baseandroidcore.dialog.CustomAlertDialog;
import com.hengyi.baseandroidcore.utils.ActivityStack;
import com.hengyi.baseandroidcore.bluetooth.BluetoothUtils;
import com.hengyi.baseandroidcore.weight.EaseTitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;

/**
 * Created by Administrator on 2017/11/22.
 */

public class BluetoothActivity extends BaseActivity {
    @BindView(R.id.titleBar)EaseTitleBar titleBar;
    @BindView(R.id.listview)ListView listView;
    @BindView(R.id.scan)Button scan;
    private BluetoothsAdapter adapter;

    private BluetoothUtils bluetoothUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bluetoothUtils = BluetoothUtils.getInstance(this);
        bluetoothUtils.init();

        registerReceiver();

        bluetoothUtils.setBluetoothClientListener(new BluetoothUtils.BluetoothConnListener() {
            @Override
            public void serverOpen() {
                toast("服务器已经打开");
            }

            @Override
            public void serverClose() {
                toast("服务器关闭");
            }

            @Override
            public void connSuccess() {
                toast("连接成功");
            }

            @Override
            public void connError(String message) {
                toast("连接错误:" + message);
            }

            @Override
            public void connClose(boolean status, String message) {
                toast("关闭状态:" + status + " 结果: " + message);
            }

            @Override
            public void onReceive(byte[] data) {
                toast("接收到数据");
            }

            @Override
            public void onReceiveError(String message) {
                toast("读取线程关闭,连接断开");
            }
        });

        adapter = new BluetoothsAdapter(this,bluetoothUtils.getScanBluetoothList(),R.layout.adapter_bluetooth_item);
        listView.setAdapter(adapter);

        titleBar.setLeftLayoutClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ActivityStack.getInstance().popActivity();
            }
        });
    }

    private void registerReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//状态改变
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mBluetoothReceiver,filter);
    }

    @Override
    public int setContentView(){
        return R.layout.activity_bluetooth;
    }

    @OnClick({R.id.scan,R.id.close,R.id.openServer,R.id.open_bluetooth,R.id.close_bluetooth})
    public void onClicks(View view){
        switch(view.getId()){
            case R.id.scan:
                bluetoothUtils.clearScanBluetoothList();
                adapter.notifyDataSetChanged();
                scan.setText("正在搜索中...");
                scan.setEnabled(false);
                bluetoothUtils.startDiscovery();
                break;

            case R.id.close:
                 bluetoothUtils.close();
                break;

            case R.id.openServer:
                bluetoothUtils.openBluetoothServer();
                break;

            case R.id.open_bluetooth:
                bluetoothUtils.openBluetooth();
                break;

            case R.id.close_bluetooth:
                bluetoothUtils.closeBluetooth();
                break;
        }
    }


    //定义广播接收
    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                bluetoothUtils.addScanDevice(device);
                adapter.notifyDataSetChanged();
            }else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                scan.setText("搜索完成");
                scan.setEnabled(true);
            }else if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBluetoothReceiver);
    }

    @OnItemClick(R.id.listview)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final BluetoothDevice bluetoothDevice = adapter.getItem(position);
        bluetoothUtils.cancelDiscovery();
        if(bluetoothDevice.getBondState() == BluetoothDevice.BOND_NONE) {
            toast("该设备没有配对");
            boolean s = bluetoothUtils.createBond(bluetoothDevice);
            adapter.notifyDataSetChanged();
        }else{
            CustomAlertDialog dialog = new CustomAlertDialog(this).builder();
            dialog.setTitle("选择操作");
            dialog.setMsg("请选择您的操作");
            dialog.setNegativeButton("取消配对",new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    boolean res = bluetoothUtils.cancelBond(bluetoothDevice);
                    toast("取消配对结果:" + res);
                }
            });

            dialog.setPositiveButton("连接",new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    bluetoothUtils.connection(bluetoothDevice);
                }
            });
            dialog.show();

        }
    }
}
