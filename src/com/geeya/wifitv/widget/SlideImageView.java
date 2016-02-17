/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-8-27 下午6:02:21
 * 
 */
package com.geeya.wifitv.widget;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.android.volley.toolbox.ImageLoader;
import com.geeya.wifitv.R;
import com.geeya.wifitv.bean.ADInfo;
import com.geeya.wifitv.utils.UpdateUserBehaviour;
import com.geeya.wifitv.utils.VolleyUtil;

/**
 * @author Administrator
 * 
 */
public class SlideImageView extends FrameLayout {

	private Context context;

	private int timeInterval = 0; // 图片显示时间，默认为0

	private ArrayList<ADInfo> adInfoes; // 直播广告列表
	//private ArrayList<ImageView> imageViewList; // 广告图片列表

	private CycleViewPager viewPager;
	private ScheduledExecutorService scheduledExecutorService; // 延迟执行服务

	private ImageLoader imageLoader; // 图片加载

	private ViewPagerHandler handler;

	public SlideImageView(Context context) {
		this(context, null);
	}

	public SlideImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = getContext();
		imageLoader = VolleyUtil.getImageLoader();
	}

	public void init(ArrayList<ADInfo> adInfoes) {
		this.adInfoes = adInfoes;
		initData();
		initUI();
	}

	private void initData() {
		timeInterval = Integer.parseInt(adInfoes.get(0).getADDuration());
		//imageViewList = new ArrayList<ImageView>();
	}

	private void initUI() {
		if (adInfoes == null || adInfoes.size() == 0) {
			return;
		}

//		for (int i = 0; i < adInfoes.size(); i++) {
//			ImageView imageView = new ImageView(context);
//			imageView.setTag(adInfoes.get(i).getADContent());
//			imageView.setScaleType(ScaleType.FIT_XY);
//			imageViewList.add(imageView);
//		}

		LayoutInflater.from(context).inflate(R.layout.item_live_slideimageview, this, true);
		viewPager = (CycleViewPager) findViewById(R.id.vp_ad_image);
		viewPager.setFocusable(true);
		viewPager.setAdapter(new MyPagerAdapter(adInfoes));
		viewPager.setOnPageChangeListener(new MyPageChangeListener());

		handler = new ViewPagerHandler(viewPager, adInfoes.size());
	}

	public void startPlay() {
		if (timeInterval != 0) {
			scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
			scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), timeInterval, timeInterval, TimeUnit.SECONDS);
		}
	}

	public void stopPlay() {
		if (null != scheduledExecutorService) {
			scheduledExecutorService.shutdown();
		}
	}

	private class MyPagerAdapter extends PagerAdapter {

		private ArrayList<ADInfo> adInfo;
		private ArrayList<ImageView> imageViewCacheList;
		
		public MyPagerAdapter(ArrayList<ADInfo> adInfos){
			this.adInfo = adInfos;
			imageViewCacheList = new ArrayList<ImageView>();
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return adInfo.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			// TODO Auto-generated method stub
			//ImageView imageView = imageViewList.get(position);
			ImageView imageView = null;
			if(imageViewCacheList.isEmpty()){
				imageView = new ImageView(context);
				imageView.setScaleType(ScaleType.FIT_XY);
				imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}else {
				imageView = imageViewCacheList.remove(0);
			}
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					OnClickDialog clickDialog = new OnClickDialog(adInfoes.get(position).getADID(), adInfoes.get(position).getADLink());
					clickDialog.showDialog(context);
				}
			});
			imageView.setTag(adInfo.get(position).getADContent());
			
			imageLoader.get(imageView.getTag() + "", ImageLoader.getImageListener(imageView, R.drawable.ic_error, R.drawable.ic_error));

			//((ViewPager) container).addView(imageViewList.get(position));
			container.addView(imageView);

			return imageView;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

//		@Override
//		public Parcelable saveState() {
//			// TODO Auto-generated method stub
//			return null;
//		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			//((ViewPager) container).removeView(imageViewList.get(position));
			container.removeView((View)object);
			imageViewCacheList.add((ImageView)object);
		}

	}

	private class MyPageChangeListener implements OnPageChangeListener {

		private UpdateUserBehaviour updateUserBehaviour; // 用户行为数据上传

		public MyPageChangeListener() {
			this.updateUserBehaviour = new UpdateUserBehaviour();
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			handler.setCurrentItem(position);
			if(position == 0 || position == adInfoes.size() + 1)
				return;
			updateUserBehaviour.updateBrowseBehaviour(adInfoes.get(position - 1).getADID());
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
		}
	}

	private class SlideShowTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized (viewPager) {
				handler.obtainMessage().sendToTarget();
			}
		}
	}
	
	public static class ViewPagerHandler extends Handler {
		
		WeakReference<ViewPager> reference;
		
		private int currentItem = 1;
		private int size;
		
		public ViewPagerHandler(ViewPager viewPager, int size) {
			reference = new WeakReference<ViewPager>(viewPager);
			this.size = size;
		}
		
		@Override
		public void handleMessage(Message msg) {
			final ViewPager viewPager = (ViewPager) reference.get();
			if(++currentItem >= size + 1){
				currentItem = 1;
			}
			viewPager.setCurrentItem(currentItem);
		}
		
		public void setCurrentItem(int currentItem){
			this.currentItem = currentItem;
		}
		
		public int getCurrentItem(){
			return currentItem;
		}
		
	}

}
