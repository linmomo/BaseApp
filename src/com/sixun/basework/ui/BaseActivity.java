package com.sixun.basework.ui;

import com.sixun.basework.utils.ViewFinder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {
	
	public Activity mActivity;
	public Context mContext;
	public ViewFinder fv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mActivity=this;
		this.mContext=this;
		this.fv = new ViewFinder(mActivity);
		initData();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
	}
	
	// 初始化数据, 可以不实现
	public void initData() {}
}
