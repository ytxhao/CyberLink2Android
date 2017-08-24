package com.scorpio.framework.core;

/**
 * 逻辑管理器
 * 
 * @author zhangchi
 * 
 */
public class CoreManager {

	private static CoreManager _inst;

	private static final String TAG = CoreManager.class.getSimpleName();

	public static CoreManager getInstance() {
		if (_inst != null) {
			return _inst;
		} else {
			_inst = new CoreManager();
			return _inst;
		}
	}

	private CoreManager() {
		init();
		return;
	}

	private void init() {

	}

}
