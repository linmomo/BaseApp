package com.sixun.basework.ui;

import com.sixun.basework.utils.L;
import com.sixun.basework.utils.ToastUtils;
import com.sixun.basework.utils.ViewFinder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import android.widget.TextView;

public class BaseActivity extends FragmentActivity {
	
	public Activity mActivity;
	public Context mContext;
	public ViewFinder fv;
	private boolean mIsInitData;  //是否初始化数据
	
	/**
	 * 初始化全局变量
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		L.e("oncreat");
		this.mActivity=this;
		this.mContext=this;
		this.fv = new ViewFinder(this.mActivity);
	}
	
	/**
	 * 设置布局，在此方法可以初始化UI
	 */
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		L.e("setContentView");
		initData();
		initView();
	}
	
	/**
	 * 窗口是否获得焦点
	 * 为了处理在Activity还没完全显示时，弹出PopupWindow或者Dialog  崩Activity not running 错误
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		L.e("onWindowFocusChanged");
		  if (hasFocus) {  
			  //第一次获得时初始化数据，之后不再初始化
	            if (!mIsInitData) {  
	            	//dialog或popwindow显示
//	            	show();
	                mIsInitData = true;  
	            }  
	        }  
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		L.e("onStart");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		L.e("onResume");
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		L.e("onPause");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		L.e("onStop");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		L.e("onDestroy");
	}
	
	/**
	 * 初始化控件
	 */
	protected void initView(){}
	/**
	 * 初始化数据, 可以不实现
	 */
	protected void initData() {}
	
	  /** 
     * 获取TextView中的文本信息 
     *  
     * @param tv 
     * @return 
     */  
    protected String mGetTvContent(TextView tv) {  
        return tv.getText().toString().trim();  
    }  
  
    /** 
     * 获取EditText中的文本信息 
     *  
     * @param et 
     * @return 
     */  
    protected String mGetEtContent(EditText et) {  
        return et.getText().toString().trim();  
    }  
    
    /**
     * 长时间显示
     * @param String message
     */
    protected void longMsg(String message){
    	if(isFinishing())
    		return;
    	ToastUtils.customLong(this.mContext, message);
    }
    
    /**
     * 长时间显示
     * @param Int message
     */
    protected void longMsg(int message){
    	if(isFinishing())
    		return;
    	ToastUtils.customLong(this.mContext, getResources().getString(message));
    }
    
    /**
     * 短时间显示
     * @param String message
     */
    protected void shortMsg(String message){
    	if(isFinishing())
    		return;
    	ToastUtils.customLong(this.mContext, message);
    }
    
    /**
     * 短时间显示
     * @param Int message
     */
    protected void shortMsg(int message){
    	if(isFinishing())
    		return;
    	ToastUtils.customLong(this.mContext, getResources().getString(message));
    }
}
