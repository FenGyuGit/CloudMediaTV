package com.geeya.wifitv.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;

import com.geeya.wifitv.R;

public class DoubleClickExitHelper {

	private final Activity activity;
	private Handler handler;
	private Toast toast;
	
	private boolean isOnKeyBacking;
	
	
	public DoubleClickExitHelper(Activity activity) {
		this.activity = activity;
		handler = new Handler(Looper.getMainLooper());
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode != KeyEvent.KEYCODE_BACK) {
			return false;
		}
		
		if(isOnKeyBacking) {
			handler.removeCallbacks(onBackTimeRunnable);
			if(toast != null) {
				toast.cancel();
			}
			
			// 退出
			appExit();
			
			return true;
		} else {
			isOnKeyBacking = true;
			if(toast == null) {
				toast = Toast.makeText(activity, R.string.tip_exit, 2000);
			}
			toast.show();
			handler.postDelayed(onBackTimeRunnable, 2000);
			return true;
		}
	}
	
	private Runnable onBackTimeRunnable = new Runnable() {
		@Override
		public void run() {
			isOnKeyBacking = false;
			if(toast != null) {
				toast.cancel();
			}
		};
	};
	
	public void appExit() {
		try {
			if(activity != null) {
				activity.finish();
			}
			// 杀死该应用进程
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
