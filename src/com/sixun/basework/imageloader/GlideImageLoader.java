package com.sixun.basework.imageloader;

import java.io.File;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.sixun.basework.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

/**
 * glide加载图片
 * 
 * @author lin
 *
 */
public class GlideImageLoader {

	public static final String ANDROID_RESOURCE = "android.resource://";
	public static final String FOREWARD_SLASH = "/";

	// 将资源ID转为Uri
	public Uri resourceIdToUri(Context context, int resourceId) {
		return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
	}
	
    /**
     * 加载网络图片
     * @param context 上下文	
     * @param url 图片url
     * @param imageView 显示图片的imageview
     * @param loadingResId 加载中的资源id
     * @param loadErrorResId 加载失败的资源id
     */
    public void loadUrlImage(Context context, String url, ImageView imageView , int loadingResId , int loadErrorResId) {
    	 int imageLoadingResId = R.drawable.ic_stub;
         int imageErrorResId = R.drawable.ic_empty;
         if (loadingResId != 0) {
             imageLoadingResId = loadingResId;
         }
         if(loadErrorResId !=0){
         	imageErrorResId = loadErrorResId;
         }
         Glide.with(context)
        		.load(url)
        		.placeholder(imageLoadingResId)
        		.error(imageErrorResId)
        		.crossFade()
          		.into(imageView);
    }
    
    /**
     * 加载网络图片
     * @param context 上下文	
     * @param url 图片url
     * @param imageView 显示图片的imageview
     */
    public void loadUrlImage(Context context, String url, ImageView imageView ){
    	loadUrlImage(context, url, imageView, 0, 0);
    } 
    
    /**
     * 加载本地图片
     * @param context 上下文	
     * @param path 图片文件路径
     * @param imageView 显示图片的imageview
     * @param loadingResId 加载中的资源id
     * @param loadErrorResId 加载失败的资源id
     */
    public void loadLocalImage(Context context, String path, ImageView imageView , int loadingResId , int loadErrorResId) {
    	 int imageLoadingResId = R.drawable.ic_stub;
         int imageErrorResId = R.drawable.ic_empty;
         if (loadingResId != 0) {
             imageLoadingResId = loadingResId;
         }
         if(loadErrorResId !=0){
         	imageErrorResId = loadErrorResId;
         }
        Glide.with(context)
                .load("file://" + path)
                .placeholder(imageLoadingResId)
                .error(imageErrorResId)
                .crossFade()
                .into(imageView);
    }
    
    /**
     * 加载本地图片
     * @param context 上下文	
     * @param path 图片文件路径
     * @param imageView 显示图片的imageview
     */
    public void loadLocalImage(Context context, String path, ImageView imageView ){
    	loadLocalImage(context, path, imageView, 0, 0);
    }

     /**
      * 下载图片，得到bitmap
      * @param context 上下文
      * @param url	图片url
      * @param target 下载监听
      */
    public void loadImage(Context context, String url, SimpleTarget<Bitmap> target){
		Glide.with(context)//如果下载图片独立于Activity声明周期，使用application的context
    			.load(url)
    			.asBitmap()//强制返回bitmp，防止或许是gif
    			.into(target);
    }
    
//    	Glide.with(context)
//		.load(url)
//    	.transform(transformations)//转换，可以包含多个转换  用了转换后你就不能使用 .centerCrop() 或 .fitCenter()了
//    	.thumbnail(0.1f)//显示缩略图  0.1f为缩略倍数，显示为原始分辨率的10%
//    	.priority(Priority.LOW)//请求的优先级
//    	.skipMemoryCache(true)//是否跳过内存缓存
//    	.diskCacheStrategy(DiskCacheStrategy.NONE)//磁盘缓存策略： NONE=什么都不缓存  SOURCE=仅仅只缓存原来的全分辨率的图像 RESULT=仅仅缓存最终的图像  ALL=缓存所有版本的图像（默认行为）
//    	.asGif()//是否gif，不是就显示错误占位符
//    	.asBitmap()//将gif的第一帧显示
//		.placeholder(R.drawable.ic_stub)// 设置占位图片
//		.error(R.drawable.ic_empty)//设置显示错误图片
//    	.animate(animation)//自定义动画
//		.crossFade()//设置渐现动画
//		.dontAnimate()//不需要动画
//		.override(width, height)//重设大小
//    	.centerCrop()//填满imageview，但图片可能不全
//		.fitCenter()//图片全显，但ImageView可能不会填满
//		.into(imageView);

}
