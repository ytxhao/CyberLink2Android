package com.ytx.cyberlink2android.scorpio.business.http;


import com.scorpio.framework.business.protocol.http.HttpProtocolEngine;
import com.scorpio.framework.net.http.HPackage;
import com.scorpio.framework.net.http.HPackageFactory;
import com.scorpio.framework.net.http.HttpConfigureSchema;
import com.scorpio.framework.utils.ScoLog;
import com.ytx.cyberlink2android.scorpio.business.cfg.AppConfig;
import com.ytx.cyberlink2android.scorpio.business.cfg.MessageTypeID;
import com.ytx.cyberlink2android.scorpio.business.data.update.CheckUpdateResponseModel;
import com.ytx.cyberlink2android.scorpio.business.opt.update.VersionResponseEntity;


/**
 * 网络连接配置中心
 *
 * 
 */
public class HttpConnectedCfg extends HttpProtocolEngine {

	private static final String TAG = HttpConnectedCfg.class.getSimpleName();
	@Override
	public HPackage sendPackage(int protocolId, Object obj) {
		HPackage f;
		switch (protocolId) {

		/*
		 * 分类说明
		 */
		


		/**
		 * 版本升级
		 */
		case MessageTypeID.VERSION_UPGRADE:
			f = HPackageFactory.get(URLCfg.HTTP_CHECK_UPDATE, null, null);
			f.setClassofT(VersionResponseEntity.class);
			f.setCharset("GBK");
			break;

		case MessageTypeID.CHECK_FORCE_UPDATE://强制升级
			ScoLog.d(TAG,"HttpConnectedCfg CHECK_FORCE_UPDATE");
			f = HPackageFactory.post(URLCfg.HTTP_FORCE_UPDATE, obj, null);
			f.setClassofT(CheckUpdateResponseModel.class);
			//	f.setCharset("GBK");
			break;

		default:
			f = null;
		}
		return f;
	}
	
	static private HttpProtocolEngine inst;

	public static void init() {
		/**
		 * 设定全局超时时间
		 */
		HttpConfigureSchema.DEFAULT_TIMEOUT_SECS = AppConfig.NETWORK_TIMEOUT_SECS;
		if (inst == null) {
			inst = new HttpConnectedCfg();
			HttpConnectedCfg.getInstance().regeditHttpProtocolHandler(inst);
		}
	}

}
