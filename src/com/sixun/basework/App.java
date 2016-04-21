package com.sixun.basework;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sixun.basework.Constans.Constans;
import com.sixun.basework.common.RebootThreadExceptionHandler;
import com.sixun.basework.imageloader.UilImageLoader;

import android.app.Application;
import android.content.Context;

/**
 * 全局application
 * @author Administrator
 *
 */
public class App extends Application {

	private static App instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		// 初始化uil
		UilImageLoader.initImageLoader(getApplicationContext());
		makeAppFolder();
		 // 异常处理
//        BaseCrashHandler handler = BaseCrashHandler.getInstance();
//        handler.init(this);

        // 程序异常关闭1s之后重新启动
//        new RebootThreadExceptionHandler(getBaseContext());
	}

	// 获取本appContext
	public static App getInstance() {
		return instance;
	}

	/**
	 * 生成app的根文件夹
	 * */
	private void makeAppFolder() {
		File file = new File(Constans.APPDIR);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		File file1 = new File(Constans.DIR_IMAGE);
		if (!file1.isDirectory()) {
			file1.mkdirs();
		}
		File file2 = new File(Constans.DIR_VOICE);
		if (!file2.isDirectory()) {
			file2.mkdirs();
		}

	}
}
