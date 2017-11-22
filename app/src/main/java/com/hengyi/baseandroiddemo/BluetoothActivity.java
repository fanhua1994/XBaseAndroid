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

        if(!bluetoothUtils.isEnabled()){
            bluetoothUtils.openBluetooth();
        }else{
            bluetoothUtils.setName("iPhone 7");
        }
        registerReceiver();


        bluetoothUtils.setBluetoothListener(new BluetoothUtils.BluetoothConnListener() {
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
                toast("关闭连接：" + message);
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
        IntentFilter filter=new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mBluetoothReceiver,filter);
    }

    @Override
    public int setContentView() {
        return R.layout.activity_bluetooth;
    }

    @OnClick({R.id.scan,R.id.close})
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
        }

    }


    //定义广播接收
    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                bluetoothUtils.addScanDevice(device);
                adapter.notifyDataSetChanged();
            }else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                scan.setText("搜索完成");
                scan.setEnabled(true);
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
        BluetoothDevice bluetoothDevice = adapter.getItem(position);
        if(bluetoothDevice.getBondState() == BluetoothDevice.BOND_NONE) {
            toast("该设备没有配对");
            boolean s = bluetoothUtils.createBond(bluetoothDevice);
            toast("配对结果:" + s);
            adapter.notifyDataSetChanged();
        }else{
            bluetoothUtils.cancelDiscovery();
            toast("建立连接.");
            bluetoothUtils.connection(bluetoothDevice);
        }
    }
}
