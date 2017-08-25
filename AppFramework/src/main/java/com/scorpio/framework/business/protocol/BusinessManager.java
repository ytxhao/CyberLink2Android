package com.scorpio.framework.business.protocol;

import com.scorpio.framework.utils.ScoLog;

import java.util.ArrayList;
import java.util.List;


/**
 * 后台业务管理(注册中心)
 * 
 */
public class BusinessManager {

	private static final String TAG = BusinessManager.class.getSimpleName();
	protected static BusinessManager _inst;

	public static BusinessManager getInstance() {
		if (_inst != null) {
			return _inst;
		} else {
			_inst = new BusinessManager();
			return _inst;
		}
	}

	protected BusinessManager() {
		if (BusinessDispatcher.getInstance().optMap != null) {
			BusinessDispatcher.getInstance().optMap.init(null);
		} else {
			ScoLog.E(TAG, "the optMap is null");
		}
	}

	public void onDestroy() {

	}

	@SuppressWarnings("rawtypes")
	static public List<IBusinessOperation> cps;

	@SuppressWarnings("rawtypes")
	protected static void addOperation(IBusinessOperation operation) {
		if (cps == null) {
			cps = new ArrayList<IBusinessOperation>();
		}
		cps.add(operation);
	}

	@SuppressWarnings("rawtypes")
	public IBusinessOperation searchIBusinessOperationByBusinessId(int bid) {
		return BusinessDispatcher.getInstance().optMap.get(bid);
	}

}