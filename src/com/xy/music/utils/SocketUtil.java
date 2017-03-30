package com.xy.music.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.xy.music.service.SocketService;
/**
 * 功能描述：长链接工具类
 *
 *@author xy 2016 12 02
 */
public class SocketUtil {

	/**
	 * 定时启动长连接服务
	 */
	public static void startSocketService(Context context) {
        LogUtil.i("SocketService", "@@@@@ open socket service @@@@@");
        Intent intent = new Intent(context, SocketService.class);  
        context.startService(intent);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);    
        long firstime = System.currentTimeMillis();  
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // 2分钟一个周期，不停的发送广播    
        am.cancel(pi);
        am.setRepeating(AlarmManager.RTC_WAKEUP, firstime, 1 * 60 * 1000, pi);    
	}
	
   /**
    * 结束掉闹钟服务，停止长链接
    * @param context
    */
   public static void stopSocketService(Context context) {
        Intent intent = new Intent(context, SocketService.class);  
        PendingIntent pi = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);    
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
        context.stopService(intent);
	}
}
