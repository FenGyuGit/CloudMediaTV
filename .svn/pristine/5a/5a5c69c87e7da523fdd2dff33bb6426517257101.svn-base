/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-8-24 下午2:30:52
 * 
 */
package com.geeya.wifitv.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class UserActionInfo extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JSONObject userInfo = new JSONObject();

	private final String USERNAME = "username"; // 用户名称
	private final String USERID = "userID"; // 用户ID
	private final String AREAID = "areaID"; // 地区编码

	private final String MSGTYPE = "msgType"; // 数据类型
	private final String CREATETIME = "createTime"; // 行为创建时间
	private final String OBJECTID = "objectID"; // 行为对象，视频为视频ID,广告为广告ID
	private final String ACTION = "action"; // 动作类型, 1: 查看, 2: 点击

	public UserActionInfo(String msgType, String userName, int userID, String areaID, String createTime, String objectID, String action) {
		// TODO Auto-generated constructor stub
		try {
			userInfo.put(MSGTYPE, msgType);
			userInfo.put(USERNAME, userName);
			userInfo.put(USERID, userID);
			userInfo.put(AREAID, areaID);
			userInfo.put(CREATETIME, createTime);
			userInfo.put(OBJECTID, objectID);
			userInfo.put(ACTION, action);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JSONArray getUserActionInfos() {
		JSONArray array = new JSONArray();
		array.put(userInfo);

		return array;
	}

}
