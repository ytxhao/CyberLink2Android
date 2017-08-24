package com.scorpio.framework.business.handler;


import com.scorpio.framework.business.protocol.IBusinessOperation;
import com.scorpio.framework.business.protocol.ResultModel;

import java.io.Serializable;



/**
 * 默认业务处理方法
 * 
 *
 */
public abstract class SimpleBusinessOpt implements IBusinessOperation<Object> {

//	private static final String TAG = SimpleBusinessOpt.class.getSimpleName();
	
	protected String ERROR_NO_RIGHT_TYPE = "the obj is not right type.";

	@Override
	abstract public int getBusinessId();


	@Override
	public Serializable preHandle(Serializable obj){
		return doTask(obj);
	}
	abstract public Serializable doTask(Serializable obj);
	
	abstract public void handleSucceedResult(Object obj, ResultModel resultModel);
	
	abstract public void handleFailResult(String failMsg, boolean isNetworkFail, ResultModel resultModel);
	

	@Override
	public void postHandle(ResultModel resultModel) {
//		Log.d("SimpleBusinessOpt", "the business postHandle is:"+resultModel);
		Object obj = resultModel.getObj();
		int result = resultModel.getResultId();
		if (result==ResultModel.TAG_OK){
			handleSucceedResult(obj,resultModel);
		}
		else if((result==ResultModel.TAG_FAIL)||(result==ResultModel.TAG_NETWORK_FAIL)){
			handleFailResult(resultModel.getDate(),(result==ResultModel.TAG_NETWORK_FAIL),resultModel);
		}
	}
	
	

}
