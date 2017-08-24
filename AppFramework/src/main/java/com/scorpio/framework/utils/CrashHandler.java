package com.scorpio.framework.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * UncaughtException
 *
 */
public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = CrashHandler.class.getSimpleName();

	private UncaughtExceptionHandler mDefaultHandler;

	private Map<String, String> infos = new HashMap<String, String>();
	private Map<String, String> myinfos = new HashMap<String, String>();

    Context ctx;

	private boolean isHasCrash = false;

	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	
	private static DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

	private CrashHandler() {
	}

	public static CrashHandler getInstance() {

			return new CrashHandler();
	}

	public void init() {
		if(isHasCrash){
			return;
		}
		UncaughtExceptionHandler DefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		if(DefaultHandler!=null&&((DefaultHandler instanceof CrashHandler)||DefaultHandler.equals(mDefaultHandler))){
			//do nothing...
		}else{
			mDefaultHandler = DefaultHandler;
			Thread.setDefaultUncaughtExceptionHandler(this);
		}		
	}

    public void init(Context ctx){
        this.ctx = ctx;
        init();
    }

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		ex.printStackTrace();
		if (!isHasCrash&&!handleException(ex) && mDefaultHandler != null&&!mDefaultHandler.equals(this)) {
			isHasCrash = true;
			mDefaultHandler.uncaughtException(thread, ex);
			mDefaultHandler = null;			
		} else {
			if(isHasCrash){
				Log.e(TAG, "crash same !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			}
			isHasCrash = true;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}			
			closed();
		}
		
	}
	
	public void closed(){
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(1);
	}
	
	public void closed2(){
		exit2(ctx);
	}
	
	private void exit2(Context contact) {
		ActivityManager actMgr = (ActivityManager) contact .getSystemService(Context.ACTIVITY_SERVICE);
		actMgr.restartPackage(contact.getPackageName());
	} 

	public boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		final Context mContext = ctx;

		collectDeviceInfo(mContext);
		collectFanxerInfo();
		saveCrashInfo2File(ex);
		return false;
	}

	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	private void collectFanxerInfo() {

	}

	private void put(String key, String value) {
		myinfos.put(key, value);
		Log.e(TAG, key + ":" + value);
	}

	private String saveCrashInfo2File(Throwable ex) {

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append("device:" + key + "=" + value + "\n");
		}
		for (Map.Entry<String, String> entry : myinfos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append("fx:" + key + "=" + value + "\n");
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
			long timestamp = System.currentTimeMillis();
			String time = formatter.format(new Date());
			String fileName = "my-crash-" + time + "-" + timestamp + ".log";
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String path = "/sdcard/MyCrash/";
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path + fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
			}
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
		return null;
	}
	
	static public String saveInfo2File(String info){
		// StringBuffer sb = new StringBuffer(info);
// 		Writer writer = new StringWriter();
		// PrintWriter printWriter = new PrintWriter(writer);
// 		ex.printStackTrace(printWriter);
// 		Throwable cause = ex.getCause();
// 		while (cause != null) {
// 			cause.printStackTrace(printWriter);
// 			cause = cause.getCause();
// 		}
// 		printWriter.close();
// 		String result = writer.toString();
// 		sb.append(result);
		try {
			long timestamp = System.currentTimeMillis();
			String time = formatter2.format(new Date());
			String fileName = "my-info-" + time  + ".log";
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String path = "/sdcard/MyInfo/";
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path + fileName,true);
				info = info+"\r\n";
				fos.write(info.toString().getBytes());
				fos.close();
			}
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
		return null;
	}
}
