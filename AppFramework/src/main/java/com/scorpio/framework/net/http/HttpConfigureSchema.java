package com.scorpio.framework.net.http;


import com.scorpio.framework.config.CoreSchema;

public class HttpConfigureSchema {

//	public final static String WEB_URL = "http://"+HttpConfigureSchema.WEB_HOST+":"+HttpConfigureSchema.WEB_PORT;
//
//	public final static String RES_URL = "http://"+HttpConfigureSchema.WEB_HOST+":"+HttpConfigureSchema.RES_PORT;

	public final static String PASSPORT_URL = "http://"+HttpConfigureSchema.PASSPORT_HOST+":"+HttpConfigureSchema.WEB_PORT;

	public final static String PASSPORT_HOST = "192.168.50.0";

//	public final static String WEB_HOST =CoupleIMSchema.IS_DEV_VERSION? "192.168.50.0":"192.168.50.8";//开发服务器:测试服务器

	public final static String WEB_PORT = "8080";

	public final static String RES_PORT = "9000";

	public static int DEFAULT_TIMEOUT_SECS = 30;

	public static final int S_CORE_POOL_SIZE = 2;

	public static final int CORE_POOL_SIZE = 2;

	public static final int MAXIMUM_POOL_SIZE = 999;

	public static final int KEEP_ALIVE = 1;

	public static final int HTTP_KEEP_ALIVE = 5;

	public static final boolean isEncodingGzip = false;
	
	public static final boolean IS_USED_MUTITHREADS_MANAGER = false;

	public static final boolean isDebug = CoreSchema.IS_SHOW_CONTACT;//用于日志调试，网络细节

	public static final boolean isShowContent = CoreSchema.IS_SHOW_CONTACT;//用于日志调试，网络过程

	

}
