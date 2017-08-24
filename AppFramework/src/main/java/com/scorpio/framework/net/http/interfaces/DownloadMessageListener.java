package com.scorpio.framework.net.http.interfaces;

import android.os.Handler;

/**
 * 下载处理接口
 *
 */
public interface DownloadMessageListener {
	
	/**
	 * 下载前处理接口
	 * 
	 * @param packetId
	 * 
	 */
	public void handleBeginDownloadApk(int fileLen, Handler handler,
                                       String packetId);
	
	/**
	 * 下载前处理接口
	 * 
	 * @param packetId
	 * @param done 
	 * 
	 */
	public void handleBeginDownloadApkInThread(int fileLen, Handler handler,
                                               String packetId, int done);
	
	
	/**
	 * 下载中处理接口
	 * 
	 * @param fileLen
	 * @param df
	 * 
	 */
	
//	public void handleDownloadingApk(int done, int fileLen, Handler handler,
//			String packetId, DownloadFile df);

	/**
	 * 下载后处理接口
	 * 
	 */
	public void handleDownLoadedApk(int done, Handler handler, String packetId,
                                    String filePath);

	/**
	 * 停止下载处理接口
	 * 
	 * @param handler
	 * @param done 
	 * 
	 */
	public void handleStopDownLoadedApk(Handler handler, String packetId, int done);

	/**
	 * 下载error处理接口
	 * 
	 * @param handler
	 * @param obj 
	 * @param errCode 
	 * 
	 */
	public void handleErrorDownLoadedApk(Handler handler, String packetId, int errCode, Object obj);
	

}
