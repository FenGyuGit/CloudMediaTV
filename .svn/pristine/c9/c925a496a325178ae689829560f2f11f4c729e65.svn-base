/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-8-24 下午3:24:55
 * 
 */
package com.geeya.wifitv.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.geeya.wifitv.R;
import com.geeya.wifitv.service.AlarmServiceRunnable;
import com.geeya.wifitv.utils.LegalityJudgeUtil;
import com.geeya.wifitv.utils.UpdateUserBehaviour;

/**
 * @author Administrator
 * 
 */
public class OnClickDialog {

	private String ADID;
	private String ADLink;
	private UpdateUserBehaviour updateUserBehaviour;

	public OnClickDialog(String ADID, String ADLink) {
		// TODO Auto-generated constructor stub
		this.ADID = ADID;
		this.ADLink = ADLink;
		updateUserBehaviour = new UpdateUserBehaviour();
	}

	public void showDialog(final Context context) {
		if (context != null && LegalityJudgeUtil.isLegalUrl(ADLink)) {
			Dialog alertDialog = new AlertDialog.Builder(context).setTitle("亲，点击打开更有惊喜！").setPositiveButton("打开", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					// 用户点击行为数据
					updateUserBehaviour.updateClickBehaviour(ADID);
					Uri uri = Uri.parse(ADLink);
					if(uri != null){
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						context.startActivity(intent);
					
					// 开始计时
						AlarmServiceRunnable alarmServiceRunnable = new AlarmServiceRunnable(ADID, context);
						alarmServiceRunnable.run();
					}
				}
			}).setNegativeButton("离开", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			}).create();
			alertDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
			alertDialog.show();
		}
	}
}
