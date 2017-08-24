package com.scorpio.framework.net.http;

import android.os.Bundle;

import com.scorpio.framework.net.http.interfaces.HandleMessageListener;
import com.scorpio.framework.net.http.interfaces.MessageListener;

import java.io.File;
import java.util.ArrayList;


/**
 * http 协议包生成工厂类...
 *
 */
public class HPackageFactory extends HPackage {
	
	private static int mode = HttpManagerAdapter.JsonType;
	
	private HPackageFactory(){
		
	}
	
	private HPackageFactory(String url, Object jsonObject, Bundle headers, Bundle params, File downsavefile, ArrayList<File> updatefiles, String uploadtype, final int requestType, int contentType){
		this.setUrl(url);
		this.setPostObject(jsonObject);
		this.setHeaders(headers); 
		this.setParams(params);
		this.setDownsavefile(downsavefile); 
		this.setUpdatefiles(updatefiles);
		this.setUploadtype(uploadtype);
		this.setRequestType(requestType); 
		this.setContentType(contentType);
	}
	
	
	static public HPackageFactory downloadResource(String url, File file){
		
		return new HPackageFactory(url, null,  null, null,file,null,null, HttpManagerAdapter.DOWNLOAD_RESOURCE, mode);
	}
	
	static public <T> HPackageFactory uploadResource(String url, ArrayList<File> files, String mimetype){
		
		return new HPackageFactory(url, null, null, null,null,files,mimetype, HttpManagerAdapter.UPLOAD_RESOURCE, mode);
	}
	
	static public <T> HPackageFactory uploadResource(String url, ArrayList<File> files){
		
		return new HPackageFactory(url, null,  null, null,null,files,null, HttpManagerAdapter.UPLOAD_RESOURCE, mode);
	}	
	
	static public <T> HPackageFactory get(String url, Bundle getParams, Bundle headers){
		
		return new HPackageFactory(url, null,  headers, getParams,null,null,null, HttpManagerAdapter.GET_METHOD, mode);
	}
	/**
	 * post form
	 * 
	 * @param url
	 * @param postParams
	 * @param headers
	 * @return
	 */
	static public <T> HPackageFactory post(String url, Bundle postParams, Bundle headers, boolean isForm){
		
		return new HPackageFactory(url, null, headers, postParams,null,null,null, HttpManagerAdapter.POST_FORM_METHOD, mode);
	}
	
	/**
	 * post obj
	 * 
	 * @param url
	 * @param pojoObject
	 * @param headers
	 * @return
	 */
	static public <T> HPackageFactory post(String url, Object pojoObject, Bundle headers){
       
		return new HPackageFactory(url, pojoObject, headers, null,null,null,null, HttpManagerAdapter.POST_METHOD, mode);
	}
	
	static public Object exec(HPackage fpackage) {
		@SuppressWarnings("rawtypes")
        Class c = fpackage.getClassofT();
		MessageListener ml;
		if(fpackage.getMessageListener()!=null){
			ml = fpackage.getMessageListener();
		}else{
			ml = new HandleMessageListener(fpackage.getpId(),fpackage.getPostObject());
		}
//		DownloadManager.getInstance().checkerInfosWithHeader(fpackage);
		HttpManagerAdapter.getInstance().request
		(
				fpackage.getUrl(), 
				fpackage.getPostObject(), 
				ml,
				c, 
				fpackage.getHeaders(), 
				fpackage.getParams(), 
				fpackage.getDownsavefile(), 
				fpackage.getUpdatefiles(), 
				fpackage.getUploadtype(), 
				fpackage.getRequestType(), 
				fpackage.getContentType(),
				false,
				fpackage.getProgress(),
				fpackage.getTimeoutSecs(),
				fpackage.getCharset()
		);
		
		return fpackage;
	}

}
