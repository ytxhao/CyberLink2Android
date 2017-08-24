package com.scorpio.framework.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;


public class SystemUtil {
	
	private static final String TAG = SystemUtil.class.getSimpleName();

	public static boolean networkAvailable(Context context) {
		if(context==null){
			ScoLog.D(TAG, "context is null");
		}
		NetworkInfo info = getNetworkInfo(context);
		if(info != null) {
			return (info != null && info.isConnected());
		}else {
			return false;
		}
	}

	public static NetworkInfo getNetworkInfo(Context context) {
		NetworkInfo info = null;
		try{
			ConnectivityManager connMan = (ConnectivityManager) context.getApplicationContext()
			.getSystemService(Context.CONNECTIVITY_SERVICE);
			info = connMan.getActiveNetworkInfo();
		}
		catch(Throwable t){
			t.printStackTrace();
		}
		
		return info;
	}
	/** 检查SD卡状态 **/
	public static boolean isSdCardAvailable() {
		String externalStorageState = Environment.getExternalStorageState();
		if (externalStorageState != null)
		{
			return externalStorageState.equals(Environment.MEDIA_MOUNTED);
		}
		
		return false;
	}
	
	/**返回SD卡中剩余可用字节**/
	public static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}
	
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
	
	/**
	 * 判断系统版本是否足够高
	 * 
	 * @param sysVersion
	 * @return
	 */
	public static boolean isSystemVersionWell(int sysVersion){
		boolean r = false;
		int code = android.os.Build.VERSION.SDK_INT;
		r=code>=sysVersion;
		return r;
	}
	
	/**返回SD卡中剩余可用字节**/
	public static long getSDMemorySize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}
	
	
}
