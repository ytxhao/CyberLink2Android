package com.ytx.cyberlink2android.scorpio.business.cfg;

/**
 * App总配置类
 *
 */
public class AppConfig {
	/**
	 * push是否是正式环境，false为正式环境
	 */
	public static final boolean pushIsOfficial = false;

	/**
	 * 是否连接正式服务器
	 *
	 */
	public static final boolean isRelease = true;

	/**
	 * 是否启动本地文件记录日志
	 *
	 */
	public static boolean IS_USE_FILE_LOG = false;

	/**
	 * 网络超时时间（秒）
	 *
	 */
	public static final int NETWORK_TIMEOUT_SECS = 15;

}
