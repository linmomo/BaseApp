package com.sixun.basework.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 双击退出程序
 * 用法：
 *	public void onBackPressed() {
 *		if(doubleClickExitDetector.onClick()){
 *			super.onBackPressed();
 *		}
 *	}
 * @author lin
 *
 */
public class DoubleClickExitDetector {
	public static final String DEFAULT_HINT_MESSAGE = "再按一次退出程序";
	public static final int DEFAULT_SPACE_TIME = 2000;
	private long spaceTime;
	private long lastClickTime;
	private String hintMessage;
	private Context context;

	public DoubleClickExitDetector(Context context) {
		this(context, DEFAULT_HINT_MESSAGE, DEFAULT_SPACE_TIME);
	}

	public DoubleClickExitDetector(Context context, String hintMessage) {
		this(context, hintMessage, DEFAULT_SPACE_TIME);
	}

	public DoubleClickExitDetector(Context context, String hintMessage,
			long space_time) {
		this.context = context;
		this.hintMessage = hintMessage;
		this.spaceTime = space_time;
	}

	public boolean onClick() {
		long currentTime = System.currentTimeMillis();
		boolean result = (currentTime - lastClickTime) < spaceTime;
		lastClickTime = currentTime;
		if (!result) {
			Toast.makeText(context, hintMessage, Toast.LENGTH_SHORT).show();
		}
		return result;
	}

	public void setSpaceTime(long spaceTime) {
		this.spaceTime = spaceTime;
	}

	public void setHintMessage(String hintMessage) {
	        this.hintMessage = hintMessage;
	    }
}
