package com.scorpio.framework.business.protocol;


import com.scorpio.framework.business.model.BusinessContent;
import com.scorpio.framework.net.http.interfaces.HResponse;

import java.io.Serializable;



public class ResultModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7053325841831525983L;
	/**
	 * 网络、业务层共用
	 */
	static public int TAG_NETWORK_FAIL = 2;
	/**
	 * 网络、业务层共用
	 */
	static public int TAG_OK = 3;
	/**
	 * 网络、业务层共用
	 */
	static public int TAG_FAIL = 4;
	
	public ResultModel(){
		
	}
	
	public ResultModel(HResponse hrm){
		this.setResponseId(hrm.getResponseCode());
		this.setResultId(hrm.getResultId());
		this.setTag(hrm.getTag());
		this.setObj(hrm.getMessage());
		this.setFromObj(hrm.getFromModel());
		if(hrm.getHttpResponse()!=null){
			this.setDate(hrm.getHttpResponse().getDate());
		}
	}
	
//	public ResultModel(HPackage xrm){
//		this.setFrom(xrm.getFrom());
//		this.setTo(xrm.getTo());
//		this.setDelay(xrm.getDelay());
//		this.setObj(xrm.getObj());
//		this.setResultId(xrm.getResult());
//	}
	
	/**
	 * 业务标示字段
	 */
	
	private int tag;
	
	private String tagKey;
	
	private String date;
	
	/**
	 * http字段及结果字段
	 */
	
	private int errno;
	
	private int responseId;
	
	private int resultId;
	
	private int subResultId;
	
	
	/**
	 * 业务字段
	 */
	
	private Object obj;
	
	private Object fromObj;
	
	private int arg1;
	
	private int arg2;
	
	private BusinessContent bcnt;
	
	/**
	 * xml字段
	 */
	
	private String from;
	
	private String to;
	
//	private Delay delay ;
	
	private String template;
	
	
	//------------------------------------------------------------------------------------------------------------------
	
	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getErrno() {
		return errno;
	}

	public void setErrno(int errno) {
		this.errno = errno;
	}

	public int getResponseId() {
		return responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}

	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	public int getSubResultId() {
		return subResultId;
	}

	public void setSubResultId(int subResultId) {
		this.subResultId = subResultId;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public int getArg1() {
		return arg1;
	}

	public void setArg1(int arg1) {
		this.arg1 = arg1;
	}

	public int getArg2() {
		return arg2;
	}

	public void setArg2(int arg2) {
		this.arg2 = arg2;
	}

	public String getTagKey() {
		return tagKey;
	}

	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

//	public Delay getDelay() {
//		return delay;
//	}
//
//	public void setDelay(Delay delay) {
//		this.delay = delay;
//	}
//
	public BusinessContent getBcnt() {
		return bcnt;
	}

	public void setBcnt(BusinessContent bcnt) {
		this.bcnt = bcnt;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Object getFromObj() {
		return fromObj;
	}

	public void setFromObj(Object fromObj) {
		this.fromObj = fromObj;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	

}

