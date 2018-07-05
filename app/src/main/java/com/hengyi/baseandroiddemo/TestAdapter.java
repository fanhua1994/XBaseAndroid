package com.hengyi.baseandroiddemo;

import android.content.Context;
import android.widget.TextView;

import com.hengyi.baseandroidcore.adapter.CommonAdapter;
import com.hengyi.baseandroidcore.adapter.CommonViewHolder;

import java.util.List;

/**
 * Created：2018/7/5
 * Time：20:55
 * Author：dongzp
 * Email：90fanhua@gmail.com
 * Project：XBaseAndroid
 * Use：
 */
public class TestAdapter extends CommonAdapter<String> {

    public TestAdapter(Context context, List<String> data, int layout_id) {
        super(context, data, layout_id);
    }

    @Override
    public void onBindView(CommonViewHolder holder, int position) {
        String item = getItem(position);
//        holder.setCircleImage(R.id.left_layout,"https://www.baidu.com/logo.png",null);
//        holder.setImage(R.id.left_layout,"https://www.baidu.com/logo.png",null);
//        holder.setText(R.id.left_layout,item,null);
//        TextView textView = holder.getView(R.id.design_bottom_sheet,TextView.class);
    }
}
