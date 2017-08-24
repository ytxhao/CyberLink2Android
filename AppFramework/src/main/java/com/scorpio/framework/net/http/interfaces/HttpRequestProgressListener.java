package com.scorpio.framework.net.http.interfaces;


import com.scorpio.framework.net.http.HttpRequest;

public interface HttpRequestProgressListener {
	public void onHttpDownloadProgressUpdated(HttpRequest request, int percentComplete);
	public void onHttpUploadProgressUpdated(HttpRequest request, int percentComplete, int currentFile, int totalFiles);
}
