package com.scorpio.framework.net.http.interfaces;


import com.scorpio.framework.net.http.HttpRequest;

public interface MessageListener {
	public void onMessage(Object message, HttpRequest.HHttpResponse response);
	public void onFailed(HttpResponseException err);
}
