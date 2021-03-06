/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-24 上午10:40:01
 * 
 */
package com.geeya.wifitv.ui.activity;

import io.vov.vitamio.LibsChecker;

import java.util.ArrayList;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.geeya.wifitv.R;
import com.geeya.wifitv.bean.ADInfo;
import com.geeya.wifitv.bean.ProgramSource;
import com.geeya.wifitv.presenter.player.PlayerAction;
import com.geeya.wifitv.presenter.player.PlayerActionImpl;
import com.geeya.wifitv.ui.interf.IProgramPlay;
import com.geeya.wifitv.widget.CornerADPlayer;
import com.geeya.wifitv.widget.StartADPlayer;
import com.geeya.wifitv.widget.StartADPlayer.OnADCompletionListener;
import com.geeya.wifitv.widget.VideoPlayer;

/**
 * @author Administrator
 * 
 */
public class ProgramPlayActivity extends BaseActivity implements IProgramPlay {

	private PlayerAction playerAction;

	private long currentPosition; // 当前播放位置

	private ArrayList<ProgramSource> programSources;
	private String programID;
	private int position;

	private StartADPlayer startADPlayer = null;
	private CornerADPlayer cornerADPlayer = null;

	public VideoPlayer programPlayer = null;
	private GestureDetector gestureDetector = null;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		if (!LibsChecker.checkVitamioLibs(this)) {
			return;
		}

		setContentView(R.layout.player);
		Bundle bundle = getIntent().getExtras();
		programSources = (ArrayList<ProgramSource>) bundle.getSerializable("ProgramSource");
		programID = bundle.getString("ProgramID");
		position = bundle.getInt("Position");

		playerAction = new PlayerActionImpl(this);
		playerAction.programPlayerAction();
		System.out.println("onCreate");

	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		System.out.println(newConfig.orientation);
	}

//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		// TODO Auto-generated method stub
//		super.onSaveInstanceState(outState);
//		outState.putLong("currentPostion", currentPosition);
//	}
//
//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onRestoreInstanceState(savedInstanceState);
//		if (savedInstanceState != null && programPlayer != null) {
//			long temp = savedInstanceState.getLong("currentPosition");
//			programPlayer.restart(temp);
//			cornerADPlayer.restart();
//		}
//	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		super.onResume();
		
		programPlayer.restart(currentPosition);
		if(!programPlayer.getIsPlaying())
			cornerADPlayer.onPauseShow();
		cornerADPlayer.restart();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if(!programPlayer.getIsPlaying())
			programPlayer.setIsActive(false);
		super.onPause();
		if (startADPlayer != null) {
			finish();
		} else {
			currentPosition = programPlayer.pause();
			cornerADPlayer.pause();
		}		
	}	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("onDestroy");
		programPlayer.setIsPlayingAd(true);
		if (startADPlayer != null) {
			startADPlayer.stopADPlayer();
		}
		if (programPlayer != null) {
			programPlayer.stop();
			programPlayer = null;
			cornerADPlayer.stop();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			programPlayer.setVisibility(View.INVISIBLE);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (startADPlayer == null) {
			if (gestureDetector.onTouchEvent(event)) {
				return true;
			}
			// 处理手势结束
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_UP:
				programPlayer.endGesture();
				break;
			default:
				break;
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void initProgramPlay() {
		// TODO Auto-generated method stub
		programPlayer = new VideoPlayer(context, activity);
		cornerADPlayer = new CornerADPlayer(activity);
		programPlayer.setMediaControllerBar(cornerADPlayer); // 点播播放需要设置进度条，直播不需要
		programPlayer.setProgramSources(programSources, programID, position); // 设置点播资源信息
		gestureDetector = programPlayer.getGesture();
		programPlayer.start();
		
	}

	@Override
	public void initStartAD(ArrayList<ADInfo> adInfoes) {
		// TODO Auto-generated method stub
		programPlayer.setIsPlayingAd(true);
		currentPosition = programPlayer.pause();		
		cornerADPlayer.pause();
		startADPlayer = new StartADPlayer(ProgramPlayActivity.this, this, adInfoes);
		startADPlayer.startADPalyer();
		startADPlayer.setOnADCompletionListener(new OnADCompletionListener() {

			@Override
			public void onADCompletion() {
				// TODO Auto-generated method stub
				if (programPlayer != null) {					
					programPlayer.restart(currentPosition);
					programPlayer.setIsPlayingAd(false);
					cornerADPlayer.restart();
				}
				startADPlayer = null;
			}
		});
	}

	@Override
	public void initCornerAD(ArrayList<ADInfo> adInfoes) {
		// TODO Auto-generated method stub
		cornerADPlayer.setCornerADView(adInfoes);
	}

	@Override
	public void initSubtitleAD(ArrayList<ADInfo> adInfoes) {
		// TODO Auto-generated method stub
		cornerADPlayer.setSubtitleADView(adInfoes);
	}

	@Override
	public void initPauseAD(ArrayList<ADInfo> adInfoes) {
		// TODO Auto-generated method stub
		cornerADPlayer.setPauseADView(adInfoes);
	}

}
