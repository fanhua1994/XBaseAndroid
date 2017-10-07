package com.hengyi.baseandroidcore.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.hengyi.baseandroidcore.R;

public class NotifacationUtils {
	private Context context;
	public NotificationManager mNotificationManager;
	public NotificationCompat.Builder mBuilder;
	public Notification mNotification;
	public int notifyId = 102;
	
	@SuppressWarnings("static-access")
	public NotifacationUtils(Context context) {
		mNotificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
		this.context = context;
		initNotify();
	}
	
	/** 初始化通知栏 */
	@SuppressLint("InlinedApi")
	private void initNotify() {
		mBuilder = new NotificationCompat.Builder(context);
		NotificationCompat.Builder builder = mBuilder.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setContentIntent(getDefalutIntent(0))
				// .setNumber(number)//显示数量
				.setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
				// .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
				.setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
//				.setDefaults(Notification.DEFAULT_VIBRATE)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
				// requires VIBRATE permission
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("正在下载")
				.setTicker("应用更新");// 通知首次出现在通知栏，带上升动画效果的;
	}
	
	/**
	 * @获取默认的pendingIntent,为了防止2.3及以下版本报错
	 * @flags属性:  
	 * 在顶部常驻:Notification.FLAG_ONGOING_EVENT  
	 * 点击去除： Notification.FLAG_AUTO_CANCEL 
	 */
	public PendingIntent getDefalutIntent(int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, new Intent(), flags);
		return pendingIntent;
	}
	
	/** 显示带进度条通知栏 */
	public void showProgressNotify(int progress) {
		//确定进度的
		mBuilder.setProgress(100, progress, false); // 这个方法是显示进度条 
		mBuilder.setContentText("进度：" + progress + "%");
		mNotification = mBuilder.build();
		/*
		 * FLAG_ONGOING_EVENT 在顶部常驻，可以调用下面的清除方法去除  FLAG_AUTO_CANCEL
		 * 点击和清理可以去调cancel方法
		 */
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		mNotificationManager.notify(notifyId, mNotification);
	}
}