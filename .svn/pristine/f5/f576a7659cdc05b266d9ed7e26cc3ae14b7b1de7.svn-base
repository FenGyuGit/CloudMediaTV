package com.geeya.wifitv.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.geeya.wifitv.WiFiTVApplication;

public class BaseActivity extends FragmentActivity {

	public Context context;
	public Activity activity;
	public WiFiTVApplication application;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		application = (WiFiTVApplication) getApplication();
		context = application.getApplicationContext();
		activity = this;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		application = null;
		context = null;
		activity = null;
	}
}
