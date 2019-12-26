package com.hengyi.baseandroidcore.adapter.listview;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengyi.baseandroidcore.utils.GlideUtils;


/**
 * Created by 繁华 on 2017/5/15.
 */

public class ViewHolder {
    private Context context;
    private View view ;
    private SparseArray<View> mViews;

    //初始化
    public ViewHolder(Context context, int layout_id){
        this.context = context;
        mViews = new SparseArray<>();
        view = LayoutInflater.from(context).inflate(layout_id,null);
        view.setTag(this);
    }

    //获取视图
    public View getView(){
        return view;
    }

    //获取
    public static ViewHolder get(Context context, View mView, int layout_id){
        if(mView == null){
            return new ViewHolder(context,layout_id);
        }else{
            return (ViewHolder) mView.getTag();
        }

    }

    //设置文本
    public void setText(int layout_id,String text){
        setText(layout_id,text,null);
    }

    //设置文本
    public void setText(int layout_id,String text,View.OnClickListener listener){
        View mView = mViews.get(layout_id);
        if(mView == null) {
            mView = view.findViewById(layout_id);
            mViews.put(layout_id,mView);
        }
        TextView mTextView = (TextView) mView;
        if(listener != null)
            mTextView.setOnClickListener(listener);
        mTextView.setText(text);
    }

    public void setImage(int layout_id,String imageUrl){
        setImage(layout_id,imageUrl,null);
    }

    //设置图片
    public void setImage(int layoutId,String imageUrl,View.OnClickListener listener){
        View mView = mViews.get(layoutId);
        if(mView == null) {
            mView = view.findViewById(layoutId);
            mViews.put(layoutId,mView);
        }
        ImageView mImageView = (ImageView) mView;
        if(listener != null)
            mImageView.setOnClickListener(listener);
        GlideUtils.loadImage(context,imageUrl,mImageView);
    }


    /***
     * 设置原图
     * @param layout_id
     * @param image_url
     */
    public void setCircleImage(int layout_id,String image_url){
        setCircleImage(layout_id,image_url,null);
    }
    //设置圆角图片
    public void setCircleImage(int layout_id,String image_url,View.OnClickListener listener){
        View mView = mViews.get(layout_id);
        if(mView == null) {
            mView = view.findViewById(layout_id);
            mViews.put(layout_id,mView);
        }
        ImageView mImageView = (ImageView) mView;
        if(listener != null)
            mImageView.setOnClickListener(listener);
        GlideUtils.loadRoundCircleImage(context,image_url,mImageView);
    }

    /**
     * 获取通用的视图
     * @param layout_id
     * @return
     */
    public View getView(int layout_id){
        View mView = mViews.get(layout_id);
        if(mView == null) {
            mView = view.findViewById(layout_id);
            mViews.put(layout_id,mView);
        }
        return mView;
    }

    /**
     * 获取视图
     * @param layout_id
     * @param type
     * @param <T>
     * @return
     */
    public <T> T getView(int layout_id,Class<T> type){
        View mView = mViews.get(layout_id);
        if(mView == null) {
            mView = view.findViewById(layout_id);
            mViews.put(layout_id,mView);
        }
        T res = (T) mView;
        return res;
    }

    /**
     * 点击事件
     * @param layout_id
     * @param listener
     */
    public void setViewListener(int layout_id,View.OnClickListener listener){
        View view = getView(layout_id);
        if(listener != null){
            view.setOnClickListener(listener);
        }
    }

    /**
     * 长按
     * @param layout_id
     * @param listener
     */
    public void setViewLongListener(int layout_id, View.OnLongClickListener listener){
        View view = getView(layout_id);
        if(listener != null){
            view.setOnLongClickListener(listener);
        }
    }
}
