package com.ytx.cyberlink2android.scorpio.business.cfg;

public class Constant {

	/**
	 * MENU 通讯录
	 */
	public final static int MENU_CONTACT_ID = 1;
	/**
	 * MENU 今日一看
	 */
	public final static int MENU_TODAY_LOOK_ID = 2;
	/**
	 * MENU 应用
	 */
	public final static int MENU_APP_ID = 3;
	/**
	 * MENU 设置
	 */
	public final static int MENU_SETTING_ID = 4;
	/**
	 * MENU 退出
	 */
	public final static int MENU_LOGOUT_ID = 5;
	/**
	 * 通信生活
	 */
	public final static int MENU_LIFE_ID = 6;

	/**
	 * 显示提示对话框的action
	 */
	public final static String ACTION_ALERT_DIALOG = "com.asiainfo.mail.broadcast.AlertDialog";

	/**
	 * 快速回复intent extra key
	 */
	public final static String ACTION_QUICK_REPLY = "QUICK_REPLY";

	public static final String EXTRA_ACCOUNTID = "accountID";
	public static final String EXTRA_SETTINGFLAG = "settingFlag";

	/**
	 * 震动设置
	 */
	public static final String SETTING_VIBRATE = "setting_vibrate";
	/**
	 * 声音提醒设置
	 */
	public static final String SETTING_WARN_TONE = "setting_warn_tone";
	/**
	 * 声音选择
	 */
	public static final String SETTING_WARN_TONE_SELECT = "setting_warn_tone_select";

	/**
	 * 获取Web联系人超时
	 */
	public static final int GET_WEB_CONTACT_TIME_OUT = 20000;

	/**
	 * 是否正在同步
	 */
	public static Boolean SYN_WEB_CONTACT = false;

	/**
	 * 是否初始化通知栏设置
	 */
	public static final String IS_INIT = "is_init";


}
