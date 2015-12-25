package com.sixun.basework.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import com.sixun.basework.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

/**
 * 常用工具类
 * 
 * @author 林
 * 
 * */
public class MyUtils {

	private static Context mApplicationContent;
	
	 /**
     * 不可实例化
     */
	private MyUtils() {
	   throw new Error("Do not need instantiate!");
	}

	/**
	 * 在application中初始化
	 */
	public static void initUtils(Application app) {
		mApplicationContent = app.getApplicationContext();
	}
	
	 /**
     * 调用系统发短信界面
     *
     * @param activity    Activity
     * @param phoneNumber 手机号码
     * @param smsContent  短信内容
     */
    public static void sendMessage(Context activity,String phoneNumber, String smsContent) {
        if (phoneNumber == null || phoneNumber.length() < 4) {
            return;
        }
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", smsContent);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(it);

    }

    /**
     * 调用系统打电话界面
     * 需要CALL_PHONE权限
     *
     * @param context     上下文
     * @param phoneNumber 手机号码
     */
    public static void callPhones(Context context,String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 1) {
            return;
        }
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    
    /**
     * 检测是否存在快捷方式
     *
     * @param activity Activity
     * @return 是否存在桌面图标
     */
    public static boolean hasShortcut(Activity activity) {
        boolean isInstallShortcut = false;
        final ContentResolver cr = activity.getContentResolver();
        final String AUTHORITY = "com.android.launcher.settings";
        final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
                + "/favorites?notify=true");
        Cursor c = cr.query(CONTENT_URI,
                new String[]{"title", "iconResource"}, "title=?",
                new String[]{activity.getString(R.string.app_name).trim()},
                null);
        if (c != null && c.getCount() > 0) {
            isInstallShortcut = true;
        }
        return isInstallShortcut;
    }

    /**
     * 为程序创建桌面快捷方式
     *
     * @param activity Activity
     */
    public static void addShortcut(Activity activity) {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                activity.getString(R.string.app_name));
        shortcut.putExtra("duplicate", false); // 不允许重复创建
        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.setClassName(activity, activity.getClass().getName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        // 快捷方式的图标
        ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
                activity, R.drawable.ic_launcher);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        activity.sendBroadcast(shortcut);
    }

    /**
     * 删除程序的快捷方式
     *
     * @param activity Activity
     */
    public static void delShortcut(Activity activity) {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                activity.getString(R.string.app_name));
        String appClass = activity.getPackageName() + "."
                + activity.getLocalClassName();
        ComponentName comp = new ComponentName(activity.getPackageName(),
                appClass);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(
                Intent.ACTION_MAIN).setComponent(comp));
        activity.sendBroadcast(shortcut);
    }

	/**
	 * 根据总的数据条数 跟后台每一页放回的条数算出总页数
	 * 
	 * @param total
	 *            后台放回的数据总条数
	 * */
	public static int getTPager(int total) {

		return total % 10 == 0 ? total / 10 : total / 10 + 1;
	}

	/**
	 * 从游标里获取String类型数据
	 * */
	public static String getSFromCursor(Cursor cursor, String key) {
		return cursor.getString(cursor.getColumnIndex(key));
	}

	/**
	 * 从游标里获取Int类型数据
	 * */
	public static int getIFromCursor(Cursor cursor, String key) {
		return cursor.getInt(cursor.getColumnIndex(key));
	}

	/**
	 * 从ViewHolder中获取View
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T getViewFromVH(View convertView, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			convertView.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = convertView.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}



	/**
	 * 复制文本到剪贴板
	 * 
	 * @param text
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void copyToClipboard(String text){
	 ClipboardManager cbm = (ClipboardManager)
	 mApplicationContent.getSystemService(Activity.CLIPBOARD_SERVICE);
	 cbm.setPrimaryClip(ClipData.newPlainText(mApplicationContent.getPackageName(), text));
	 }

	/**
	 * 获得一个HashMap对象
	 * */
	public static HashMap<String, String> getMap() {
		return new HashMap<String, String>();
	}

	/**
	 * 价格后如有小数点则不显示
	 * 
	 * @param num
	 * @return
	 */
	public static String doubleTrans(double num) {
		if (num % 1.0 == 0) {
			return String.valueOf((long) num);
		}
		return String.valueOf(num);
	}

	/**
	 * 经纬度测距
	 * 
	 * @param jingdu1
	 * @param weidu1
	 * @param jingdu2
	 * @param weidu2
	 * @return
	 */
	public static double distance(double jingdu1, double weidu1,
			double jingdu2, double weidu2) {
		double a, b, R;
		R = 6378137; // 地球半径
		weidu1 = weidu1 * Math.PI / 180.0;
		weidu2 = weidu2 * Math.PI / 180.0;
		a = weidu1 - weidu2;
		b = (jingdu1 - jingdu2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2* R* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(weidu1)* Math.cos(weidu2) * sb2 * sb2));
		return d;
	}


	/**
	 * 从Assets里读文本文件
	 * 
	 * @return
	 */
	public static String getStringFromAssets(String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(
					mApplicationContent.getResources().getAssets()
							.open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 读取资源文件Uri
	 * 
	 * @return
	 */
	public static Uri getUriFromRes(int id) {
		return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
				+ mApplicationContent.getResources().getResourcePackageName(id)
				+ "/"
				+ mApplicationContent.getResources().getResourceTypeName(id)
				+ "/"
				+ mApplicationContent.getResources().getResourceEntryName(id));
	}
	

}
