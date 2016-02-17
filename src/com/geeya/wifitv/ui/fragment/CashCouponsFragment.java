package com.geeya.wifitv.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.geeya.wifitv.R;
import com.geeya.wifitv.adapter.CashListAda;
import com.geeya.wifitv.bean.CashCoupons;

public class CashCouponsFragment extends BaseFragment{
	
	private View rootView;
	private ImageView cash;
	private ListView lvCash;
	private List<CashCoupons> list;
//	private Bitmap bitmap;
	private Drawable drawable;
//	private RelativeLayout image;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(null == rootView){
			rootView = inflater.inflate(R.layout.fragment_cash_coupons, container, false);
		}
		list = new ArrayList<CashCoupons>();
		//cash = (ImageView)rootView.findViewById(R.id.iv_cash);
//		image = (RelativeLayout)rootView.findViewById(R.id.cash_detail);
		lvCash= (ListView)rootView.findViewById(R.id.lv_cashlist);
		//bitmap = readBitmap(R.drawable.cash_bg);
		//drawable = getResources().getDrawable(R.drawable.cash_bg);
		//cash.setBackgroundDrawable(handleBackground(drawable,R.color.red));
		//image.setBackgroundDrawable(handleBackground(drawable,R.color.cash_lv_item_bg));
		
		CashCoupons list1 = new CashCoupons(0xffff0033, "1", "2", true);
		list.add(list1);
		CashCoupons list2 = new CashCoupons(0xff5ad6e1, null, null, true);
		list.add(list2);
		list.add(list1);
		list.add(list2);
		list.add(list1);
		list.add(list2);
		
		//setBackground();
		//handleqBackground();
		//handleBackground();
		
		
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}
	
	@SuppressLint("NewApi")
	private Drawable handleBackground(Drawable drawable, int resId){
		//Drawable draw = new BitmapDrawable(bitmap);
		Drawable draw = drawable;		
		//drawable.setColorFilter(getResources().getColor(R.color.cash_lv_item_bg), PorterDuff.Mode.SRC_IN);		
		draw.setColorFilter(getResources().getColor(resId), PorterDuff.Mode.SRC_IN);
		return draw;
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void handleqBackground(){
		//Drawable drawable = cash.getBackground();
		//drawable.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
		//Drawable drawable = getDrawable(bitmap, R.color.red);
		
		int currentSDKVersion = android.os.Build.VERSION.SDK_INT;
		if(currentSDKVersion < 16)
			cash.setBackgroundDrawable(drawable);
		else
			cash.setBackground(drawable);
		//drawable.clearColorFilter();
	}
	
//	private void setBackground(){
//		image.setBackground(getDrawable(bitmap, R.color.cash_lv_item_bg));
//		cash.setBackground(getDrawable(bitmap, R.color.red));
//	}
//	
//	private Bitmap getBitMap(){
//		Resources res = getResources();  
//	    return BitmapFactory.decodeResource(res, R.drawable.cash_bg);
//	}
	
//	private Drawable getDrawable(Bitmap bitmap, int resId){
//		@SuppressWarnings("deprecation")
//		Drawable drawable = new BitmapDrawable(bitmap);
//		drawable.setColorFilter(getResources().getColor(resId), PorterDuff.Mode.SRC_IN);
//		return drawable;
//	}
	
//	private Bitmap readBitmap(int resId){
//		BitmapFactory.Options opt = new BitmapFactory.Options();
//		opt.inPreferredConfig = Config.RGB_565;
//		opt.inPurgeable = true;
//		opt.inInputShareable = true;
//		InputStream is = getResources().openRawResource(resId);
//		return BitmapFactory.decodeStream(is, null, opt);
//	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lvCash.setAdapter(new CashListAda(getActivity().getBaseContext(), list));		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser){
			Toast toast = Toast.makeText(context, R.string.app_exception, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

}
