package com.sixun.basework;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sixun.basework.Constans.Constans;
import com.sixun.basework.common.RebootThreadExceptionHandler;
import com.sixun.basework.utils.MyUtils;

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
		// 初始化工具类
		MyUtils.initUtils(this);
		makeAppFolder();
		// 初始化uil
		initImageLoader(getApplicationContext());
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

	// 初始化uil
	public void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	// 初始化uil的配置
//	 .memoryCacheExtraOptions(480, 800) // max width, max
//	 height，即保存的每个缓存文件的最大长宽
//	 .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null) // Can
//	 slow ImageLoader, use it carefully (Better don't useit)/设置缓存的详细信息，最好不要设置这个
//	 .threadPoolSize(3)//线程池内加载的数量
//	 .threadPriority(Thread.NORM_PRIORITY - 2)
//	 .denyCacheImageMultipleSizesInMemory()
//	 .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can
//	 pass your own memory cache implementation/你可以通过自己的内存缓存实现
//	 .memoryCacheSize(2 * 1024 * 1024)
//	 .discCacheSize(50 * 1024 * 1024)
//	 .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5加密
//	 .tasksProcessingOrder(QueueProcessingType.LIFO)
//	 .discCacheFileCount(100) //缓存的文件数量
//	 .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
//	 .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
//	 .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
//	 // connectTimeout (5 s), readTimeout (30 s)超时时间
//	 .writeDebugLogs() // Remove for release app

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
