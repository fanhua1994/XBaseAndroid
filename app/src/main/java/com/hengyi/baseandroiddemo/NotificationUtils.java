package com.hengyi.baseandroiddemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationUtils {
	private Context context;
	private NotificationManager mNotificationManager;
	private NotificationCompat.Builder mBuilder;
	private Notification mNotification;
	private int notifyId = 0;

	public NotificationUtils(Context context) {
		mNotificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
		this.context = context;
	}
	
	/** 初始化通知栏 */
	public void createProgressNotify(int icon,int notify_id,String tickerText,String title,String content,Intent intent) {
		notifyId = notify_id;
		mBuilder = new NotificationCompat.Builder(context,"default");
		mBuilder.setSound(null);
		mBuilder.setVibrate(null);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, notify_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder builder = mBuilder.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setContentIntent(pendingIntent)
				.setPriority(Notification.PRIORITY_DEFAULT)
				.setOngoing(false)
				.setTicker(tickerText)
				.setSmallIcon(icon)
				.setContentTitle(title)
				.setTicker(content);// 通知首次出现在通知栏，带上升动画效果的;
	}

	
	/** 显示带进度条通知栏 */
	public void showProgressNotify(int progress,String content) {
		//确定进度的
		mBuilder.setProgress(100, progress, false); // 这个方法是显示进度条 
		mBuilder.setContentText(content);
		mNotification = mBuilder.build();
		mNotification.defaults|=Notification.FLAG_AUTO_CANCEL;
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		mNotificationManager.notify(notifyId, mNotification);
	}


	/**
	 *
	 * @param icon
	 * @param tickerText
	 * @param title
	 * @param content
	 * @param intent
	 * @param notify_id
	 */
	public void createNotify(int icon, String tickerText, String title, String content, Intent intent, int notify_id) {
		notifyId = notify_id;
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setAction(Intent.ACTION_MAIN);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, notify_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		mBuilder = new NotificationCompat.Builder(context,"default");
		mBuilder.setContentTitle(title)
				.setContentText(content)
				.setContentIntent(pendingIntent)
				.setTicker(tickerText)
				.setWhen(System.currentTimeMillis())
				.setPriority(Notification.PRIORITY_DEFAULT)
				.setOngoing(false)
				.setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_SOUND)
				.setSmallIcon(icon);

		mNotification = mBuilder.build();

		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;

		mNotificationManager.notify(notify_id, mNotification);

	}

	public void cancelNotify(){
		mNotificationManager.cancel(notifyId);
	}
}