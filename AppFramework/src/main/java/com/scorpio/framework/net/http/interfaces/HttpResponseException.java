package com.scorpio.framework.net.http.interfaces;


import com.scorpio.framework.net.http.HttpRequest.HHttpResponse;

public class HttpResponseException extends Exception {
    private String mResponseMessage;
    private int mResponseCode;
    private HHttpResponse response;

    public HHttpResponse getResponse() {
		return response;
	}

	

	public HttpResponseException(int code, String message) {
        super(String.valueOf(code) + ": " + message);
        mResponseCode = code;
        mResponseMessage = message;
    }
	
	public HttpResponseException(int code, String message, HHttpResponse response) {
        super(String.valueOf(code) + ": " + message);
        mResponseCode = code;
        mResponseMessage = message;
        this.response = response;
    }

    public int getResponseCode() {
        return mResponseCode;
    }

    public String getResponseMessage() {
        return mResponseMessage;
    }
}