package com.scorpio.framework.business.protocol;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import com.scorpio.framework.utils.ScoLog;

import java.io.Serializable;

public class BusinessCenter {
	private static final String TAG = BusinessCenter.class.getSimpleName();
	private static BusinessCenter _ins;
	public Handler mHandler;
	
	public BusinessCenter()
	{
		 mHandler = new Handler() {
             public void handleMessage(Message msg) {
               // process incoming messages here 
            	 ScoLog.E(TAG, "the rich msg 's :"+msg+".................................................");
            	Bundle r=msg.getData();
				Serializable s =r.getSerializable(MessageDataKey.SIMPLE_TASK);
				BusinessDispatcher.getInstance().handle(msg, s);
             }
         };
	}
	
	public Handler getHandler()
	{
		return mHandler;
	}
	
	void sendMessage(Message msg, boolean isBack){
		sendMessage(msg,isBack,true);
	}
	
	public void sendMessage(Message msg, boolean isBack, boolean isHttp){
		if(isBack){
			msg.arg1 = BusinessDispatcher.ENGINE_TASK_MSG_RECEIVE;
		}else{
			msg.arg1 = BusinessDispatcher.ENGINE_TASK_MSG_SEND;
		}
		if(isHttp){
			msg.arg2 = BusinessDispatcher.ENGINE_TASK_MSG_HTTP;
		}else{
			msg.arg2 = BusinessDispatcher.ENGINE_TASK_MSG_XMPP;
		}
		getHandler().sendMessage(msg);
	}
	
	/**
	 * 激发引擎处理相应行为。
	 * 
	 * @param BusinessId：该值必须在{@link MessageTypeID}中指定。
	 * 
	 * @param obj
	 */
	public void sendBeginBusinessMessage(int BusinessId,Serializable obj){
		sendBeginMessageInner(BusinessId,obj,true);
	}
	
	/**
	 * 激发引擎处理Im相应任务
	 * 
	 * @param BusinessId
	 * @param obj
	 */
	private void sendBeginIMMessage(int BusinessId,Serializable obj){
		sendBeginMessageInner(BusinessId,obj,false);
	}
	
	
	private void sendBeginMessageInner(int BusinessId, Serializable obj, boolean isHttp){
		Message msg = mHandler.obtainMessage();
		msg.what = BusinessId;
		Bundle b = new Bundle();
		b.putSerializable(MessageDataKey.SIMPLE_TASK, obj);
		msg.setData(b);
		sendMessage(msg,false,isHttp);
	}
}

