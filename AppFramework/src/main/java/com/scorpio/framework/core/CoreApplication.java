package com.scorpio.framework.core;

import android.app.Application;
import android.util.DisplayMetrics;

import com.scorpio.framework.utils.ScoLog;
import com.scorpio.framework.utils.ScreenUtil;

/**
 * 全局上下文
 *
 */
public class CoreApplication  {

	private static final String TAG = CoreApplication.class.getSimpleName();

	static private Application ins;

	public static Application getInstance() {
		return ins;
	}
	
	public static void init(Application context){
		ins = context;
		//读取屏幕相关信息
		DisplayMetrics dm = ins.getResources().getDisplayMetrics();
		ScreenUtil.screenWidth = dm.widthPixels < dm.heightPixels?dm.widthPixels:dm.heightPixels;
		ScreenUtil.screenHeight = dm.widthPixels > dm.heightPixels?dm.widthPixels:dm.heightPixels;
		ScreenUtil.density = dm.density;
		ScreenUtil.densityDpi = dm.densityDpi;
		ScoLog.d(TAG, "screen width:" + ScreenUtil.screenWidth
				+ ", height:" + ScreenUtil.screenHeight
				+ ", density:" + ScreenUtil.density
				+ ", densityDpi:" + ScreenUtil.densityDpi);
	}

}
