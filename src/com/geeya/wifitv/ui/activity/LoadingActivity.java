package com.geeya.wifitv.ui.activity;

import io.vov.vitamio.LibsChecker;

import java.lang.ref.SoftReference;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.geeya.wifitv.R;
import com.geeya.wifitv.presenter.AppAction;
import com.geeya.wifitv.presenter.AppActionImpl;
import com.geeya.wifitv.ui.interf.ILoading;
import com.geeya.wifitv.utils.Tools;

public class LoadingActivity extends BaseActivity implements ILoading {

	private AppAction appAction;
	private boolean startFlag = true;

	private int images[] = { R.drawable.activity_loading_viewpager_start_0, 
			R.drawable.activity_loading_viewpager_start_1, 
			R.drawable.activity_loading_viewpager_start_2, 
			R.drawable.activity_loading_viewpager_start_3 };

	private ViewPager vpStart;
	private ImageView ivWelcomeLogo;
	private Bitmap bitmap;
	private ImageView iv_pager[] = new ImageView[4];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
//		
		appAction = new AppActionImpl(this, context);
		appAction.initLoad();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if(bitmap != null && !bitmap.isRecycled()){
			bitmap.recycle();
			bitmap = null;
		}
		System.gc();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		vpStart = null;
		ivWelcomeLogo = null;
		appAction = null;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK)
			startFlag = false;
		return super.onKeyDown(keyCode, event);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void initViewPager() {
		iv_pager[0]=(ImageView)findViewById(R.id.pager_1);
		iv_pager[1]=(ImageView)findViewById(R.id.pager_2);
		iv_pager[2]=(ImageView)findViewById(R.id.pager_3);
		iv_pager[3]=(ImageView)findViewById(R.id.pager_4);
		vpStart = (ViewPager) this.findViewById(R.id.vp_start);
		vpStart.setVisibility(View.VISIBLE);
		((LinearLayout)findViewById(R.id.viewGroup)).setVisibility(View.VISIBLE);
		vpStart.setFocusable(true);
		Bitmap bitmap = Tools.decodeHalfBitmapFromResource(getResources(), R.drawable.activity_loading_viewpager_background);
		SoftReference<Bitmap> bitmapSR = new SoftReference<Bitmap>(bitmap);
		bitmap = null;
		//vpStart.setBackground(new BitmapDrawable(getResources(), bitmapSR.get()));
		vpStart.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmapSR.get()));
		vpStart.setAdapter(new MyPageAdapter());
		vpStart.setOnPageChangeListener(new MyPageChangeListener());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void initWelcome() {
		// TODO Auto-generated method stub
		final FrameLayout flWelcome = (FrameLayout) this.findViewById(R.id.fl_welcome);
		bitmap = Tools.decodeHalfBitmapFromResource(getResources(), R.drawable.welcome);
		SoftReference<Bitmap> bitmapSR = new SoftReference<Bitmap>(bitmap);
		//bitmap = null;
		//flWelcome.setBackground(new BitmapDrawable(getResources(), bitmapSR.get()));
		flWelcome.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmapSR.get()));
		ivWelcomeLogo = (ImageView) this.findViewById(R.id.iv_welcome_logo);
		ivWelcomeLogo.setImageBitmap(Tools.decodeHalfBitmapFromResource(getResources(), R.drawable.welcome_logo));
		ivWelcomeLogo.setVisibility(View.VISIBLE);
	}

	@Override
	public void directTo() {
		if(startFlag){
			Intent intent = new Intent(this, MainTabActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	private class MyPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			iv_pager[arg0].setImageResource(R.drawable.pager_current);
			for(int i = 0; i < iv_pager.length; i ++){
				if(i != arg0)
					iv_pager[i].setImageResource(R.drawable.pager_pre);
			}
		}
		
	}

	private class MyPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return images.length;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View imageLayout = layoutInflater.inflate(R.layout.item_loading_viewpager, null);
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.iv_start);
			imageView.setImageResource(images[position]);
			if (position == images.length - 1) {
				ImageView ivDirect = (ImageView) imageLayout.findViewById(R.id.iv_direct);
				ivDirect.setVisibility(View.VISIBLE);
				ivDirect.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						directTo();
					}
				});
			}
			
			((ViewPager) container).addView(imageLayout);
			
			return imageLayout;
		}
	
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}
		

	}

}
