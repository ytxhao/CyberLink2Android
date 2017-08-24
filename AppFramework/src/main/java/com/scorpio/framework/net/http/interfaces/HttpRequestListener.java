package com.scorpio.framework.net.http.interfaces;


import com.scorpio.framework.net.http.HttpRequest.HHttpResponse;

public interface HttpRequestListener {
	public void onHttpRequestSucceeded(HHttpResponse response);
	public void onHttpRequestFailed(HHttpResponse response);
	public void onHttpRequestSucceededInBackground(HHttpResponse response) throws Exception;
	public void onHttpRequestFailedInBackground(HHttpResponse response);
}
