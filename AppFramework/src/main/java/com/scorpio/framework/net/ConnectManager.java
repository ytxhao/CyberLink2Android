package com.scorpio.framework.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.util.Log;

import com.scorpio.framework.business.protocol.MessageDataKey;
import com.scorpio.framework.config.CoreSchema;
import com.scorpio.framework.core.CoreApplication;
import com.scorpio.framework.core.CoreEngine;
import com.scorpio.framework.core.UICenterDef;
import com.scorpio.framework.utils.SystemUtil;


/**
 */
public class ConnectManager {
	private static ConnectManager instance;

	public static ConnectManager getInstance() {
		if (instance == null) {
			instance = new ConnectManager();
			// init
		}
		return instance;
	}

	private static final String TAG = ConnectManager.class.getSimpleName();
//	private static final String TAGHL = "ConnectManagerHL";
//	private static final String TAGTY = "ConnectManagerTY";

	
	
	/**
	 * 获取网络类型
	 *
	 * @return
	 */
	static public CoreEngine.MobileNetType getMobileNetworkTypeFromMNetStatus(Context ctx) {
		CoreEngine.MobileNetType mntype = CoreEngine.MobileNetType.NO_NETWORK;
		int netStatus = getNetStatus(ctx);
		if (netStatus == TYPE_WIFI) {
			return CoreEngine.MobileNetType.WIFI;
		} else if (netStatus < 0) {
			return CoreEngine.MobileNetType.NO_NETWORK;
		} else {
			switch (netMobileModeStatus) {
			case NETWORK_TYPE_UNKNOWN:
			case NETWORK_TYPE_GPRS:
			case NETWORK_TYPE_EDGE:
			case NETWORK_TYPE_CDMA:
				mntype = CoreEngine.MobileNetType.MOBILE_2G;
				break;
			case NETWORK_TYPE_LTE:
				mntype = CoreEngine.MobileNetType.MOBILE_4G;
				break;
			default:
				mntype = CoreEngine.MobileNetType.MOBILE_3G;
				break;
			}
		}
		return mntype;
	}

	/**
	 * The Default Mobile data connection. When active, all data traffic will
	 * use this connection by default. Should not coexist with other default
	 * connections.
	 */
	public static final int TYPE_MOBILE = 0;
	/**
	 * The Default WIFI data connection. When active, all data traffic will use
	 * this connection by default. Should not coexist with other default
	 * connections.
	 */
	public static final int TYPE_WIFI = 1;

	/* 以下为联通和移动的2G网络信号 */
	/** Network type is unknown */
	public static final int NETWORK_TYPE_UNKNOWN = 0;
	/** Current network is GPRS */
	public static final int NETWORK_TYPE_GPRS = 1;
	/** Current network is EDGE */
	public static final int NETWORK_TYPE_EDGE = 2;

	/** Current network is UMTS */
	/* 这是通用无线通信系统，兼容从GSM到WCDMA，目前广泛被等同于WCDMA */
	public static final int NETWORK_TYPE_UMTS = 3;

	/* 以下为电信的2G/3G网络信号 */
	/** Current network is CDMA: Either IS95A or IS95B */
	/* 这是CDMA 2G的版本 */
	public static final int NETWORK_TYPE_CDMA = 4;
	/** Current network is EVDO revision 0 */
	/* 这是CDMA 3G的版本 包括3种演进的制式，即：CDMA2000 CDMA2000 1X EV-DO和CDMA2000 1X EV-DV */
	public static final int NETWORK_TYPE_EVDO_0 = 5;
	/** Current network is EVDO revision A */
	public static final int NETWORK_TYPE_EVDO_A = 6;
	/** Current network is 1xRTT */
	public static final int NETWORK_TYPE_1xRTT = 7;
	/** Current network is EVDO revision B */
	public static final int NETWORK_TYPE_EVDO_B = 12;

	/* 以下为联通的3G网络信号 */
	/* 以下是WCDMA的3G演进版本，包括HSPA、HSDPA和HSDPA+ */
	/** Current network is HSDPA */
	public static final int NETWORK_TYPE_HSDPA = 8;
	/** Current network is HSUPA */
	public static final int NETWORK_TYPE_HSUPA = 9;
	/** Current network is HSPA */
	public static final int NETWORK_TYPE_HSPA = 10;
	/** Current network is iDen */
	public static final int NETWORK_TYPE_IDEN = 11;
	/** Current network is HSPA+ */
	public static final int NETWORK_TYPE_HSPAP = 15;

	/* 以下为联通的4G网络信号 */
	/** Current network is LTE */
	public static final int NETWORK_TYPE_LTE = 13;

	/* 是CDMA向LTE演进的技术，目前无商用 */
	/** Current network is eHRPD */
	public static final int NETWORK_TYPE_EHRPD = 14;

	public static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

	public void registerDateTransReceiver(Context ctx) {
		Log.i(TAG, "register receiver " + CONNECTIVITY_CHANGE_ACTION);
		IntentFilter filter = new IntentFilter();
		filter.addAction(CONNECTIVITY_CHANGE_ACTION);
		filter.setPriority(Integer.MAX_VALUE);
		ctx.registerReceiver(netReceiver, filter);
	}

	private static int netStatus = -99;

	private static int netMobileModeStatus = -99;
	
	
	
	public static int getNetStatus() {
		return getNetStatus(CoreApplication.getInstance());
	}

	public static int getNetStatus(Context ctx) {
		Log.d(TAG, "get network init..."+(netStatus == -99 || !CoreSchema.IS_USE_NETWORK_STATUS_CACHE));
		if (netStatus == -99 || !CoreSchema.IS_USE_NETWORK_STATUS_CACHE) {
			NetworkInfo ni = SystemUtil.getNetworkInfo(ctx);
			if (ni != null) {
				if (ni.isConnected()) {
					netStatus = ni.getType();
					netMobileModeStatus = ni.getSubtype();
					Log.d(TAG, "network:"+netStatus+","+netMobileModeStatus);
				} else {
					netStatus = -1;
				}
			} else {
				netStatus = -1;
			}
		}
		Log.d(TAG, "the "+netStatus);
		return netStatus;
	}

	public static int getNetMobileModeStatus() {
		return netMobileModeStatus;
	}
	
	public static final int EVENT_NET_STATUS_CHANGE = MessageDataKey.EVENT_NET_STATUS_CHANGE;
	/**
	 * 
	 * 网络监听
	 */
	private final BroadcastReceiver netReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(final Context context, final Intent intent) {
			String action = intent.getAction();
			String v;
			// Bundle infos = intent.getExtras();
			Log.i(TAG, "PfDataTransReceiver receive action " + action);
			if (action.equals(CONNECTIVITY_CHANGE_ACTION)) {// 网络变化的时候会发送通知
				int status = getNetStatus(context);
				UICenterDef.event_default.fireEvent(EVENT_NET_STATUS_CHANGE, status);
				Log.d("net",
						"ischange is not change ......the status is:"
								+ netStatus);
				return;
			}
		}

	};

}
