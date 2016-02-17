/**
 * @author Administrator
 * 用于被嵌套入ScrollView中的ListView
 * 2015-10-28
 */

package com.geeya.wifitv.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;

public class NestListView extends ListView{
	
	
	//用于嵌套ListView的ScrollView
	private ScrollView parentScrollView;

	public NestListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setParentScroollAble(false);
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			setParentScroollAble(true);
			break;
		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	public void setParentScroollAble(boolean flag){
		if(parentScrollView != null)
			parentScrollView.requestDisallowInterceptTouchEvent(!flag);
	}
	
	public void setParentScrollView(ScrollView scrollView){
		this.parentScrollView = scrollView;
	}
	
	public ScrollView getParentScrollView(){
		return parentScrollView;
	}

}
