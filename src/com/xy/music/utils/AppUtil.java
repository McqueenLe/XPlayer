/*
 * 功能描述：获取系统信息
 *
 */
package com.xy.music.utils;

import java.io.File;

import com.xy.music.AppConstants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

public class AppUtil {

    /**
     * 获取TelephonyManager
     * 
     * @param context
     * @return
     */
    public static TelephonyManager getTelephonyManager(Context context) {
        TelephonyManager telMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telMgr;
    }

    /**
     * 获取"渠道号"等在manifest内定义的meta值
     * 
     * @param context
     * @param key
     * @return
     */
    public static String getMetaData(Context context, String key) {
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取网络连接类型
     * 
     * @param context
     * @return
     */
    public static String getNetWorkType(Context context) {
        TelephonyManager telMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String nt = "";
        if (telMgr.getNetworkType() == TelephonyManager.NETWORK_TYPE_EDGE) {
            nt = "EDGE";
        } else if (telMgr.getNetworkType() == TelephonyManager.NETWORK_TYPE_GPRS) {
            nt = "GPRS";
        } else if (telMgr.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS) {
            nt = "UMTS";
        } else if (telMgr.getNetworkType() == 4) {
            nt = "HSDPA";
        } else {
        }
        return nt;
    }

    /**
     * 获取IMSI
     * 
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager telMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = "111111111111111";
        if(telMgr.getSubscriberId() != null && telMgr.getSubscriberId().length() != 0) {
            imsi = telMgr.getSubscriberId();
        }
        return imsi;
    }
    
    /**
     * 获取IMEI
     * 
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager telMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = "111111111111111";
        if(telMgr.getDeviceId() != null && telMgr.getDeviceId().length() != 0) {
            imei = telMgr.getDeviceId();
        }
        return imei;
    }

    /**
     * 获取运营商所在国家
     * 
     * @param context
     * @return
     */
    public static String getCountry(Context context) {
        TelephonyManager telMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String country = "";
        country = telMgr.getSimCountryIso();
        return country;
    }
    
    /**
     * 获取手机品牌
     * 
     * @param mContext
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获得VersionCode，返回值为版本号，例如：5
     * 
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "get versionCode Exception(RuntimeException)");
        }
    }

    /**
     * 获得versionName，返回值为版本名称，例如：1.3.1
     * 
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "get versionCode Exception(RuntimeException)");
        }
    }

    /**
     * 获取分辨率
     * @param context
     * @return
     */
    public static String getScreenResolution(Context context) {
    	String resolution;
    	DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    	((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
    	int W = mDisplayMetrics.widthPixels;
    	int H = mDisplayMetrics.heightPixels;
    	resolution = Integer.toString(W) + "*" + Integer.toString(H);
    	return resolution;
    }
    
    /**
     * 获取屏幕宽度
     * @param mContext
     * @return
     */
    public static int getScreenW(Activity activity) {
    	DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    	activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
    	int width = mDisplayMetrics.widthPixels;
    	return width;
    }
    
    /**
     * 获取屏幕高度
     * @param mContext
     * @return
     */
    public static int getScreenH(Activity activity) {
    	DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    	activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
    	int height = mDisplayMetrics.heightPixels;
    	return height;
    }

    /**
     * 获取 uuid
     * 
     * @param context
     * @return
     */
    public static String getUserUUID(Context context) {
        if (context == null)
            return null;
        String uuid = context.getSharedPreferences(
                AppConstants.SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
                .getString(AppConstants.SHARED_PREF_KEY_UUID, "");
        if ("".equals(uuid)) {
            uuid = java.util.UUID.randomUUID().toString();
            context.getSharedPreferences(AppConstants.SHARED_PREF_FILE_NAME,
                    Context.MODE_PRIVATE).edit()
                    .putString(AppConstants.SHARED_PREF_KEY_UUID, uuid)
                    .commit();
        }
        return uuid;
    }

    /**
     * 获取手机Mac地址
     * @param ctx
     * @return Mac地址
     */
    public static String getMac(Context ctx) {
    	String mac = SharedPreferencesInfo.getTagString(ctx, SharedPreferencesInfo.MAC);
    	if(!TextUtils.isEmpty(mac)) {
    		return mac;
    	} else {
	        WifiManager manager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
	        WifiInfo info = manager.getConnectionInfo();
	        return info.getMacAddress();
    	}
    }

    /***************************** 根据ip地址获取mac地址*****************************************************
	public static String getLocalMacAddressFromIp(Context context) {
		String mac_s = "";
		try {
			byte[] mac;
			NetworkInterface ne = NetworkInterface.getByInetAddress(InetAddress.getByName(getLocalIpAddress()));
			mac = ne.getHardwareAddress();
			mac_s = byte2hex(mac);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mac_s;
	}    
    
	public static String getLocalIpAddress() {
		try {
			for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = (NetworkInterface) en.nextElement();
				for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return null;
	}

	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer(b.length);
		String stmp = "";
		int len = b.length;
		for (int n = 0; n < len; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1) {
				hs = hs.append("0").append(stmp);
			} else {
				hs = hs.append(stmp);
			}
		}
		return String.valueOf(hs);
	}
	*****************************根据ip获取mac地址******************************************/
    
    /**
     * 安装apk
     */
    public static void installApk(Context ctx, File apkfile) {
        if (apkfile != null && apkfile.exists()) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                    "application/vnd.android.package-archive");
            ctx.startActivity(i);
        }
    }
    
    /**
     * dp转px
     * @param context
     * @param dipValue
     * @return
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    
    /**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param pxValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int px2sp(Context context, float pxValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
   
    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int sp2px(Context context, float spValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    }

    /**
     * 判断apk是否安装
     * @param context
     * @param packageName 包名
     * @return 
     */
	public static boolean isInstalled(Context context, String packageName, int verCode) {
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(packageName, 0);
//			if(verCode >= info.versionCode) {
				return true;
//			} else {
//				return false; //版本过旧,后期可修改为做升级操作
//			}
		} catch (NameNotFoundException e) {
			return false;
		}
	}
	
	/**
	 * 判断是否连接的wifi网络
	 * @param mContext
	 * @return
	 */
	public static boolean isConnectWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}
}
