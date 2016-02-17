package com.geeya.wifitv.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CycleViewPager extends ViewPager{
	
	private InnerPagerAdapter mPagerAdapter;
	
	public CycleViewPager(Context context) {
		super(context);
		setOnPageChangeListener(null);
	}

	public CycleViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnPageChangeListener(null);
	}
	
	@Override
	public void setAdapter(PagerAdapter arg0) {
		mPagerAdapter = new InnerPagerAdapter(arg0);
		super.setAdapter(mPagerAdapter);
		setCurrentItem(1);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		super.setOnPageChangeListener(new InnerOnPageChangeListener(listener));
	}

	private class InnerOnPageChangeListener implements OnPageChangeListener{

		private OnPageChangeListener mListener;
		private int position;
		
		public InnerOnPageChangeListener (OnPageChangeListener listener){
			this.mListener = listener;
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			if(null != mListener)
				mListener.onPageScrollStateChanged(arg0);
			if(arg0 == ViewPager.SCROLL_STATE_IDLE){
				if(position == mPagerAdapter.getCount() - 1){
					setCurrentItem(1, false);
				}else if(position == 0){
					setCurrentItem(mPagerAdapter.getCount() - 2, false);
				}
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			if(null != mListener) {
                mListener.onPageScrolled( arg0, arg1, arg2);
            }
		}

		@Override
		public void onPageSelected(int arg0) {
			position = arg0;
            if(null != mListener) {
                mListener.onPageSelected( arg0);
            }
		}
		
	}
	
	private class InnerPagerAdapter extends PagerAdapter{

		private PagerAdapter mAdapter;
		
		public InnerPagerAdapter(PagerAdapter adapter){
			this.mAdapter = adapter;
			adapter.registerDataSetObserver(new DataSetObserver() {
				
				@Override
				public void onChanged() {
					//super.onChanged();
					notifyDataSetChanged();
				}
				
				@Override
				public void onInvalidated() {
					//super.onInvalidated();
					notifyDataSetChanged();
				}
			});
		}
		
		@Override
		public int getCount() {
			return mAdapter.getCount() + 2;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			if(position == 0){
				position = mAdapter.getCount() - 1;
			}else if (position == mAdapter.getCount() + 1) {
				position = 0;
			}else {
				position -= 1;
			}
			return mAdapter.instantiateItem(container, position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return mAdapter.isViewFromObject(arg0, arg1);
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			//super.destroyItem(container, position, object);
			
			mAdapter.destroyItem(container, position, object);
		}
		
	}

}
