/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-9-6 上午10:57:57
 * 
 */
package com.geeya.wifitv.bean;

import android.content.Context;
import android.os.Environment;

/**
 * @author Administrator
 *
 */
public class ConfigInfo extends Entity {

	private static final long serialVersionUID = 1L;
	
	private boolean autoCheckWifi;
	private boolean acceptNotice;
	private boolean autoUpdate;
	private String downloadPath;

	public ConfigInfo(Context context) {
		autoCheckWifi = true;
		acceptNotice = true;
		autoUpdate = true;
		downloadPath = Environment.DIRECTORY_DOWNLOADS;
	}
	
	public void setAutoCheckWifi(boolean autoCheckWifi) {
		this.autoCheckWifi = autoCheckWifi;
	}
	public boolean getAutoCheckWifi() {
		return autoCheckWifi;
	}
	
	public void setAcceptNotice(boolean acceptNotice) {
		this.acceptNotice = acceptNotice;
	}
	public boolean getAcceptNotice() {
		return acceptNotice;
	}
	
	public void setAutoUpdate(boolean autoUpdate) {
		this.autoUpdate = autoUpdate;
	}
	public boolean getAutoUpdate() {
		return false;
	}
	
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
	public String getDownloadPath() {
		return downloadPath;
	}

}
