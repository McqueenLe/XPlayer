package com.xy.music;

import java.io.File;

import com.xy.music.service.ServiceManager;

import android.app.Application;
import android.os.Environment;

public class AppManager extends Application {
	
	public static boolean mIsSleepClockSetting = false;
	public static ServiceManager mServiceManager = null;
	private static String rootPath = "/mymusic";
	public static String lrcPath = "/lrc";
	
	@Override
	public void onCreate() {
		super.onCreate();
		mServiceManager = new ServiceManager(this);
		initPath();
	}
	
	/**
	 * 初始化缓存目录
	 */
	private void initPath() {
		String ROOT = "";
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			ROOT = Environment.getExternalStorageDirectory().getPath();
		}
		rootPath = ROOT + rootPath; // app根目录
		lrcPath = rootPath + lrcPath; // app歌词目录
		File lrcFile = new File(lrcPath);
		if(lrcFile.exists()) {
			lrcFile.mkdirs();
		}
	}
}
