package com.sixun.basework.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 系统显示相关工具类
 * 
 * @author Administrator
 *
 */
public class DisplayUtils {

	private static Context mApplicationContent;

	/**
	 * 在application中初始化
	 */
	public static void initDisplayUtils(Application app) {
		mApplicationContent = app.getApplicationContext();
	}

	/**
	 * 不可实例化
	 */
	private DisplayUtils() {
		throw new Error("Do not need instantiate!");
	}

	/**
	 * 把dp转换成px
	 * */
	public static int Dp2Px(float dp) {
		final float scale = mApplicationContent.getResources()
				.getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * 把px转换成dp
	 * */
	public static int Px2Dp(float px) {
		final float scale = mApplicationContent.getResources()
				.getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 sp 的单位 转成为 px
	 *
	 * @param spValue
	 *            SP值
	 * @return 像素值
	 */
	public static int sp2px(float spValue) {
		float fontScale = mApplicationContent.getResources()
				.getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 获得屏幕宽度
	 * */
	public static int getScreenWidth() {
		DisplayMetrics dm = mApplicationContent.getResources()
				.getDisplayMetrics();
		return dm.widthPixels;
	}

	/**
	 * 获得屏幕高度
	 * */
	public static int getScreenHeight() {
		DisplayMetrics dm = mApplicationContent.getResources()
				.getDisplayMetrics();
		return dm.heightPixels - getStatusBarHeight();
	}

	/**
	 * 取屏幕高度包含状态栏高度
	 * 
	 * @return
	 */
	public static int getScreenHeightWithStatusBar() {
		DisplayMetrics dm = mApplicationContent.getResources()
				.getDisplayMetrics();
		return dm.heightPixels;
	}

	/**
	 * 取状态栏高度
	 * 
	 * @return
	 */
	public static int getStatusBarHeight() {
		int result = 0;
		int resourceId = mApplicationContent.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = mApplicationContent.getResources().getDimensionPixelSize(
					resourceId);
		}
		return result;
	}

	/**
	 * 获取dialog宽度
	 *
	 * @param activity
	 *            Activity
	 * @return Dialog宽度
	 */
	public static int getDialogW(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = activity.getResources().getDisplayMetrics();
		int w = dm.widthPixels - 100;
		// int w = aty.getWindowManager().getDefaultDisplay().getWidth() - 100;
		return w;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth();
		int height = getScreenHeight();
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth();
		int height = getScreenHeight();
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 关闭输入法
	 * 
	 * @param act
	 */
	// public static void closeInputMethod(Activity act) {
	// View view = act.getCurrentFocus();
	// if (view != null) {
	// ((InputMethodManager)
	// mApplicationContent.getSystemService(Context.INPUT_METHOD_SERVICE))
	// .hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
	// }
	// }

	/**
	 * 打卡软键盘
	 * 
	 * @param Activity
	 *            act
	 */
	public static void openKeybord(Activity act) {
		View view = act.getCurrentFocus();
		InputMethodManager imm = (InputMethodManager) mApplicationContent
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 * 
	 * @param Activity
	 *            act
	 */
	public static void closeKeybord(Activity act) {
		View view = act.getCurrentFocus();
		InputMethodManager imm = (InputMethodManager) mApplicationContent
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 切换键盘,如果键盘是可见的,那么隐藏它,如果它是不可见的，那么显示它
	 *
	 * @param context
	 *            上下文
	 */
	public static void toggleKeyboard() {
		InputMethodManager imm = (InputMethodManager) mApplicationContent
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {//是否打开
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 获取当前窗口的旋转角度
	 *
	 * @param activity
	 * @return
	 */
	public static int getDisplayRotation(Activity activity) {
		switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
		case Surface.ROTATION_0:
			return 0;
		case Surface.ROTATION_90:
			return 90;
		case Surface.ROTATION_180:
			return 180;
		case Surface.ROTATION_270:
			return 270;
		default:
			return 0;
		}
	}

	/**
	 * 当前是否是横屏
	 *
	 * @param context
	 * @return
	 */
	public static final boolean isLandscape(Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	/**
	 * 当前是否是竖屏
	 *
	 * @param context
	 * @return
	 */
	public static final boolean isPortrait(Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}
}
