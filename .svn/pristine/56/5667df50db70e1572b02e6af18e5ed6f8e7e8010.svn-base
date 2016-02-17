/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-8-24 下午2:29:40
 * 
 */
package com.geeya.wifitv.utils;

import java.util.HashMap;

import com.geeya.wifitv.AppConfig;
import com.geeya.wifitv.WiFiTVApplication;
import com.geeya.wifitv.api.AppApiResponse;
import com.geeya.wifitv.bean.AreaInfo;
import com.geeya.wifitv.bean.UserActionInfo;
import com.geeya.wifitv.bean.UserInfo;

/**
 * @author Administrator
 * 
 */
public class UpdateUserBehaviour {

	private UserInfo userInfo;
	private AreaInfo areaInfo;

	public UpdateUserBehaviour() {
		// TODO Auto-generated constructor stub
		userInfo = WiFiTVApplication.getInstance().getUserInfo();
		areaInfo = WiFiTVApplication.getInstance().getAreaInfo();
	}

	/**
	 * 用户浏览数据
	 * 
	 * @param ADID
	 * 
	 *            Created by Administrator Created on 2015-8-24 下午2:32:27
	 */
	public void updateBrowseBehaviour(String ADID) {
		if (userInfo != null) {
			UserActionInfo userActionInfo = new UserActionInfo("1", userInfo.getUserName(), userInfo.getUserIdentify(), areaInfo.getAreaID(), Tools.getCurrentTime(), ADID, "1");
			String action = userActionInfo.getUserActionInfos().toString();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("actionMsg", action);
			String postUrl = AppConfig.CLOUD_IP + AppConfig.API_USERACTION;
			if (postUrl != null && !postUrl.equals("")) {
				VolleyUtil.volleyPost(postUrl, map, new AppApiResponse<String>() {

					@Override
					public void callback(String str) {
						// TODO Auto-generated method stub

					}

				});
			}

		}
	}

	/**
	 * 用户点击数据
	 * 
	 * @param ADID
	 * 
	 *            Created by Administrator Created on 2015-8-24 下午2:32:46
	 */
	public void updateClickBehaviour(String ADID) {
		if (userInfo != null) {
			UserActionInfo userActionInfo = new UserActionInfo("1", userInfo.getUserName(), userInfo.getUserIdentify(), areaInfo.getAreaID(), Tools.getCurrentTime(), ADID, "2");
			String action = userActionInfo.getUserActionInfos().toString();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("actionMsg", action);
			String postUrl = AppConfig.CLOUD_IP + AppConfig.API_USERACTION;
			if (postUrl != null && !postUrl.equals("")) {
				VolleyUtil.volleyPost(postUrl, map, new AppApiResponse<String>() {

					@Override
					public void callback(String str) {
						// TODO Auto-generated method stub

					}

				});
			}

		}
	}

	/**
	 * 用户关注数据
	 * 
	 * @param ADID
	 * 
	 *            Created by Administrator Created on 2015-8-24 下午2:33:11
	 */
	public void updateAttentionBehaviour(String ADID) {
		if (userInfo != null) {
			UserActionInfo userActionInfo = new UserActionInfo("1", userInfo.getUserName(), userInfo.getUserIdentify(), areaInfo.getAreaID(), Tools.getCurrentTime(), ADID, "3");
			String action = userActionInfo.getUserActionInfos().toString();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("actionMsg", action);
			String postUrl = AppConfig.CLOUD_IP + AppConfig.API_USERACTION;
			if (postUrl != null && !postUrl.equals("")) {
				VolleyUtil.volleyPost(postUrl, map, new AppApiResponse<String>() {

					@Override
					public void callback(String str) {
						// TODO Auto-generated method stub

					}

				});
			}
		}
	}
	
	/**
	 * 用户点播视频数据
	 * 
	 * @param videoID
	 *
	 * Created by Administrator
	 * Created on 2015-8-25 下午5:20:43
	 */
	public void updateProgramBehaviour(String videoID) {
		if (userInfo != null) {
			UserActionInfo userActionInfo = new UserActionInfo("2", userInfo.getUserName(), userInfo.getUserIdentify(), areaInfo.getAreaID(), Tools.getCurrentTime(), videoID, "0");
			String action = userActionInfo.getUserActionInfos().toString();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("actionMsg", action);
			String postUrl = AppConfig.CLOUD_IP + AppConfig.API_USERACTION;
			if (postUrl != null && !postUrl.equals("")) {
				VolleyUtil.volleyPost(postUrl, map, new AppApiResponse<String>() {

					@Override
					public void callback(String str) {
						// TODO Auto-generated method stub

					}

				});
			}
		}
	}
	
	/**
	 * 用户直播视频数据
	 * 
	 * @param videoID
	 *
	 * Created by Administrator
	 * Created on 2015-8-25 下午5:21:02
	 */
	public void updateLiveBehaviour(String videoID) {
		if (userInfo != null) {
			UserActionInfo userActionInfo = new UserActionInfo("2", userInfo.getUserName(), userInfo.getUserIdentify(), areaInfo.getAreaID(), Tools.getCurrentTime(), videoID, "1");
			String action = userActionInfo.getUserActionInfos().toString();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("actionMsg", action);
			String postUrl = AppConfig.CLOUD_IP + AppConfig.API_USERACTION;
			if (postUrl != null && !postUrl.equals("")) {
				VolleyUtil.volleyPost(postUrl, map, new AppApiResponse<String>() {

					@Override
					public void callback(String str) {
						// TODO Auto-generated method stub

					}

				});
			}
		}
	}

}
