package com.sixun.basework.imageloader;

/**
 * imageLoaderFactory工厂类
 * @author lin
 *
 */
public class ImageLoaderFactory {
	
	 private static volatile GlideImageLoader sInstance;
	 
	 private ImageLoaderFactory() {
		 
	 }

	    /**
	     * 获取图片加载器
	     *
	     * @return
	     */
	    public static GlideImageLoader getLoader() {
	        if (sInstance == null) {
	            synchronized (ImageLoaderFactory.class) {
	                if (sInstance == null) {
	                    sInstance = new GlideImageLoader();
	                }
	            }
	        }
	        return sInstance;
	    }
}
