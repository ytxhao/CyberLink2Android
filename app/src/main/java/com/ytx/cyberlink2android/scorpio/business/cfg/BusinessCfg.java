package com.ytx.cyberlink2android.scorpio.business.cfg;


import com.scorpio.framework.business.protocol.BusinessManager;
import com.ytx.cyberlink2android.scorpio.business.opt.update.VersionUpgradeForceBusinessOpt;


/**
 * 后台业务配置中心
 *
 *
 */
public class BusinessCfg extends BusinessManager {
	
	static {

		addOperation(VersionUpgradeForceBusinessOpt.getInstance());
	}
	
//	public static BusinessManager getInstance(){
//		if (_inst != null) {
//			return _inst;
//		} else {
//			_inst = new BusinessCfg();
//			return _inst;
//		}
//	}
	
	public static void init(){
		getInstance();
	}

}
