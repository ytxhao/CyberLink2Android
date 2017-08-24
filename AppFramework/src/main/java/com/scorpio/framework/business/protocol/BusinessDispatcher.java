package com.scorpio.framework.business.protocol;

import android.os.Bundle;
import android.os.Message;

import com.scorpio.framework.business.handler.DefaultBusinessOpt;
import com.scorpio.framework.business.model.FailTaskTag;
import com.scorpio.framework.core.CoreEngine;
import com.scorpio.framework.core.CoreService;
import com.scorpio.framework.data.dm.DataManagerInterface;
import com.scorpio.framework.utils.CrashHandler;
import com.scorpio.framework.utils.ScoLog;

import java.io.Serializable;



public class BusinessDispatcher {

	private static final String TAG = BusinessDispatcher.class.getSimpleName();
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 7420254352639458269L;
	final static public int ENGINE_TASK_MSG_SEND = 2;
	final static public int ENGINE_TASK_MSG_RECEIVE = 4;
	final static public int ENGINE_TASK_MSG_HTTP = 1;
	final static public int ENGINE_TASK_MSG_XMPP = 3;


	static private BusinessDispatcher _ins;

	public void handle(Message msg, Serializable obj){
		int id = msg.what;
		if(msg.arg1==ENGINE_TASK_MSG_SEND){
			handle(id,obj,false,msg.arg2,msg);
		}else if(msg.arg1==ENGINE_TASK_MSG_RECEIVE){
			handle(id,obj,true,msg.arg2,msg);
		}else{
//			oldHandle(id/10000,msg);
			ScoLog.E(TAG, "BusinessDispatcher's msg 's arg1 is error:no such number task:"+msg.arg1);
		}
	}

	@SuppressWarnings("rawtypes")
	public DataManagerInterface<Integer,IBusinessOperation> optMap;


	public static BusinessDispatcher getInstance()
	{
		if(_ins!=null)
		{
			return _ins;
		}
		else
		{
			_ins=new BusinessDispatcher();
			return _ins;
		}
	}

	/**
	 * 消息处理方法
	 *
	 * @param id
	 * @param obj
	 * @param isBack
	 * @param networkMode
	 * @param msg2
	 */

	@SuppressWarnings("rawtypes")
	private void handle(int id, Serializable obj, boolean isBack, int networkMode, Message msg2) {
		IBusinessOperation fhp = BusinessManager.getInstance().searchIBusinessOperationByBusinessId(id);
		if(fhp==null){
//			return;
			fhp = DefaultBusinessOpt.getInstance();
			DefaultBusinessOpt.getInstance().setBusinessId(id);
		}
		if(isBack){
				CoreService.exec(new BusinessPostTask(obj,networkMode,fhp,id,msg2));
		}else{
				CoreService.exec(new BusinessProTask(obj,networkMode,fhp,id));
		}
	}

	private class BusinessPostTask implements Runnable {

		private Serializable obj;

		@SuppressWarnings("rawtypes")
		private IBusinessOperation fhp;
		private int id;
		private Message msg2;
		private int networkMode;

		@SuppressWarnings("rawtypes")
		public BusinessPostTask(Serializable obj, int networkMode, IBusinessOperation fhp, int id, Message msg2){
			this.obj = obj;
			this.networkMode = networkMode;
			this.fhp = fhp;
			this.id = id;
			this.msg2 = msg2;
		}

		@Override
		public void run() {
			try {				
				fhp.postHandle((ResultModel) obj);
				long taskResId = msg2.getData().getLong("resTaskId",0);
				if(taskResId>0){
					ResultModel rm = (ResultModel)obj;
					int result = rm.getResultId();
					if (result==ResultModel.TAG_OK){
						// todo something...
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				CrashHandler.getInstance().handleException(e);
			}finally{
				ScoLog.D(TAG, "post end----"+removeTi()+"--------"+obj+"---"+msg2+"----------------------------------------------------------------------------------------------");
			}
		}
	}

	private static int ti = 0;
	
	@SuppressWarnings("unused")
	private synchronized int getTi(){
		ti++;
		return ti;
	}
	
	private synchronized int removeTi(){
		ti--;
		return ti;
	}
	
	private class BusinessProTask implements Runnable {

		private Serializable obj;
		private int networkMode;
		@SuppressWarnings("rawtypes")
		private IBusinessOperation fhp;
		private int id;

		@SuppressWarnings("rawtypes")
		public BusinessProTask(Serializable obj, int networkMode, IBusinessOperation fhp, int id){
			this.obj = obj;
			this.networkMode = networkMode;
			this.fhp = fhp;
			this.id = id;
		}

		@Override
		public void run() {
//			Log.d("ct", "pre begin------"+getTi()+"----"+obj+"---"+id+"---------------------------------------------------------------------------------------------");
			try {
				Serializable doneObj = null;
				try {
					doneObj = fhp.preHandle(obj);
				} catch (Exception e) {
//					ConnectManager.writeBusinessInfoAsyn("error:pro:"+id+":" + e);
					e.printStackTrace();
				}
				if (doneObj != null && doneObj instanceof FailTaskTag) {
					ScoLog.D(TAG, "the task is end.");
					return;
				}
				if (networkMode == ENGINE_TASK_MSG_XMPP) {
					//				ChatMsg  msg = (ChatMsg)obj;
					//now just the txt msg;
//					cXmppBoss.handle(id, doneObj);
				} else {
					Message msg = new Message();
					msg.what = id;
					Bundle b = new Bundle();
					if (doneObj != null) {
						b.putSerializable(MessageDataKey.SIMPLE_TASK, doneObj);
					}
					msg.setData(b);
					CoreEngine.getInstance().getHttpCenter().getHandler()
							.sendMessage(msg);
				}
			}catch (Exception e) {
				e.printStackTrace();
				CrashHandler.getInstance().handleException(e);
			}finally{
//				Log.d("ct", "pre end-------"+removeTi()+"--------"+obj+"---"+id+"-------------------------------------------------------------------------------------------");
			}			
		}
	}


	private BusinessDispatcher(){
		optMap = new BusinessOperationMap();
	}



	/**
	 * 存储待运行任务项
	 *
	 */
	public boolean saveBusinessTask(Message msg){
		boolean r = false;
		return r;
	}

	/**
	 * 执行该待运行任务。
	 *
	 * @param path
	 * @param id
	 * @return
	 */
	public boolean runExeResTask(String path, long id){
		boolean r = false;
		return r;
	}
}
