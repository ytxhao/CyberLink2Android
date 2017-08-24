package com.ytx.cyberlink2android.scorpio.business.http;


import com.ytx.cyberlink2android.scorpio.business.cfg.AppConfig;

/**
 * 网址统一管理类
 *
 */
public class URLCfg {
	
    
    
	@SuppressWarnings("unused")
	private static boolean isRelease = AppConfig.isRelease;
	//-------------------------------------------------
	/**
	 * http请求服务器 ：http://124.207.3.23:80
	 *
	 */
	public static String HTTP_SERVER = AppConfig.isRelease ? "http://smtp.wo.com.cn:2090"
			: "http://124.207.3.23:80";

	/**
	 * 强制升级接口
	 *
	 */
	public static String HTTP_FORCE_UPDATE = HTTP_SERVER+"/aiwmServPortal/rest/clientVersion/clientVersionList";


	/**
	 * 检测升级地址
	 * 
	 */
	public static String HTTP_CHECK_UPDATE = "http://114.247.0.100:3070/aiwmServPortal/version.json";

}
