package com.scorpio.framework.core;


import com.scorpio.framework.business.protocol.SimpleEvent;

/**
 * 事件注册中心
 *
 * 
 */
public class UICenterDef {
	public static final String TAG = UICenterDef.class.getSimpleName();
	
	public static final int  CODE_EVENT_OK = 1;
	
	public static final int  CODE_EVENT_FAIL = 2;
	
	public static final int  CODE_EVENT_NETWORK_FAIL = 3;
	
	public static final String KEYWORK_CODE_EVENT = "event";
	
	public static final String KEYWORK_CODE_MESSAGE = "msg";
	
	public static final String KEYWORK_CODE_OBJECT = "obj";
	
	public static final String KEYWORK_CODE_CODE = "code";
	
	

	public UICenterDef() {

	}

	public SimpleEvent download_event = new SimpleEvent();

	public SimpleEvent net_event = new SimpleEvent();

	public SimpleEvent subject_event = new SimpleEvent();
	
	public SimpleEvent unread_event = new SimpleEvent();
	
	static public SimpleEvent event_default = new SimpleEvent();

}
