package com.scorpio.framework.net.http.interfaces;


import com.scorpio.framework.net.http.HttpRequest.HHttpResponse;

/**
 * http回应结构体
 *
 *
 */
public class HResponse {
	
	/**
	 * 仅供网络层使用
	 */
	final static public int TAG_MESSAGE = 1;
	/**
	 * 网络、业务层共用
	 */
	final static public int TAG_NETWORK_FAIL = 2;
	
	
	private int tag;
	
	private int responseCode;
	
	private int error;
	
	private int resultId;
	
	private Object message;
	
	private Object fromModel;
	
	private HHttpResponse httpResponse;
	
	private HttpResponseException err;

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public HHttpResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HHttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public HttpResponseException getErr() {
		return err;
	}

	public void setErr(HttpResponseException err) {
		this.err = err;
	}

	public Object getFromModel() {
		return fromModel;
	}

	public void setFromModel(Object fromModel) {
		this.fromModel = fromModel;
	}

	@Override
	public String toString() {
		return "HttpResponseModel [err=" + err + ", error=" + error
				+ ", fromModel=" + fromModel + ", httpResponse=" + httpResponse
				+ ", message=" + message + ", responseCode=" + responseCode
				+ ", resultId=" + resultId + ", tag=" + tag + "]";
	}
	
	
	
}
