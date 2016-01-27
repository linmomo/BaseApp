package com.sixun.basework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 专门嵌套使用的ListView，重写其onMeasure()方法使其显示所有数据不会出现滚动条
 */
public class NestedListView extends ListView {
	  
    public NestedListView(Context context, AttributeSet attrs) {
        super(context, attrs);   
    }   
  
    public NestedListView(Context context) {
        super(context);   
    }   
  
    public NestedListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);   
    } 
    
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);   
    }   
}
