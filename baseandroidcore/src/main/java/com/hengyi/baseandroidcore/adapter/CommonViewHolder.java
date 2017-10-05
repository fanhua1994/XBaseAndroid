package com.hengyi.baseandroidcore.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengyi.baseandroidcore.utils.GlideImageUtils;


/**
 * Created by 繁华 on 2017/5/15.
 */

public class CommonViewHolder {
    private Context context;
    private View view ;
    private SparseArray<View> mViews;

    //初始化
    public CommonViewHolder(Context context,int layout_id){
        this.context = context;
        mViews = new SparseArray<View>();
        view = LayoutInflater.from(context).inflate(layout_id,null);
        view.setTag(this);
    }

    //获取视图
    public View getView(){
        return view;
    }

    //获取
    public static CommonViewHolder get(Context context,View mView,int layout_id){
        if(mView == null){
            return new CommonViewHolder(context,layout_id);
        }else{
            return (CommonViewHolder) mView.getTag();
        }

    }


    //设置文本
    public void setText(int layout_id,String text){
        View mView = mViews.get(layout_id);
        if(mView == null) {
            mView = view.findViewById(layout_id);
            mViews.put(layout_id,mView);
        }
        TextView mTextView = (TextView) mView;
        mTextView.setText(text);
    }

    //设置图片
    public void setImage(int layout_id,String image_url){
        View mView = mViews.get(layout_id);
        if(mView == null) {
            mView = view.findViewById(layout_id);
            mViews.put(layout_id,mView);
        }
        ImageView mImageView = (ImageView) mView;
        GlideImageUtils.showImageView(context,image_url,mImageView);
    }

    //设置圆角图片
    public void setCircleImage(int layout_id,String image_url){
        View mView = mViews.get(layout_id);
        if(mView == null) {
            mView = view.findViewById(layout_id);
            mViews.put(layout_id,mView);
        }
        ImageView mImageView = (ImageView) mView;
        GlideImageUtils.showImageViewToCircle(context,image_url,mImageView);
    }
}
