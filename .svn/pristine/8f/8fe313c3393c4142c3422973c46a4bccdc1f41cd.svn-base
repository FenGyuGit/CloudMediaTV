/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-8-26 下午3:43:33
 * 
 */
package com.geeya.wifitv.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.geeya.wifitv.R;

/**
 * @author Administrator
 * 
 */
public class VideoFragment extends BaseFragment {

	private View rootView; // 缓存Fragment view

	private FragmentManager fragmentManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		fragmentManager = getChildFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		LiveFragment liveFragment = new LiveFragment();
		fragmentTransaction.replace(R.id.fl_video, liveFragment);
		fragmentTransaction.commit();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_maintab_video, container, false);
		}

		initView();

		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}

	private void initView() {
		RadioGroup group = (RadioGroup) rootView.findViewById(R.id.rg_change);
		RadioButton leftButton = (RadioButton) rootView.findViewById(R.id.rb_left);
		RadioButton rightButton = (RadioButton) rootView.findViewById(R.id.rb_right);
		leftButton.setText(R.string.radiobutton_text_live);
		final int leftButtonId = leftButton.getId();
		rightButton.setText(R.string.radiobutton_text_program);
		final int rightButtonId = rightButton.getId();
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				if (checkedId == leftButtonId) {
					LiveFragment liveFragment = new LiveFragment();
					fragmentTransaction.replace(R.id.fl_video, liveFragment);
				}

				if (checkedId == rightButtonId) {
					ProgramFragment programFragment = new ProgramFragment();
					fragmentTransaction.replace(R.id.fl_video, programFragment);
				}
				fragmentTransaction.commit();
			}
		});

	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		rootView = null;
	}

}
