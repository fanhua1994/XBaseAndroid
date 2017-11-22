package com.hengyi.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.hengyi.baseandroidcore.adapter.CommonAdapter;
import com.hengyi.baseandroidcore.adapter.CommonViewHolder;
import com.hengyi.baseandroiddemo.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class BluetoothsAdapter extends CommonAdapter<BluetoothDevice> {

    public BluetoothsAdapter(Context context, List<BluetoothDevice> data, int layout_id) {
        super(context, data, layout_id);
    }

    private String getStatus(int status){
        if(status == BluetoothDevice.BOND_BONDED){
            return "已配对";
        }else if(status == BluetoothDevice.BOND_BONDING){
            return "配对中";
        }else{
            return "未配对";
        }
    }

    @Override
    public void ViewHolder(CommonViewHolder holder, int position) {
        BluetoothDevice bluetoothDevice = getItem(position);
        holder.setText(R.id.name,bluetoothDevice.getName());
        holder.setText(R.id.address,bluetoothDevice.getAddress());
        holder.setText(R.id.status,getStatus(bluetoothDevice.getBondState()));
    }
}
