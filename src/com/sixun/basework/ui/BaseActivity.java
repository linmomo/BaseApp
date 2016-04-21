package com.sixun.basework.ui;

import com.sixun.basework.utils.L;
import com.sixun.basework.utils.ViewFinder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import android.widget.TextView;

public abstract class BaseActivity extends FragmentActivity {
	
	protected String TAG = getClass().getSimpleName();
	protected Activity mActivity;
	protected Context mContext;
	protected ViewFinder fv;
	protected boolean mIsInitData;  //是否初始化数据
	
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
		initData();
	}
	
	/**
	 * 设置布局，在此方法可以初始化UI
	 */
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		L.e("setContentView");
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
	 * 初始化页面数据,无关view, 可以不实现
	 */
	protected void initData() {}
	
	/**
	 * 初始化控件
	 */
	protected abstract void initView();
	
	/**
	 * 设置view
	 */
	protected void setView() {};
	
	
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
    
}
