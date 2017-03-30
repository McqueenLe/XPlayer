/*
 * 功能描述：sharinfo工具类。
 *
 */
package com.xy.music.utils;

import java.util.ArrayList;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class SharedPreferencesInfo {
    public static final String SWIFI_PREFERENCES = "swifi_data"; // shareinfo 名称
    public static final String ACTIVATE = "activate"; // 程序是否第一次运行，未激活。需要显示APP的用户引导页，
    public static final String IS_LOGIIN = "login"; // 是否有成功登陆过。
    public static final String NEWS_GUIDE = "news_guide"; // 是否第一次进入新闻列表页面，如果是第一次，需要显示引导浮层。
    public static final String WIFI_GUIDE = "wifi_guide"; // 是否第一次进入wifi管理页面。如果是第一次，需要显示引导浮层。
    public static final String COLLECT_GUIDE = "collect_guide";	//是否第一次进入我的收藏页面.如果第一次,需要显示引导浮层.
    public static final String SHAKE_GUIDE = "shake_guide"; //是否显示摇一摇引导提示
    public static final String ACCOUNT = "swifi_account"; // 登陆成功后服务器返回的账户名
    public static final String PHONE_NUM = "swifi_phone_num"; // 登陆成功的手机号
//    public static final String USER_PSW = "swifi_psw"; // 登陆成功的MD5加密密码
    public static final String RSA_KEY = "public_rsa_key"; // 登陆成功服务器返回的rsa公钥
    public static final String CHANNELS = "channel_info"; // 新闻频道列表的Json 字符串
    public static final String SAVE_CHANNEL_FAIL = "save_channel_fail"; // 保存频道列表到服务器失败状态
    public static final String GET_CHANNEL_FAIL = "get_channel_fail"; // 从服务器获取频道列表失败状态
    public static final String IGNORE_VERSION = "ignoreVersion"; // 忽略升级的版本号
    public static final String SHOP_DB_USER_ID = "shop_user_id";
    public static final String IS_PUSHMESSAGE_RECEIVE = "is_pushmessage_receive";//是否接收推送消息
    public static final String PUSH_TAG_LIST = "push_tag_list";//存储从百度云服务器查询出的所有标签
    public static final String READED = "readed"; //已读内容标识
    public static final String COLLECT_LIST_NEWS = "collect_list_news";
    public static final String SUGGEST = "suggest";//意见反馈，一段文字三张图，","隔开
    public static final String FREE_WIFI_START_TIME = "free_wifi_start_time";//最后一次点击我要上网或者续时的系统时间
    public static final String FREE_WIFI_TIME = "free_wifi_time";//最后一次点击我要上网或者续时后,还剩余的可用上网时间
    public static final String SHOP_AUTH = "shop_auth";//判断是否可访问电商,三个参数：ret&time&user
    public static final String NEW_HOME = "new_home"; //首页发现按钮是否有小红点
//    public static final String NEW_FIND = "new_find"; //发现界面的设置按钮是否有小红点
//    public static final String NEW_SETTING = "new_setting"; //设置界面的意见反馈按钮是否有小红点
    public static final String NEW_SUGGEST = "new_suggest"; //意见反馈的列表按钮是否有小红点
    public static final String APP_USE_TIME = "app_use"; //应用打开次数
    public static final String MAC = "mac";//本机mac
    public static final String IS_VERSION_NEWEST = "is_version_newest"; //应用是否为最新版本
    
    /**
     * @Description: 保存String类型的字符串到shareinfo。
     * @param context
     * @param key 存储的名称
     * @param values   要存储的字符串
     * @return: void
     */
    public static void saveTagString(Context context, String key, String values) {
        SharedPreferences preferences = context.getSharedPreferences(
                SWIFI_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(key, values);
        editor.commit();
    }

    /**
     * @Description: 从shareinfo里面获取字符串。
     * @param context
     * @param key 要获取的key名称。
     * @return   
     * @return: String 得到的字符串。获取失败返回null。
     */
    public static String getTagString(Context context, String key) {
        return context.getSharedPreferences(SWIFI_PREFERENCES,
                Context.MODE_PRIVATE).getString(key, "");
    }
    
    /**
     * @Description: 保存long类型到shareinfo。
     * @param context
     * @param key 存储的名称
     * @param values   要存储long值
     * @return: void
     */
    public static void saveTagLong(Context context, String key, long values) {
        SharedPreferences preferences = context.getSharedPreferences(
                SWIFI_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putLong(key, values);
        editor.commit();
    }
    
    /**
     * @Description: 获取long数据
     * @param context
     * @param key 要获取的key
     * @return   
     * @return: long 返回获取到的整形值。获取失败返回0.
     */
    public static long getTagLong(Context context, String key) {
        return context.getSharedPreferences(SWIFI_PREFERENCES,
                Context.MODE_PRIVATE).getLong(key, 0l);
    }

    /**
     * @Description: 保存整形数据到shareinfo
     * @param context
     * @param key 要保存的key。
     * @param values   要保存的整形值
     * @return: void
     */
    public static void saveTagInt(Context context, String key, int values) {
        SharedPreferences preferences = context.getSharedPreferences(
                SWIFI_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(key, values);
        editor.commit();
    }

    /**
     * @Description: 获取整形数据
     * @param context
     * @param key 要获取的key
     * @return   
     * @return: int 返回获取到的整形值。获取失败返回0.
     */
    public static int getTagInt(Context context, String key) {
        return context.getSharedPreferences(SWIFI_PREFERENCES,
                Context.MODE_PRIVATE).getInt(key, 0);
    }

    /**
     * @Description: 保存布尔类型的数据到shareinfo
     * @param context
     * @param key 要保存的key。
     * @param values   要保存的布尔值。
     * @return: void
     */
    public static void saveTagBoolean(Context context, String key, boolean values) {
        SharedPreferences preferences = context.getSharedPreferences(
                SWIFI_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean(key, values);
        editor.commit();
    }

    /**
     * @Description: 从shareinfo获取布尔值。
     * @param context
     * @param key 要获取的key。
     * @return   
     * @return: boolean 获取到的布尔值。获取失败返回false。
     */
    public static boolean getTagBoolean(Context context, String key, boolean defaultValue) {
        return context.getSharedPreferences(SWIFI_PREFERENCES,
                Context.MODE_PRIVATE).getBoolean(key, defaultValue);
    }
    
    /**
     * @Description: 保存set<String>集合类型数据
     * @param context
     * @param key 要保存的key
     * @param value 要保存的值
     * @return   
     * @return: set<String> 返回获取到的set集合。获取失败返回null.
     */
	public static void setTagSet(Context context,String key,Set<String> values){
    	SharedPreferences preferences = context.getSharedPreferences(
                SWIFI_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putStringSet(key, values);
        editor.commit();
    }
    
    /**
     * @Description: 获取set<String>集合类型数据
     * @param context
     * @param key 要获取的key
     * @return   
     * @return: set<String> 返回获取到的set集合。获取失败返回null.
     */
	public static Set<String> getTagSet(Context context,String key){
    	return context.getSharedPreferences(SWIFI_PREFERENCES,
                Context.MODE_PRIVATE).getStringSet(key, null);
    }

	/**
     * @Description: 删除对应Key的数据
     * @param context
     * @param key 要删除的key
     * @return   
     * @return: void
     */
    public static void removeData(Context context,String key){
    	SharedPreferences preferences = context.getSharedPreferences(
                SWIFI_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }
    
}
