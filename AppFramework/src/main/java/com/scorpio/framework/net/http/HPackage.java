package com.scorpio.framework.net.http;

import android.os.Bundle;

import com.scorpio.framework.business.model.BusinessContent;
import com.scorpio.framework.net.http.interfaces.HttpRequestProgressListener;
import com.scorpio.framework.net.http.interfaces.MessageListener;

import java.io.File;
import java.util.ArrayList;


/**
 * 协议请求包封装格式
 *
 *
 */
public class HPackage {
	
	private int pId;//协议id（任务id）
	
	private String url;//请求网址
	
	private Object postObject;//请求内容
	
	private Bundle headers;//头
	
	private Bundle params;//参数
	
	private File downsavefile;//下载文件
	
	private ArrayList<File> updatefiles;//上传文件
	
	private String uploadtype;//上传文件类型
	
	private int requestType;//请求类型
	
	private int contentType;//内容封装类型
	
	private boolean isdelay;//是否延迟(已废)
	
	private int timeoutSecs = HttpConfigureSchema.DEFAULT_TIMEOUT_SECS;//超时时间
	
	private BusinessContent bct;
	
	private HttpRequestProgressListener progress;
	
	private MessageListener messageListener;
	
	private String charset;
	
	
	
	
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public BusinessContent getBct() {
		return bct;
	}
	public void setBct(BusinessContent bct) {
		this.bct = bct;
	}

	private Class classofT;
	
	public Class getClassofT() {
		return classofT;
	}
	public void setClassofT(Class classofT) {
		this.classofT = classofT;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	
	public int getTimeoutSecs() {
		return timeoutSecs;
	}
	public void setTimeoutSecs(int timeoutSecs) {
		this.timeoutSecs = timeoutSecs;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Object getPostObject() {
		return postObject;
	}
	public void setPostObject(Object post) {
		this.postObject = post;
	}	
	public Bundle getHeaders() {
		return headers;
	}
	public void setHeaders(Bundle headers) {
		this.headers = headers;
	}
	public Bundle getParams() {
		return params;
	}
	public void setParams(Bundle params) {
		this.params = params;
	}
	public File getDownsavefile() {
		return downsavefile;
	}
	public void setDownsavefile(File downsavefile) {
		this.downsavefile = downsavefile;
	}
	public ArrayList<File> getUpdatefiles() {
		return updatefiles;
	}
	public void setUpdatefiles(ArrayList<File> updatefiles) {
		this.updatefiles = updatefiles;
	}
	public String getUploadtype() {
		return uploadtype;
	}
	public void setUploadtype(String uploadtype) {
		this.uploadtype = uploadtype;
	}
	public int getRequestType() {
		return requestType;
	}
	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}
	public int getContentType() {
		return contentType;
	}
	public void setContentType(int contentType) {
		this.contentType = contentType;
	}
	public boolean isIsdelay() {
		return isdelay;
	}
	public void setIsdelay(boolean isdelay) {
		this.isdelay = isdelay;
	}
	
	
	
	public HttpRequestProgressListener getProgress() {
		return progress;
	}
	public void setProgress(HttpRequestProgressListener progress) {
		this.progress = progress;
	}

	public static final int DOWNLOAD_RESOURCE = 1;
	public static final int UPLOAD_RESOURCE = 2;
	public static final int GET_METHOD = 3;
	public static final int POST_METHOD = 4;
	public static final int POST_FORM_METHOD = 5;
	
	public static final int StringType = 0;
	public static final int JsonType = 1;
	public static final int XmlType = 2;



	public MessageListener getMessageListener() {
		return messageListener;
	}
	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}
	@Override
	public String toString() {
		return "HPackage [pId=" + pId + ", url=" + url + ", postObject="
				+ postObject + ", headers=" + headers + ", params=" + params
				+ ", downsavefile=" + downsavefile + ", updatefiles="
				+ updatefiles + ", uploadtype=" + uploadtype + ", requestType="
				+ requestType + ", contentType=" + contentType + ", isdelay="
				+ isdelay + ", timeoutSecs=" + timeoutSecs + ", bct=" + bct
				+ ", progress=" + progress + ", messageListener="
				+ messageListener + ", classofT=" + classofT + "]";
	}
	
	

}
