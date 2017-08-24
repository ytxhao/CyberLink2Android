package com.scorpio.framework.business.handler;

import android.util.Log;

import com.scorpio.framework.business.protocol.ResultModel;
import com.scorpio.framework.core.UICenterDef;

import java.io.Serializable;


public class DefaultBusinessOpt extends SimpleBusinessOpt {

	private static final String TAG = DefaultBusinessOpt.class.getSimpleName();

	@Override
	public int getBusinessId() {
		return businessId;
	}

	@Override
	public Serializable doTask(Serializable obj) {
		
		return obj;
	}

	@Override
	public void handleSucceedResult(Object obj, ResultModel resultModel) {
//		Log.d("DefaultBusinessOpt", "the business postHandle ok is:"+obj);
		Serializable sobj = null ;
		if(resultModel.getFromObj()!=null){
			sobj = (Serializable)resultModel.getFromObj();
		}else{
			Log.d(TAG, "the from obj is null...");
		}
		UICenterDef.event_default.fireEvent(getBusinessId(), UICenterDef.CODE_EVENT_OK,sobj, obj);
//		Log.d("DefaultBusinessOpt", "the business postHandle ok is:"+obj);
	}

	@Override
	public void handleFailResult(String failMsg, boolean isNetworkFail,
								 ResultModel resultModel) {
//		Log.d("DefaultBusinessOpt", "the business postHandle fail is:"+isNetworkFail);
		Serializable sobj = null ;
		if(resultModel.getFromObj()!=null){
			sobj = (Serializable)resultModel.getFromObj();
		}else{
			Log.d(TAG, "the from obj is null...");
		}
		int eventCode = isNetworkFail?UICenterDef.CODE_EVENT_NETWORK_FAIL:UICenterDef.CODE_EVENT_FAIL;
		UICenterDef.event_default.fireEvent(getBusinessId(), eventCode,sobj, null);
//		Log.d("DefaultBusinessOpt", "the business postHandle fail is:"+isNetworkFail);
	}
	
	private static DefaultBusinessOpt _inst;
	
	private int businessId;
	
	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}

	public static DefaultBusinessOpt getInstance() {
		if (_inst != null) {
			return _inst;
		} else {
			_inst = new DefaultBusinessOpt();
			return _inst;
		}
	}

}
