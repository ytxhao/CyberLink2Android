package com.scorpio.framework.net.http;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.scorpio.framework.net.http.interfaces.HttpFilePostUploadEntity;
import com.scorpio.framework.net.http.interfaces.HttpPostUploadEntity;
import com.scorpio.framework.net.http.interfaces.HttpRequestListener;
import com.scorpio.framework.net.http.interfaces.HttpRequestProgressListener;
import com.scorpio.framework.net.http.interfaces.HttpResponseException;
import com.scorpio.framework.net.http.interfaces.MessageListener;
import com.scorpio.framework.net.http.HttpRequest.HHttpResponse;
import com.scorpio.framework.utils.JsonTricks;
import com.scorpio.framework.utils.SystemUtil;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.util.ArrayList;




/**
 * httpManager的方法实现类和适配类
 *
 *
 */
public class HttpManagerAdapter {

	public static final int DOWNLOAD_RESOURCE = 1;
	public static final int UPLOAD_RESOURCE = 2;
	public static final int GET_METHOD = 3;
	public static final int POST_METHOD = 4;
	public static final int POST_FORM_METHOD = 5;

	public static final int StringType = 0;
	public static final int JsonType = 1;
	public static final int XmlType = 2;



	private static final String JsonContactType = "application/json";


	private static HttpManagerAdapter _inst;
	
	private static Application context;
	
	

	public static void setContext(Application context) {
		HttpManagerAdapter.context = context;
	}

	public static HttpManagerAdapter getInstance() {
		if (_inst != null) {
			return _inst;
		} else {
			_inst = new HttpManagerAdapter();
			return _inst;
		}
	}

	public void onDestroy(){

	}

	/**
	 * 普通http交互方法封装
	 *
	 * @param url
	 * @param jsonObject
	 * @param mlistener
	 * @param classOfT
	 * @param headers
	 * @param params
	 * @param downsavefile
	 * @param updatefiles
	 * @param uploadtype
	 * @param requestType
	 * @param contentType
	 * @param isdelay
	 * @param progress
	 */
	public  void request(String url, Object jsonObject, final MessageListener mlistener, final Class classOfT, Bundle headers, Bundle params, File downsavefile, ArrayList<File> updatefiles, String uploadtype, final int requestType, int contentType, boolean isdelay, HttpRequestProgressListener progress, String charset){
		request(url,jsonObject,mlistener,classOfT,headers,params,downsavefile, updatefiles, uploadtype,  requestType, contentType, isdelay, progress,HttpConfigureSchema.DEFAULT_TIMEOUT_SECS,charset);
	}
//	public  void requestInSync(String url,Object jsonObject,final MessageListener mlistener,final Class classOfT,Bundle headers,Bundle params,File downsavefile,ArrayList<File> updatefiles,String uploadtype,final int requestType,int contentType,boolean isdelay,HttpRequestProgressListener progress){
//		requestInsync(url,jsonObject,mlistener,classOfT,headers,params,downsavefile, updatefiles, uploadtype,  requestType, contentType, isdelay, progress,HttpConfigureSchema.DEFAULT_TIMEOUT_SECS);
//	}
//	public  void requestMsgFile(String url,Object jsonObject,final MessageListener mlistener,final Class classOfT,Bundle headers,Bundle params,File downsavefile,ArrayList<File> updatefiles,String uploadtype,final int requestType,int contentType,boolean isdelay,HttpRequestProgressListener progress){
//		requestFileInMsg(url,jsonObject,mlistener,classOfT,headers,params,downsavefile, updatefiles, uploadtype,  requestType, contentType, isdelay, progress,HttpConfigureSchema.DEFAULT_TIMEOUT_SECS);
//	}
	/**
	 * 普通http交互方法封装
	 *
	 * @param url
	 * @param jsonObject
	 * @param mlistener
	 * @param classOfT
	 * @param headers
	 * @param params
	 * @param downsavefile
	 * @param updatefiles
	 * @param uploadtype
	 * @param requestType
	 * @param contentType
	 * @param isdelay
	 * @param progress
	 */
	public  void request(String url, Object jsonObject, final MessageListener mlistener, final Class classOfT, Bundle headers, Bundle params, File downsavefile, ArrayList<File> updatefiles, String uploadtype, final int requestType, int contentType, boolean isdelay, HttpRequestProgressListener progress, int timeoutSecs, String charset){
		HttpRequest req;
		int seq = HttpConnectionManager.getInstance().getNextSeq();
		switch(requestType){
		case DOWNLOAD_RESOURCE:
			req = HttpRequest.RequestFactory.createGetRequest(context, url, true, seq);
			req.setDownloadFile(downsavefile);
			if(progress!=null){
				req.setProgressListener(progress);
			}
			break;
		case UPLOAD_RESOURCE:
			req = HttpRequest.RequestFactory.createMultipartPostRequest(context, url, false, seq);
			req.setPostFiles(getArrayListEzHttpPostUploadEntity(updatefiles,uploadtype));
			if(progress!=null){
				req.setProgressListener(progress);
			}
			break;
		case POST_METHOD:
			req = HttpRequest.RequestFactory.createPostStringEntityRequest(context, url, false, seq , JsonTricks.getString(jsonObject), JsonContactType, HTTP.UTF_8);
			if(charset!=null){
				req.setCharset_name(charset);
			}
			break;
		case GET_METHOD:
			req = HttpRequest.RequestFactory.createGetRequest(context, url, false, seq);
			if(charset!=null){
				req.setCharset_name(charset);
			}
			break;
		case POST_FORM_METHOD:
			req = HttpRequest.RequestFactory.createPostRequest(context, url, false, seq );
			if(charset!=null){
				req.setCharset_name(charset);
			}
			break;
		default://POST_FORM_METHOD
			req = HttpRequest.RequestFactory.createPostRequest(context, url, false, seq );
			if(charset!=null){
				req.setCharset_name(charset);
			}
			break;
		}
//		req.addHeader("User-Agent", "Android "+context.localClientInfo.OSVersion);
//		req.addHeader("LANG", "zh_CN");
		if(headers!=null){
			for(String name:headers.keySet()){
				req.addHeader(name, headers.getString(name));
			}
		}
//		Bundle t = getHeaderWithToken();
//		if(t!=null){
//			for(String name:t.keySet()){
//				req.addHeader(name, t.getString(name));
//			}
//		}
		if(params!=null){
			for(String name:params.keySet()){
				req.addParam(name, params.getString(name));
			}
		}
		if(contentType == JsonType){
			if(requestType!=UPLOAD_RESOURCE&&requestType!=POST_FORM_METHOD){
//				req.addHeader("Content-type", "application/json");
				req.addHeader("Content-type", "text/plain");
			}else if(requestType==POST_FORM_METHOD){
				req.addHeader("content-type", "application/x-www-form-urlencoded");
			}
//			req.addHeader("Accept", "*/*");
			if(mlistener!=null){
				req.setFinishedListener(new HttpRequestListener(){
					@Override
					public void onHttpRequestFailed(HHttpResponse response) {
						// TODO Auto-generated method stub
//						Log.e("net", "the code is:"+response.getResponseCode()+";the rcode is:"+response.getRequestCode()+";the token:"+ context.token);
						mlistener.onMessage(null, response);
					}

					@Override
					public void onHttpRequestFailedInBackground(HHttpResponse response) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onHttpRequestSucceeded(HHttpResponse response) {
						// TODO Auto-generated method stub
						HttpConnectionManager.getInstance().unRegisterConnection(response.getRequestCode());


						
						if(response.getResponseText()!=null&&requestType != DOWNLOAD_RESOURCE&&classOfT!=null){
							Object obj = null;
							try {
								obj = JsonTricks.getObject(response.getResponseText().toString(),classOfT);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							mlistener.onMessage(obj,response);
						}else{
							if(requestType == DOWNLOAD_RESOURCE){
								if(response.getResponseFile()!=null){
									mlistener.onMessage(response.getResponseFile(),response);
									return;
								}
							}
							mlistener.onMessage(null,response);
						}

					}

					@Override
					public void onHttpRequestSucceededInBackground(
							HHttpResponse response) throws Exception {
						// TODO Auto-generated method stub

					}});
			}
		}
		req.setTimeoutSecs(timeoutSecs);
		HttpConnectionManager.getInstance().registerConnection(seq,req);
		if(isdelay){
			addResDelay(seq);
		}else{
			if(SystemUtil.networkAvailable(context)){
				req.executeAsync();
			}else{
				Log.e("net", "time out:"+url);
				mlistener.onFailed(new HttpResponseException(-1, "timeout",null));
			}

		}
	}
	
	private void addResDelay(int seq) {
		// TODO Auto-generated method stub
		//FileManager.getInstance().addResDelay(seq);
	}

	/**
	 * 获取上传文件结构体
	 *
	 * @param files
	 * @param cotentType
	 * @return
	 */
	static public ArrayList<HttpPostUploadEntity> getArrayListEzHttpPostUploadEntity(
            ArrayList<File> files, String cotentType) {
		// TODO Auto-generated method stub
		ArrayList<HttpPostUploadEntity> fileEntitys = new ArrayList<HttpPostUploadEntity>();
		String tp = "upload";
		String cType = "application/octet-stream";
		if(cotentType != null){
			cType = cotentType;
		}
//		int i = 1;
		for(File file:files){
			fileEntitys.add(new HttpFilePostUploadEntity(file, tp, file.getName(),cType));
//			i++;
		}
		return fileEntitys;
	};

	/**
	 * 取出网络交互应答头中的时间戳
	 *
	 * @param ezHttpResponse
	 * @return
	 */
	public static String getDate(HHttpResponse ezHttpResponse) {
		// TODO Auto-generated method stub
		if(ezHttpResponse==null||ezHttpResponse.getAllheaders()==null){
			Log.e("core","the Headers of HttpResponse is null in RelaionManager 's getDate() .");
			return null;
		}
		for(Header h:ezHttpResponse.getAllheaders()){
			if(h.getName().equals("Date")){
				return h.getValue();
			}
		}
		return null;
	}



	
}
