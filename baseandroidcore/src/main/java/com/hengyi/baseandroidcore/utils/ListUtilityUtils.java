package com.hengyi.baseandroidcore.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hengyi.baseandroidcore.R;

/**
 * Created by Jabez on 2016/6/22.
 */
public class ListUtilityUtils {
	/**
	 * 设置listview的空视图 当没有数据时默认显示当前视图。
	 * @param listview
	 * @param context
	 */
	public static void setListViewNoDataView(ListView listview,Context context,String show_text,int show_image){
		View view = listview.getEmptyView();
		if(view != null){
			listview.setEmptyView(null);
		}
		view = LayoutInflater.from(context).inflate(R.layout.layout_default_no_data_view, null);
		TextView textView = view.findViewById(R.id.textView);
		ImageView imageView = view.findViewById(R.id.imageView);
		textView.setText(show_text);
		imageView.setImageResource(show_image);
		((ViewGroup)listview.getParent()).addView(view);
		listview.setEmptyView(view);

	}
	
	public static void setGridViewNoDataView(GridView gridView,Context context,String show_text,int show_image){
		View view = gridView.getEmptyView();
		if(view != null){
			gridView.setEmptyView(null);
		}
		view = LayoutInflater.from(context).inflate(R.layout.layout_default_no_data_view, null);
		TextView textView = view.findViewById(R.id.textView);
		ImageView imageView = view.findViewById(R.id.imageView);
		textView.setText(show_text);
		imageView.setImageResource(show_image);
		((ViewGroup)gridView.getParent()).addView(view);
		gridView.setEmptyView(view);
	}
	
	
	  /**
	    * 动态设置ListView的高度
	    * @param listView
	    */
	    public static void setListViewHeightBasedOnChildren(ListView listView) {
	        if(listView == null) return;
	        ListAdapter listAdapter = listView.getAdapter();
	        if (listAdapter == null) {
	            // pre-condition
	            return;
	        }
	        int totalHeight = 0;
	        for (int i = 0; i < listAdapter.getCount(); i++) {
	            View listItem = listAdapter.getView(i, null, listView);
	            listItem.measure(0, 0);
	            totalHeight += listItem.getMeasuredHeight();
	        }
	        ViewGroup.LayoutParams params = listView.getLayoutParams();
	        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	        listView.setLayoutParams(params);
	    }
	
    public static void setListViewHeightBasedOnChildren(ListView listView,float itemHeight) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        
        float totalHeight = 0;
        int len = listAdapter.getCount();
        totalHeight = len * itemHeight;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = (int) (totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)));

        listView.setLayoutParams(params);
    }
    public static void setGridViewHeightBasedOnChildren(GridView gridView,float itemHeight){
    	//获取GridView对应的Adapter
        ListAdapter gridAdapter = gridView.getAdapter();
        if (gridAdapter == null) {
            return;
        }
        int row = gridAdapter.getCount() / 3;
        if(gridAdapter.getCount() == 0){
        	row = 0;
        }else if(gridAdapter.getCount()>0 && row == 0){
        	row = 1;
        }else if(gridAdapter.getCount() > 3*row){
        	row++;
        }
        float totalHeight = 0;
        totalHeight = row * itemHeight;
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = (int)totalHeight;
        gridView.setLayoutParams(params);
    }
    public static void setGridViewHeightBasedOnChildren(GridView gridView,int columNum,float itemHeight){
    	//获取GridView对应的Adapter
        ListAdapter gridAdapter = gridView.getAdapter();
        if (gridAdapter == null) {
            return;
        }
        int row = gridAdapter.getCount() / columNum;
        if(gridAdapter.getCount() == 0){
        	row = 0;
        }else if(gridAdapter.getCount()>0 && row == 0){
        	row = 1;
        }else if(gridAdapter.getCount() > columNum*row){
        	row++;
        }
        float totalHeight = 0;
        totalHeight = row * itemHeight;
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = (int)totalHeight;
        gridView.setLayoutParams(params);
    }
}
