package com.ytx.cyberlink2android.scorpio.ui.uicenter;

import android.os.Message;
import android.text.TextUtils;
import android.util.Log;


import com.scorpio.framework.core.UICenterDef;
import com.ytx.cyberlink2android.scorpio.business.data.common.IError;
import com.ytx.cyberlink2android.scorpio.business.data.flow.CheckFlowModel;
import com.ytx.cyberlink2android.utils.Msg;

import java.io.Serializable;


/**
 * 用户事件通知中心
 * 
 *
 */
public class UICenter extends UICenterDef {
	public static final String TAG = UICenter.class.getSimpleName();

	public UICenter() {

	}
	
	/**
	 * 用户事件标示定义
	 * 
	 * 请统一在MessageTypeID中定义。
	 * 
	 */
//	public static final String SIMPLE_TASK = "simple_task";
//	

	
	/**
	 * 用户事件触发定义
	 * 
	 */

	static public ModelUIEvent<CheckFlowModel> event_lift = new ModelUIEvent<CheckFlowModel>();
	/**
	 * 登录相关事件
	 * 
	 */
	static public UIEventCfg event_login = new UIEventCfg();
	/**
	 * 账户相关事件
	 * 
	 */
	static public UIEventCfg event_account = new UIEventCfg();

	/**
	 * 消息列表事件
	 */
	static public UIEventCfg event_message = new UIEventCfg();
	
	/**
	 * 设置界面
	 */
	static public UIEventCfg event_setting = new UIEventCfg();
	
	/**
	 * 登录相关事件
	 * 
	 */
	static public UIEventCfg event_ad = new UIEventCfg();

//	/**
//	 * 联系人获取
//	 *
//	 */
//
//	static public ModelUIEvent<GetWebContactsResponseEntity> event_contact = new ModelUIEvent<GetWebContactsResponseEntity>();
//
//
	
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//以下为返回处理封装
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * 获取简单异常描述
	 * 
	 * @param code
	 * @param desc
	 * @return
	 */
	static public String getSimpleDesc(String code, String desc){
		String mDesc = "请稍后重试。";
		if(!TextUtils.isEmpty(code)){
			mDesc = "错误码："+code;
		}
		mDesc = TextUtils.isEmpty(desc)?mDesc:desc;
		return mDesc;
	}
	
	/**
	 * 返回事件处理监听
	 * 
	 * @author zhangchi
	 *
	 * @param <T>
	 */
	static public interface OnEventEx<T,F>{
		public void onEvent(T t, F f);
	}
	
	/**
	 * 返回事件处理监听
	 *
	 *
	 * @param <T>
	 */
	static public interface OnEvent<T>{
		public void onEvent(T t);
	}
	
	/**
	 * 带code的事件处理监听
	 *
	 */
	static public interface OnFailEvent{
		static final int CODE_FAIL_BUSINESS = -1;
		static final int CODE_FAIL_CLASS_ERROR = -2;
		static final int CODE_FAIL_CLASS2_ERROR = -3;
		static final int CODE_FAIL_CONTEXT_NULL = -13;
		static final int CODE_FAIL_NETWORK = -14;
		static final int CODE_FAIL_OTHER = -99;
		public void onEvent(int code, String errorCode, String msg);
	}
	
	
	/**
	 * 简单返回事件处理
	 * 
	 * @param eventCode
	 * @param obj
	 * @param classOfT
	 * @param actionDesc
	 * @param okAction
	 * @param failAction
	 */
	static private <T, F> void onBusinessEventInner(int eventCode, Object obj, Class<T> classOfT, String actionDesc, final OnEvent<T> okAction, OnFailEvent failAction){
		if(okAction!=null){
			onBusinessEventInnerEx(eventCode,obj,null,classOfT,Serializable.class,actionDesc,new OnEventEx<T,Serializable>(){
				@Override
				public void onEvent(T t, Serializable f) {
					okAction.onEvent(t);
				}},failAction);
		}else{
			onBusinessEventInnerEx(eventCode,obj,null,classOfT,Serializable.class,actionDesc,null,failAction);
		}
		
	}
	
	/**
	 * 返回事件处理
	 * @param <F>
	 * 
	 * @param eventCode
	 * @param obj
	 * @param classOfT
	 * @param actionDesc
	 * @param classOfT
	 * @param classOfF
	 * @param okAction
	 * @param failAction
	 */
	static private <T, F> void onBusinessEventInnerEx(int eventCode, Object obj, F fromObj, Class<T> classOfT, Class<F> classOfF, String actionDesc, OnEventEx<T,F> okAction, OnFailEvent failAction){
		String msgvv = "请稍后再试。";
		switch (eventCode) {
		case UICenter.CODE_EVENT_OK:
			String code ,desc ;
			int icode;
			try {
				IError i = (IError)obj;
				code = i.getCode();
				desc = i.getDesc();
			} catch (Exception e) {
				e.printStackTrace();
				code = null;
				desc = "返回格式异常。";
				icode = OnFailEvent.CODE_FAIL_CLASS2_ERROR;
			}
			
			if(IError.CODE_OK.equals(code)){
				try {
					@SuppressWarnings("unchecked")
					T t = (T)obj;
					if(okAction!=null){
						okAction.onEvent(t,fromObj);
					}else{
						Msg.t(actionDesc+"成功。");
					}
					break;
				} catch (Exception e) {
					msgvv = "内容格式异常。";
					icode = OnFailEvent.CODE_FAIL_CLASS_ERROR;
					e.printStackTrace();
				}
				;
			}else{
				icode = OnFailEvent.CODE_FAIL_BUSINESS;
				msgvv = getSimpleDesc(code,desc);
			}
			String msg = actionDesc+"失败,"+msgvv;
			if(failAction!=null){
				failAction.onEvent(icode,code,msg);
			}else{
				Msg.t(msg);
			}
			break;
		case UICenter.CODE_EVENT_FAIL:
			msg = actionDesc+"失败，连接服务器超时。";
			if(failAction!=null){
				failAction.onEvent(OnFailEvent.CODE_FAIL_CONTEXT_NULL,null,msg);
			}else{
				Msg.t(msg);
			}
			break;
		case UICenter.CODE_EVENT_NETWORK_FAIL:
			msg = actionDesc+"失败，请检查网络。";
			if(failAction!=null){
				failAction.onEvent(OnFailEvent.CODE_FAIL_NETWORK,null,msg);
			}else{
				Msg.t(msg);
			}
			break;
		default:
			msg = actionDesc+"发生未知错误，请稍后再试。";
			if(failAction!=null){
				failAction.onEvent(OnFailEvent.CODE_FAIL_OTHER,null,msg);
			}else{
				Msg.t(msg);
			}
			break;
		}
	}
	
	/**
	 * 简单返回事件应答处理
	 * 
	 * @param msg
	 * @param classOfT
	 * @param actionDesc
	 * @param okAction
	 * @param failAction
	 */
	public static <T> void onBusinessEvent(android.os.Message msg, Class<T> classOfT, String actionDesc, OnEvent<T> okAction, OnFailEvent failAction){
		int eventCode = -1;
		if (msg!=null&&msg.getData()!= null) {
			eventCode = msg.getData().getInt(UICenter.KEYWORK_CODE_EVENT);
		}else{
			Msg.t(actionDesc+"失败，连接服务器超时。");
			onBusinessEventInner(eventCode,null,classOfT,actionDesc, okAction, failAction);
			return;
		}
		onBusinessEventInner(eventCode,msg.obj,classOfT,actionDesc, okAction, failAction);
	}

	/**
	 * 简单返回事件应答处理扩展
	 *
	 * @param msg
	 * @param classOfT
	 * @param classOfF
	 * @param actionDesc
	 * @param okAction
	 * @param failAction
	 */
	@SuppressWarnings("unchecked")
	public static <T, F> void onBusinessEventEx(android.os.Message msg, Class<T> classOfT, Class<F> classOfF, String actionDesc, OnEventEx<T,F> okAction, OnFailEvent failAction){
		int eventCode = -1;
		if (msg!=null&&msg.getData()!= null) {
			eventCode = msg.getData().getInt(UICenter.KEYWORK_CODE_EVENT);
		}else{
			Msg.t(actionDesc+"失败，连接服务器超时。");
			onBusinessEventInnerEx(eventCode,null,null,classOfT,classOfF,actionDesc, okAction, failAction);
			return;
		}
		F fromObj = null;
		try {
			Serializable sobj = null;
			if (msg.getData() != null
					&& msg.getData().getSerializable(
							UICenter.KEYWORK_CODE_OBJECT) != null) {
				sobj = msg.getData().getSerializable(
						UICenter.KEYWORK_CODE_OBJECT);
			}
			fromObj = (F)sobj;
		} catch (Exception e) {
			Log.e(TAG, "onBusinessEventEx : task from obj is not right ....:"+actionDesc);
			e.printStackTrace();
		}finally{
			onBusinessEventInnerEx(eventCode,msg.obj,fromObj,classOfT,classOfF,actionDesc, okAction, failAction);
		}
	}
	/**
	 * 获取code
	 * 
	 * @param msg
	 * @return
	 */
	public static int getCodeFromMessage(Message msg) {
		int code = -1;
		if(msg==null){
			return code;
		}
		if (msg!=null&&msg.getData()!= null) {
			code = msg.getData().getInt(UICenter.KEYWORK_CODE_CODE);
		}
		return code;
	}
	
	
	
}
