package com.ytx.cyberlink2android.application;


import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.ytx.cyberlink2android.utils.ScreenUtil;
import com.ytx.cyberlink2android.utils.YtxLog;


public class LinkApplication extends MultiDexApplication {

    private static String TAG = "LinkApplication";

    public static Context context;



    @Override
    public void onCreate() {
        super.onCreate();

        //读取屏幕相关信息
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ScreenUtil.screenWidth = dm.widthPixels < dm.heightPixels?dm.widthPixels:dm.heightPixels;
        ScreenUtil.screenHeight = dm.widthPixels > dm.heightPixels?dm.widthPixels:dm.heightPixels;
        ScreenUtil.density = dm.density;
        ScreenUtil.densityDpi = dm.densityDpi;
        YtxLog.d(TAG, "screen width:" + ScreenUtil.screenWidth
                        + ", height:" + ScreenUtil.screenHeight
                        + ", density:" + ScreenUtil.density
                        + ", densityDpi:" + ScreenUtil.densityDpi);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}

