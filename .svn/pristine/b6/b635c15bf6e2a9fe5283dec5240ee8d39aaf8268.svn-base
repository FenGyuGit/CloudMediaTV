package com.geeya.wifitv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.geeya.wifitv.utils.Tools;

/**
 * UncaughtExceptionHandler异常处理
 * 
 * @author Administrator
 *
 */
public class AppExceptionHandler implements Thread.UncaughtExceptionHandler {
	
	private String tag = "AppExceptionHandler";

	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler defaultHandler;
	private static AppExceptionHandler instance = new AppExceptionHandler();
	private Context context;
	private Map<String, String> infos = new HashMap<String, String>();
	
	private AppExceptionHandler() {
		
	}
	
	/**
	 * 单例模式
	 * 
	 * @return
	 *
	 * Created by Administrator
	 * Created on 2015-9-30 上午9:33:39
	 */
	public static AppExceptionHandler getInstance() {
		return instance;
	}
	
	public void init(Context context) {
		this.context = context;
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	
	// 异常发生后的处理函数
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		if (!handleException(ex) && defaultHandler != null) {
			// 如果用户未做处理则默认处理
			defaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ie) {
				Log.e(tag, "error : " + ie);
			}
			// 退出程序
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 自定义错误处理类
	 * 
	 * @param throwable
	 * @return
	 *
	 * Created by Administrator
	 * Created on 2015-9-30 上午9:43:21
	 */
	private boolean handleException(Throwable throwable) {
		if (throwable == null) {
			return false;
		}
		// 使用Toast显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(context, "程序出现异常！", Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
		// 收集设备参数信息
		collectDeviceInfo(context);
		// 保存日志
		saveCrashInfo2File(throwable);
		return true;
	}
	
	/**
	 * 收集设备参数
	 * 
	 * @param context
	 *
	 * Created by Administrator
	 * Created on 2015-9-30 上午10:07:14
	 */
	public void collectDeviceInfo(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException nnfe) {
			Log.e(tag, "an error occured when collect package info" + nnfe.getMessage());
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
			} catch (Exception e) {
				Log.e(tag, "an error occured when collect crash info");
			}
		}
	}
	
	/**
	 * 保存异常信息到文件
	 * 
	 * @param ex
	 *
	 * Created by Administrator
	 * Created on 2015-9-30 上午10:59:15
	 */
	private void saveCrashInfo2File(Throwable ex) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}
		
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
			String currentTime = Tools.getCurrentTime();
			String fileName = "crash-" + currentTime + ".log";
			File cacheFile = context.getCacheDir();
			if (!cacheFile.isDirectory()) {
				cacheFile.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(cacheFile +  "/" + fileName);
			fos.write(sb.toString().getBytes());
			fos.close();
		} catch (Exception e) {
			Log.e(tag, "an error occured when write file" + e);
		}
	}
	
	
	

}
