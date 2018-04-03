package com.hengyi.baseandroidcore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/** 万能适配器
 * Created by 繁华 on 2017/5/15.
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    private Context context;
    public List<T> data;
    private int layout_id;

    public CommonAdapter(Context context,List<T> data,int layout_id){
        this.context = context;
        this.data = data;
        this.layout_id = layout_id;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        CommonViewHolder holder = CommonViewHolder.get(context,view,layout_id);
        onBindView(holder,i);
        return holder.getView();
    }

    public abstract void onBindView(CommonViewHolder holder,int position);
}
