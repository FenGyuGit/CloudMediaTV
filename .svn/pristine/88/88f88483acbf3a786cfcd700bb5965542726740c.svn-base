package com.geeya.wifitv.widget;

import com.geeya.wifitv.R;
import com.geeya.wifitv.ui.fragment.GameFragment;
import com.geeya.wifitv.ui.fragment.HomeFragment;
import com.geeya.wifitv.ui.fragment.LifeFragment;
import com.geeya.wifitv.ui.fragment.UserInfoFragment;
import com.geeya.wifitv.ui.fragment.VideoFragment;


public enum MainTab {
	
	VIDEO (0, R.string.main_tab_video, R.drawable.maintab_icon_video_selector, VideoFragment.class),
	GAME  (1, R.string.main_tab_game,  R.drawable.maintab_icon_game_selector,  GameFragment.class),
	HOME  (2, R.string.main_tab_home,  R.drawable.maintab_icon_home_selector,  HomeFragment.class),
	LIFE  (3, R.string.main_tab_life,  R.drawable.maintab_icon_life_selector,  LifeFragment.class),
	MY    (4, R.string.main_tab_my,    R.drawable.maintab_icon_user_selector,  UserInfoFragment.class);
	

	private int id;

	private int resName;

	private int resIcon;

	private Class<?> clz;
	
	private MainTab(int id, int resName, int resIcon, Class<?> clz) {
		this.id = id;
		this.resName = resName;
		this.resIcon = resIcon;
		this.clz = clz;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getResName() {
		return resName;
	}

	public void setResName(int resName) {
		this.resName = resName;
	}

	public int getResIcon() {
		return resIcon;
	}

	public void setResIcon(int resIcon) {
		this.resIcon = resIcon;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}

}
