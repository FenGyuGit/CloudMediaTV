package com.geeya.wifitv;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.geeya.wifitv.bean.AreaInfo;
import com.geeya.wifitv.bean.ConfigInfo;
import com.geeya.wifitv.bean.UserInfo;
import com.geeya.wifitv.utils.NetworkCheck;
import com.geeya.wifitv.utils.PreferenceUtils;
import com.geeya.wifitv.utils.VolleyUtil;

public class WiFiTVApplication extends Application {

//	public static final String CLOUD_IP = "http://192.168.0.136:8008";
	// public static final String CLOUD_IP = "http://cloud.wifitv.com.cn:8008";

//	private String netElementIP;
//	public static final String netElementPort = "8080";
	private UserInfo userInfo = null;
	private AreaInfo areaInfo = null;
	private ConfigInfo configInfo = null;
	private String macAddress;
	private PreferenceUtils preferenceUtils;
	private NetworkCheck networkCheck = null;

	// 当前是否在debug模式下
	public static final boolean IS_DEBUG = false;
	public static final boolean IS_RecordException = false;

	private static WiFiTVApplication application;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		application = this;
		init();
	}

	public static synchronized WiFiTVApplication getInstance() {
		return application;
	}

	private void init() {
		VolleyUtil.init(this);

		if (IS_DEBUG) {
			// 严格模式
			if (Build.VERSION.SDK_INT >= 18) {
				StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().penaltyLog().build());
				StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
						.detectActivityLeaks()
						.detectLeakedClosableObjects()
						.detectLeakedRegistrationObjects()
						.detectLeakedSqlLiteObjects()
						.detectFileUriExposure()
						.penaltyLog()
						.build());
			}
		}
		
		//AppExceptionHandler appExceptionHandler = AppExceptionHandler.getInstance();
		//appExceptionHandler.init(getApplicationContext());

		networkCheck = new NetworkCheck(getApplicationContext());
		userInfo = new UserInfo();
		configInfo = new ConfigInfo(this);
		preferenceUtils = PreferenceUtils.getInstance(this);
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setAreaInfo(AreaInfo areaInfo) {
		this.areaInfo = areaInfo;
		preferenceUtils.saveStringValue("areaInfo", areaInfo.toJSONString());
	}

	public AreaInfo getAreaInfo() {
		if (areaInfo != null) {
			return areaInfo;
		} else {
			String jsonString = preferenceUtils.getStringValue("areaInfo");
			if (jsonString.equals("")) {
				return areaInfo = new AreaInfo();
			} else {
				try {
					JSONObject jsonObject = new JSONObject(jsonString);
					return areaInfo = new AreaInfo(jsonObject);
				} catch (JSONException e) {
					e.printStackTrace();
					return areaInfo = new AreaInfo();
				}
			}
		}
	}
	
	public PreferenceUtils getPreferenceUtils() {
		return preferenceUtils;
	}

	public void setConfigInfo(ConfigInfo configInfo) {
		this.configInfo = configInfo;
	}

	public ConfigInfo getConfigInfo() {
		if (null == configInfo) {
			return new ConfigInfo(this);
		} else {
			return configInfo;
		}
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public NetworkCheck getNetworkCheck() {
		if (networkCheck == null) {
			return new NetworkCheck(getApplicationContext());
		} else {
			return networkCheck;
		}
	}

}
