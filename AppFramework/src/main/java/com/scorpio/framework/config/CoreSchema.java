package com.scorpio.framework.config;

public class CoreSchema {

	public static boolean IS_SHOW_CONTACT = true;
	public static boolean IS_USE_NETWORK_STATUS_CACHE = false;


	public static boolean isIS_USE_NETWORK_STATUS_CACHE() {
		return IS_USE_NETWORK_STATUS_CACHE;
	}

	public static void setIS_USE_NETWORK_STATUS_CACHE(
			boolean iS_USE_NETWORK_STATUS_CACHE) {
		IS_USE_NETWORK_STATUS_CACHE = iS_USE_NETWORK_STATUS_CACHE;
	}

	public static boolean isIS_SHOW_CONTACT() {
		return IS_SHOW_CONTACT;
	}

	public static void setIS_SHOW_CONTACT(boolean iS_SHOW_CONTACT) {
		IS_SHOW_CONTACT = iS_SHOW_CONTACT;
	}
	
	/**
	 * 是否开启网络监听
	 * 
	 */
	public static boolean IS_USE_CONNECTIVITY_CHANGE_ACTION = false;
	
	

}
