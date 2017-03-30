/**
 * 功能描述：Toast工具类,防止Toast重复刷新,支持自定义Toast
 *
 */
package com.xy.music.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	
	public static Toast mToast;

    public static void showToast(Context context, String text) {
        if (mToast == null) {
        	mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
        	mToast.setText(text);
        	mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showToast(Context context, int StringResId) {
        if (mToast == null) {
            if (context != null) {
            	mToast = Toast.makeText(context, StringResId, Toast.LENGTH_SHORT);
            } else {
                return;
            }
        } else {
        	mToast.setText(StringResId);
        	mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void cancelToast() {
        if (mToast != null) {
        	mToast.cancel();
        }
    }

}