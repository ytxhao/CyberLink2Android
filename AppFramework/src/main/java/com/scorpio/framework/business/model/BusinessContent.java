package com.scorpio.framework.business.model;


import android.os.Bundle;

import com.scorpio.framework.business.protocol.SimpleUIEvent;

import java.io.Serializable;


public class BusinessContent implements Serializable {
	
	public static final int NET_MODE_HTTP_GET = 1;
	
	public static final int NET_MODE_HTTP_POST = 2;
	
	public static final int NET_MODE_XMPP_CHAT = 3;
	
	private int businessId;
	
	private int netMode;//0-10 http 10-20 xmpp;1:get 2:post 3:xmpp
	
	private int resultMode;//1 error判断 2 有无内容 3其他判断
	
	private String url;
	
	private Class classofT;
	
	public Class getClassofT() {
		return classofT;
	}

	public void setClassofT(Class classofT) {
		this.classofT = classofT;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private int okTag;
	
	private int failTag;
	
	private Serializable postValue;
	
	private Bundle getValue;
	
	public Bundle getGetValue() {
		return getValue;
	}

	public void setGetValue(Bundle getValue) {
		this.getValue = getValue;
	}

	public int getBusinessId() {
		return businessId;
	}

	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}

	public Serializable getPostValue() {
		return postValue;
	}

	public void setPostValue(Serializable postValue) {
		this.postValue = postValue;
	}

	private SimpleUIEvent event;

	public int getNetMode() {
		return netMode;
	}

	public void setNetMode(int netMode) {
		this.netMode = netMode;
	}

	public int getResultMode() {
		return resultMode;
	}

	public void setResultMode(int resultMode) {
		this.resultMode = resultMode;
	}

	public int getOkTag() {
		return okTag;
	}

	public void setOkTag(int okTag) {
		this.okTag = okTag;
	}

	public int getFailTag() {
		return failTag;
	}

	public void setFailTag(int failTag) {
		this.failTag = failTag;
	}

	public SimpleUIEvent getEvent() {
		return event;
	}

	public void setEvent(SimpleUIEvent event) {
		this.event = event;
	}

	
	
	
	
}

