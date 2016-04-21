package com.sixun.basework.imageloader;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sixun.basework.R;

/**
 * UIL工具类
 * @author lin
 *
 */
public class UilImageLoader {
	
	private final static String HTTP = "http";
    private final static String HTTPS = "https";
	
	/**
	 * 显示图片来自文件 
	 * @param imageView 显示图片的imageview
	 * @param imageFile 图片文件
	 * @param loadingResId 加载中的资源id
	 * @param loadErrorResId 加载失败的资源id
	 */
    public void displayImage(ImageView imageView, File imageFile, int loadingResId,int loadErrorResId) {
        int imageLoadingResId = R.drawable.ic_stub;
        int imageErrorResId = R.drawable.ic_empty;
        if (loadingResId != 0) {
            imageLoadingResId = loadingResId;
        }
        if(loadErrorResId !=0){
        	imageErrorResId = loadErrorResId;
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(imageLoadingResId)
                .showImageForEmptyUri(imageErrorResId)
                .showImageOnFail(imageErrorResId)
                .cacheInMemory(true) //加载本地图片不需要再做SD卡缓存，只做内存缓存即可
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        String uri;
        if (imageFile == null) {
            uri = "";
        } else {
            uri = ImageDownloader.Scheme.FILE.wrap(imageFile.getAbsolutePath());
        }
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }
    
    /**
	 * 显示图片来自文件 
	 * @param imageView 显示图片的imageview
	 * @param imageFile 图片文件
	 */
    public void displayImage(ImageView imageView, File imageFile){
    	displayImage(imageView, imageFile, 0, 0);
    }
    
    /**
     * 显示图片来自url
     * @param imageView 显示图片的imageview
     * @param imageUrl 图片url
     * @param loadingResId 加载中的资源id
     * @param loadErrorResId  加载失败的资源id
     */
    public void displayImage(ImageView imageView, String imageUrl, int loadingResId,int loadErrorResId) {
    	int imageLoadingResId = R.drawable.ic_stub;
        int imageErrorResId = R.drawable.ic_empty;
        if (loadingResId != 0) {
            imageLoadingResId = loadingResId;
        }
        if(loadErrorResId !=0){
        	imageErrorResId = loadErrorResId;
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(imageLoadingResId)
                .showImageForEmptyUri(imageErrorResId)
                .showImageOnFail(imageErrorResId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        if (imageUrl.startsWith(HTTPS)) {
            String uri = ImageDownloader.Scheme.HTTPS.wrap(imageUrl);
            ImageLoader.getInstance().displayImage(uri, imageView, options);
        } else if (imageUrl.startsWith(HTTP)) {
            String uri = ImageDownloader.Scheme.HTTP.wrap(imageUrl);
            ImageLoader.getInstance().displayImage(uri, imageView, options);
        }
    }
    
    /**
     * 显示图片来自url
     * @param imageView 显示图片的imageview
     * @param imageUrl 图片url
     */
    public void displayImage(ImageView imageView, String imageUrl){
    	displayImage(imageView, imageUrl, 0, 0);
    }
    
    /**
     * 下载图片监听
     * @param imageUrl  图片url
     * @param listener 	下载监听
     */
    public void loadListener(String imageUrl, ImageLoadingListener listener){
    	int imageLoadingResId = R.drawable.ic_stub;
        int imageErrorResId = R.drawable.ic_empty;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
        			.showImageOnLoading(imageLoadingResId)
        			.showImageForEmptyUri(imageErrorResId)
        			.showImageOnFail(imageErrorResId)
        			.cacheInMemory(true)
        			.cacheOnDisk(true)
        			.considerExifParams(true)
        			.bitmapConfig(Bitmap.Config.RGB_565)
        			.build();
        if (imageUrl.startsWith(HTTPS)) {
            String uri = ImageDownloader.Scheme.HTTPS.wrap(imageUrl);
            ImageLoader.getInstance().loadImage(uri, options ,listener);
        } else if (imageUrl.startsWith(HTTP)) {
            String uri = ImageDownloader.Scheme.HTTP.wrap(imageUrl);
            ImageLoader.getInstance().loadImage(uri, options ,listener);
        }
    }
	
	
	/**
	 * 初始化UIL
	 * @param context
	 */
	public static void initImageLoader(Context context) {
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

	// 初始化uil的配置说明
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

	// 图片的所有配置说明
	// .showImageOnLoading(R.drawable.ic_stub)//设置图片在下载期间显示的图片
	// .showImageForEmptyUri(R.drawable.ic_empty)//设置图片Uri为空或是错误的时候显示的图片
	// .showImageOnFail(R.drawable.ic_error)//设置图片加载/解码过程中错误时候显示的图片
	// .cacheInMemory(true)//设置下载的图片是否缓存在内存中
	// .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
	// .considerExifParams(true)//是否考虑JPEG图像EXIF参数（旋转，翻转）
	// .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
	// EXACTLY :图像将完全按比例缩小的目标大小-推荐使用
	// EXACTLY_STRETCHED:图片会缩放到目标大小完全
	// IN_SAMPLE_INT:图像将被二次采样的整数倍-推荐使用
	// IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
	// NONE:图片不会调整
	// .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
	// 默认是ARGB_8888，使用RGB_565会比使用ARGB_8888少消耗2倍的内存
	// //.decodingOptions(BitmapFactory.Options decodingOptions)//设置图片的解码配置
	// .delayBeforeLoading(0)//int delayInMillis为你设置的下载前的延迟时间
	// //设置图片加入缓存前，对bitmap进行设置
	// //.preProcessor(BitmapProcessor preProcessor)
	// .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
	// .displayer(new RoundedBitmapDisplayer(20))//设置图片的显示方式
	// 　 　RoundedBitmapDisplayer（int
	// roundPixels）设置圆角图片，不推荐！！！他会创建新的ARGB_8888格式的Bitmap对象；
	// FakeBitmapDisplayer（）这个类什么都没做
	// FadeInBitmapDisplayer（int durationMillis）设置图片渐显的时间
	// SimpleBitmapDisplayer()正常显示一张图片

	// 加载其它来源的图片
	// String imageUri = "http://site.com/image.png"; // from Web
	// String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
	// String imageUri = "content://media/external/audio/albumart/13"; // from
	// content provider
	// String imageUri = "assets://image.png"; // from assets
	// String imageUri = "drawable://" + R.drawable.image; // from drawables
	// (only images, non-9patch)
}
