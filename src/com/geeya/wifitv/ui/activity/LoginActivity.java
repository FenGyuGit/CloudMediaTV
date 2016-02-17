package com.geeya.wifitv.ui.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import cn.geeya.tpc.authentication.AccessTokenKeeper;
import cn.geeya.tpc.authentication.QQConstants;
import cn.geeya.tpc.authentication.SinaConstants;
import cn.geeya.tpc.authentication.Util;
import cn.geeya.tpc.authentication.WXConstants;

import com.geeya.wifitv.R;
import com.geeya.wifitv.bean.UserInfo;
import com.geeya.wifitv.presenter.user.UserAction;
import com.geeya.wifitv.presenter.user.UserActionImpl;
import com.geeya.wifitv.ui.interf.ILogin;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginActivity extends BaseActivity implements ILogin, OnClickListener {
	private EditText metuserName;
	private EditText metuserPasswd;
	private Button mbtnLogin;
	private Button mbtnRegister;
	private ImageButton ibtnSinaAuthor;
	private ImageButton ibtnWeiXinAuthor;
	private ImageButton ibtnQQAuthor;
	private UserAction userAction;

	/** sina API */
	private SsoHandler mSsoHandler;// 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
	private Oauth2AccessToken mAccessToken;// "access_token"，"expires_in"，"refresh_token"，并提供了管理功能
	private AuthInfo mAuthInfo;
	/** weixin API */
	private IWXAPI api;
	/** QQ API */
	private static Tencent mTencent;
	private com.tencent.connect.UserInfo mInfo;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		userAction = new UserActionImpl(this);
		/** sina Author */
		mAuthInfo = new AuthInfo(this, SinaConstants.APP_KEY, SinaConstants.REDIRECT_URL, SinaConstants.SCOPE);
		mSsoHandler = new SsoHandler(this, mAuthInfo);
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		/** weixin Author */
		api = WXAPIFactory.createWXAPI(getApplicationContext(), WXConstants.APP_ID);
		api.registerApp(WXConstants.APP_ID);
		/** QQ Author */
		mTencent = Tencent.createInstance(QQConstants.APP_ID, context);
	}

	private void login() {
		mbtnLogin.setEnabled(false);
		this.userAction.userLogin();
	}

	public void initView() {
		metuserName = (EditText) findViewById(R.id.user_login_name);
		metuserPasswd = (EditText) findViewById(R.id.user_login_password);
		mbtnLogin = (Button) findViewById(R.id.user_login_bt_loginbutton);
		mbtnRegister = (Button) findViewById(R.id.user_login_bt_registbutton);
		ibtnSinaAuthor = (ImageButton) findViewById(R.id.login_ibtn_sina);
		ibtnWeiXinAuthor = (ImageButton) findViewById(R.id.login_ibtn_weixin);
		ibtnQQAuthor = (ImageButton) findViewById(R.id.login_ibtn_qq);
		mbtnLogin.setOnClickListener(this);
		mbtnRegister.setOnClickListener(this);
		ibtnSinaAuthor.setOnClickListener(this);
		ibtnWeiXinAuthor.setOnClickListener(this);
		ibtnQQAuthor.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.user_login_bt_loginbutton:
			login();			
			break;
		case R.id.user_login_bt_registbutton:
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.login_ibtn_sina:
			mSsoHandler.authorizeClientSso(new AuthListener());
			break;
		case R.id.login_ibtn_weixin:
			final SendAuth.Req req = new SendAuth.Req();
			req.scope = "snsapi_userinfo";
			req.state = "wechat_sdk_demo_test";
			api.sendReq(req);
			break;
		case R.id.login_ibtn_qq:
			doQQLogin();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
		if (mSsoHandler != null && requestCode != 0) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}

		Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
		if (requestCode == Constants.REQUEST_API) {
			if (resultCode == Constants.RESULT_LOGIN) {
				Tencent.handleResultData(data, loginListener);
			}
		} else if (requestCode == Constants.REQUEST_APPBAR) { // app��Ӧ�ðɵ�¼
			if (resultCode == Constants.RESULT_LOGIN) {
				updateUserInfo();
				// Util.showResultDialog(LoginActivity.this,
				// data.getStringExtra(Constants.LOGIN_INFO), "登录成功");
			}
		}
		if(requestCode == 0 && resultCode == RESULT_OK){
			finish();
		}
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return metuserName.getText().toString().trim();
	}

	@Override
	public String getUserPassword() {
		// TODO Auto-generated method stub
		return metuserPasswd.getText().toString().trim();
	}

	@Override
	public void showToast(String message) {
		// TODO Auto-generated method stub
		Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
		toast.show();
		mbtnLogin.setEnabled(true);
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		LoginActivity.this.finish();
	}

	/**
	 * sina微博认证说明 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener {
		UsersAPI mUsersApi = null;

		@Override
		public void onComplete(Bundle values) {
			mUsersApi = new UsersAPI(context, null, mAccessToken);
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			// 从这里获取用户输入的 电话号码信息
			// String phoneNum = mAccessToken.getPhoneNum();
			if (mAccessToken.isSessionValid()) {
				long uid = Long.parseLong(mAccessToken.getUid());				
				// 从 Bundle 中解析 Token
				
				AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
				mUsersApi.show(uid, mListener);				
			} else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = values.getString("code");
				String message = getString(R.string.weibosdk_demo_toast_auth_failed);
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(), R.string.weibosdk_demo_toast_auth_uninstall, Toast.LENGTH_LONG).show();
		}
	}

	private RequestListener mListener = new RequestListener() {
		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {
				// 调用User#parse将JSON串解析成User对象
				User user = User.parse(response);
				UserInfo userInfo = new UserInfo(user.url, user.weihao, null, user.screen_name, null, user.created_at, 0, user.idstr, user.avatar_hd);
				application.setUserInfo(userInfo);
				Toast.makeText(getApplicationContext(), R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
				LoginActivity.this.finish();
			}else {
				Toast.makeText(getApplicationContext(), R.string.weibosdk_demo_toast_auth_failed, Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		public void onWeiboException(WeiboException arg0) {
			// TODO Auto-generated method stub

		}
	};

	/** QQ login */
	private void doQQLogin() {
		mTencent.login(this, "all", loginListener);
	}

	IUiListener loginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject values) {
			initOpenidAndToken(values);
			updateUserInfo();
		}
	};

	public static void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
			String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openId);
			}
		} catch (Exception e) {
		}
	}

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			if (null == response) {
				Util.showResultDialog(LoginActivity.this, "����Ϊ��", "��¼ʧ��");
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				// Util.showResultDialog(LoginActivity.this, "123", "456");
				return;
			}
			// Util.showResultDialog(LoginActivity.this, response.toString(),
			doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			Util.toastMessage(LoginActivity.this, "onError: " + e.errorDetail);
			Util.dismissDialog();
		}

		@Override
		public void onCancel() {
			// Util.toastMessage(LoginActivity.this, "onCancel: ");
			Util.dismissDialog();
		}
	}

	private void updateUserInfo() {
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onError(UiError e) {

				}

				@Override
				public void onComplete(final Object response) {
					JSONObject user = (JSONObject) response;
					String username = null;
					String userface = null;
					try {
						username = user.getString("nickname");
						userface = user.getString("figureurl_qq_2");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					UserInfo userInfo = new UserInfo(null, null, null, username, null, null, 0, "0", userface);
					application.setUserInfo(userInfo);
					finish();
				}

				@Override
				public void onCancel() {

				}
			};
			mInfo = new com.tencent.connect.UserInfo(getBaseContext(), mTencent.getQQToken());
			mInfo.getUserInfo(listener);

		}
	}
}
