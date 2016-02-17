package com.geeya.wifitv.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.geeya.wifitv.WiFiTVApplication;

public class BaseFragment extends Fragment {

	public WiFiTVApplication application;
	public Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		application = getApplication();
		context = application.getApplicationContext();
	}

	public WiFiTVApplication getApplication() {
		return (WiFiTVApplication) getActivity().getApplication();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		application = null;
		context = null;
	}

}
