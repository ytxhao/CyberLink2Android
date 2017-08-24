package com.ytx.cyberlink2android.application;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.multidex.MultiDexApplication;

import com.scorpio.framework.core.CoreApplication;

public class LinkApplication extends MultiDexApplication {

    private static String TAG = "LinkApplication";


    /**
     * 单例入口
     *
     */
    private static LinkApplication instance;

    public static Context context;

    private String versionName;
    private int versionCode;
    public ClientInfo localClientInfo;

    public static LinkApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        getMobileInfo();
        fillPackageInfo();
        CoreApplication.init(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }



    /**
     * 当前版本名
     *
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * 当前版本号
     *
     */
    public int getVersionCode() {
        return versionCode;
    }

    /**
     * 初始化获取本地系统信息
     *
     */
    public void getMobileInfo() {
        localClientInfo = new ClientInfo();
        localClientInfo.ClientNativeId = Build.ID;
        localClientInfo.ClientType = 1;
        localClientInfo.OSName = android.os.Build.VERSION.SDK_INT;
        // System.getProperty("os.name");
        localClientInfo.OSVersion = Build.VERSION.RELEASE;// System.getProperty("os.version");
        localClientInfo.PlatformModel = Build.MODEL;
        localClientInfo.PlatformVendor = Build.MANUFACTURER;
        localClientInfo.VersionMajor = Build.VERSION.RELEASE;

    }

    /**
     * 获取当前应用版本信息
     *
     */
    public void fillPackageInfo() {
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (mPackageInfo != null) {
            versionName = mPackageInfo.versionName;
            versionCode = mPackageInfo.versionCode;
        }
    }

}

