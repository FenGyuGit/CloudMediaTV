package com.geeya.wifitv.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geeya.wifitv.R;
import com.geeya.wifitv.WiFiTVApplication;
import com.geeya.wifitv.bean.ApkInfo;
import com.geeya.wifitv.presenter.AppAction;
import com.geeya.wifitv.presenter.AppActionImpl;
import com.geeya.wifitv.ui.interf.IUpdate;

public class AboutActivity extends BaseActivity implements IUpdate, OnClickListener{

	private AppAction actionUpdate;
	private Button btnUpdate;
	private TextView tvCooperation;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		actionUpdate = new AppActionImpl(this);
		initView();
	}
	
	private void initView(){
		btnUpdate = (Button)findViewById(R.id.bt_about_update);
		tvCooperation = (TextView)findViewById(R.id.tv_about_cooperation);
		TextView title = (TextView)findViewById(R.id.title);
		title.setText(R.string.user_about);
		LinearLayout ll_back = (LinearLayout)findViewById(R.id.back);
		ll_back.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		tvCooperation.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_about_update:
			checkUpdate();
			break;
		case R.id.tv_about_cooperation:
			Intent intent = new Intent(AboutActivity.this, JoinActivity.class);
			startActivity(intent);
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}
	
	private void checkUpdate(){
		actionUpdate.update();
	}
	
	@Override
	public void createDialog(final ApkInfo apkInfo) {
		// TODO Auto-generated method stub
		if(context != null){
			AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
			builder.setTitle(getResources().getString(R.string.checkupdate));
			builder.setMessage(apkInfo.getDescrible());
			builder.setPositiveButton(context.getResources().getString(R.string.update_now), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					String url = WiFiTVApplication.getInstance().getAreaInfo().getNetElementIP();
					if(url != null)
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
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		toast.show();
	}
}
