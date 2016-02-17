package com.geeya.wifitv.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.geeya.wifitv.R;
import com.geeya.wifitv.WiFiTVApplication;
import com.geeya.wifitv.bean.ApkInfo;
import com.geeya.wifitv.presenter.AppAction;
import com.geeya.wifitv.presenter.AppActionImpl;
import com.geeya.wifitv.ui.interf.IUpdate;
import com.geeya.wifitv.utils.DoubleClickExitHelper;
import com.geeya.wifitv.widget.MainTab;

public class MainTabActivity extends BaseActivity implements IUpdate {

	private FragmentTabHost mTabHost;
	private AppAction actionUpdate;

	private DoubleClickExitHelper exitHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintab);
		actionUpdate = new AppActionImpl(this);
		initView();
		if (application.getConfigInfo().getAutoUpdate()) {
			actionUpdate.update();
		}
		getWindow().setWindowAnimations(R.style.DialogAnimation);
	}

	private void initView() {
		exitHelper = new DoubleClickExitHelper(this);

		mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
		mTabHost.setup(getApplicationContext(), getSupportFragmentManager(), R.id.realtabcontent);
		if (android.os.Build.VERSION.SDK_INT > 10) {
			mTabHost.getTabWidget().setShowDividers(0);
		}

		MainTab[] tabs = MainTab.values();
		final int size = tabs.length;
		for (int i = 0; i < size; i++) {
			MainTab mainTab = tabs[i];
			TabSpec tabSpec = mTabHost.newTabSpec(getString(mainTab.getResName()));
			View indicator = LayoutInflater.from(getApplicationContext()).inflate(R.layout.indicator_maintab, null);
			ImageView icon = (ImageView) indicator.findViewById(R.id.iv_icon);
			icon.setImageResource(tabs[i].getResIcon());
			TextView title = (TextView) indicator.findViewById(R.id.tv_title);
			title.setText(tabs[i].getResName());
			tabSpec.setIndicator(indicator);
			mTabHost.addTab(tabSpec, tabs[i].getClz(), null);
			mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.maintab_background_selector);
		}

		mTabHost.setCurrentTab(2);
	}

	public void onDestroy() {
		super.onDestroy();
		mTabHost.clearAllTabs();
		// mTabHost = null;
	}

	@Override
	public void createDialog(final ApkInfo apkInfo) {
		// TODO Auto-generated method stub
		if (context != null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(MainTabActivity.this);
			builder.setTitle(getResources().getString(R.string.checkupdate));
			builder.setMessage(apkInfo.getDescrible());
			builder.setPositiveButton(context.getResources().getString(R.string.update_now), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					String url = WiFiTVApplication.getInstance().getAreaInfo().getNetElementIP();
					if (url != null)
						url += apkInfo.getUpdateUrl();
					actionUpdate.download(url);
				}
			});
			builder.setNegativeButton(context.getResources().getString(R.string.update_after), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		}
	}

	@Override
	public void showToast(String message) {
		// TODO Auto-generated method stub
		// 不做任何提示
		// Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		// toast.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return exitHelper.onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}
}
