/**
 * 功能描述：捕获全局异常
 *
 */
package com.xy.music.utils;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;

public class CrashUtil implements UncaughtExceptionHandler {

    private static CrashUtil instance; // 单例引用，这里我们做成单例的，因为我们一个应用程序里面只需要一个UncaughtExceptionHandler实例

    private CrashUtil() {
    	
    }

    /**
     * 同步方法，以免单例多线程环境下出现异常
     * 
     * @return
     */
    public synchronized static CrashUtil getInstance() {
        if (instance == null) {
            instance = new CrashUtil();
        }
        return instance;
    }

    /**
     * 初始化，把当前对象设置成UncaughtExceptionHandler处理器
     * 
     * @param ctx
     */
    public void init(Context ctx) {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当有未处理的异常发生时，就会来到这里
     */
    @Override
    public void uncaughtException(Thread thread, Throwable e) {
    	LogUtil.writeToFile(e);
    }

}