package com.xy.music.service;

import com.xy.music.AppConstants;
import com.xy.music.R;
import com.xy.music.io.socket.client.Ack;
import com.xy.music.io.socket.client.IO;
import com.xy.music.io.socket.client.IO.Options;
import com.xy.music.io.socket.client.Socket;
import com.xy.music.utils.LogUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import io.socket.emitter.Emitter.Listener;
/**
 * 长连接服务
 * 
 * @author xy 2016 12 01
 */
public class SocketService extends Service{
	private static final String TAG = SocketService.class.getSimpleName();
	private Socket mSocket;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		initSocket();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogUtil.i(TAG, "@@@@@ SocketService onStartCommand @@@@@");
		if(mSocket != null && !mSocket.connected()) {
			mSocket.connect();
		}
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mSocket != null && mSocket.connected()) {
			mSocket.disconnect();
			mSocket.close();
		}
	}

	/**
	 * 初始化socket长连接
	 */
	private void initSocket() {
		LogUtil.i(TAG, "@@@@@ on socket init @@@@@");
		try {
			Options opt = new Options();
			opt.forceNew = true;
			opt.reconnection = true;
			opt.reconnectionAttempts = 3;
			opt.reconnectionDelay = 5000;
			opt.reconnectionDelayMax = 10000;
			mSocket = IO.socket(AppConstants.SERVER_SOCKET_ADDRESS, opt);
			mSocket.on("message", new Listener() { // 接收消息

				@Override
				public void call(Object... arg0) {
					// 握手
					Ack ack = (Ack) arg0[arg0.length - 1];
					ack.call();
					String msgId = arg0[0].toString(); // 消息id
					String title = arg0[1].toString(); // 消息标题
					String msgContent = arg0[2].toString(); // 消息内容
					sendNotify(SocketService.this, title, msgContent, null);
					LogUtil.i(TAG, "@@@@@ on socket message @@@@@ " + msgContent);
				}
			});
//			mSocket.on("message", new Listener() { // 接收消息
//
//				@Override
//				public void call(Object... arg0) {
//					// 握手
//					Ack ack = (Ack) arg0[arg0.length - 1];
//					ack.call();
//					String msgId = arg0[0].toString(); // 消息id
//					String title = arg0[1].toString(); // 消息标题
//					String msgContent = arg0[2].toString(); // 消息内容
//					sendNotify(SocketService.this, title, msgContent, null);
//					LogUtil.i(TAG, "@@@@@ on socket message @@@@@ " + msgContent);
//				}
//			}).on(Socket.EVENT_CONNECT, new Listener() { // 连接回调
//
//				@Override
//				public void call(Object... arg0) {
//					LogUtil.i(TAG, "@@@@@ on socket connect @@@@@");
//				}
//			}).on(Socket.EVENT_CONNECT_ERROR, new Listener() { // 连接错误回调
//
//				@Override
//				public void call(Object... arg0) {
//					LogUtil.i(TAG, "@@@@@ on socket connect error @@@@@");
//				}
//			}).on(Socket.EVENT_CONNECT_TIMEOUT, new Listener() { // 连接超时回调
//
//				@Override
//				public void call(Object... arg0) {
//					LogUtil.i(TAG, "@@@@@ on socket connect timeout @@@@@");
//				}
//			}).on(Socket.EVENT_DISCONNECT, new Listener() { // 断开连接回调
//
//				@Override
//				public void call(Object... arg0) {
//					LogUtil.i(TAG, "@@@@@ on socket disconnect @@@@@");
//				}
//			}).on(Socket.EVENT_RECONNECT, new Listener() { // 重新连接回调
//
//				@Override
//				public void call(Object... arg0) {
//					LogUtil.i(TAG, "@@@@@ on socket reconnect @@@@@");
//				}
//			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param ctx
	 * @param title 标题
	 * @param content 内容
	 * @param jumpIntent 跳转的目标intent
	 */
	public static void sendNotify(Context ctx, String title, String content, Intent jumpIntent) {
		NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		int id = (int) System.currentTimeMillis();
		PendingIntent pi = PendingIntent.getActivity(ctx, id, jumpIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		Notification.Builder builder = new Notification.Builder(ctx);
		builder.setSmallIcon(R.drawable.ic_launcher);
		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			builder.setColor(0xFF39B44A);
		}
		builder.setContentTitle(title);
		builder.setContentText(content);
		builder.setDefaults(Notification.DEFAULT_SOUND);
		builder.setAutoCancel(true);
		builder.setContentIntent(pi);
		builder.build();
	}
}
