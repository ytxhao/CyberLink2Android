package com.scorpio.framework.net.http.interfaces;

import android.os.Bundle;

import com.scorpio.framework.business.protocol.MessageDataKey;
import com.scorpio.framework.business.protocol.ResultModel;
import com.scorpio.framework.core.CoreEngine;
import com.scorpio.framework.net.http.HttpRequest.HHttpResponse;
import com.scorpio.framework.net.http.protocol.SimpleHttpHandle;
import com.scorpio.framework.utils.ScoLog;



/**
 * MessageListener实现处理类
 *
 *
 */
public class HandleMessageListener implements MessageListener {
	private static final String TAG = HandleMessageListener.class.getSimpleName();
	int id;
	@SuppressWarnings("rawtypes")
	IHttpProtocol fhp;
	public HResponse hrm;
	
	public HandleMessageListener(int id,Object fromobj){
		this.id = id;
		if(fhp==null){
			fhp = SimpleHttpHandle.getInstance();
		}
		this.hrm = new HResponse();
		hrm.setTag(id);
		hrm.setFromModel(fromobj);
	}

	@Override
	public void onFailed(HttpResponseException err) {
		// TODO Auto-generated method stub
		hrm.setErr(err);
		hrm.setResultId(HResponse.TAG_NETWORK_FAIL);
		hrm.setResponseCode(err.getResponseCode());		
		ScoLog.E(TAG, "err...the result is:");
		handleResponse(false);
	}

	@Override
	public void onMessage(Object message, HHttpResponse response) {
		// TODO Auto-generated method stub
		hrm.setHttpResponse(response);
		hrm.setResultId(HResponse.TAG_MESSAGE);
		hrm.setMessage(message);
		hrm.setResponseCode(response.getRequestCode());
		ScoLog.E(TAG, "ok...the result is:"+hrm.getResponseCode());
		handleResponse(true);
	}
	
	private void handleResponse(boolean isok){
		ResultModel response = fhp.HandleResponse(hrm);
//		fhp.getResponseClass().cast(obj);
		android.os.Message msg = new android.os.Message();
		msg.what = id;		
		if(response!=null){
			Bundle b = new Bundle();
			msg.what = response.getTag();
//			response.setDate(response.getDate());
			b.putSerializable(MessageDataKey.SIMPLE_TASK, response);
			msg.setData(b);
		}		
		if(isCanCallbackToEngneer(msg.what)){
			CoreEngine.getInstance().getBusinessCenter().sendMessage(msg, true,true);
		}else{
			ScoLog.E(TAG,"the task:"+id+". will be killed by frameworks.....");
		}		
	}
	
	private boolean isCanCallbackToEngneer(int taskId){
		boolean r = true;
			
		return r;
	}
	
}
