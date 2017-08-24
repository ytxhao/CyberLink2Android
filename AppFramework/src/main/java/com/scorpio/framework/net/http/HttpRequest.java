package com.scorpio.framework.net.http;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.scorpio.framework.net.http.interfaces.HttpPostUploadEntity;
import com.scorpio.framework.net.http.interfaces.HttpRequestListener;
import com.scorpio.framework.net.http.interfaces.HttpRequestProgressListener;
import com.scorpio.framework.utils.Base64;
import com.scorpio.framework.utils.ExceptionTricks;
import com.scorpio.framework.utils.IOTricks;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.CookieSpecBase;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;




/**
 * 简单http封装类
 * 
 * 封装apache的httpclient接口
 *
 * 
 */
public class HttpRequest implements IOTricks.ProgressListener {


	public static final int REQ_GET = 1;
	public static final int REQ_PUT = 2;
	public static final int REQ_DEL = 3;
	public static final int REQ_HEAD = 4;
	public static final int REQ_POST = 5;
	public static final int REQ_POST_MULTIPART = 6;
	public static final int REQ_POST_STRING_ENT = 7;

	private static final String VAL_LAST_MOD_HEADER = "Last-Modified";
	private static final String VAL_USER_AGENT_HEADER = "User-Agent";

	private static final String VAL_AUTHORIZATION_HEADER = "Authorization";
	private static final String VAL_BASIC = "Basic ";

	public static class RequestFactory {
		public static HttpRequest createGetRequest(Context c, String url,
                                                   boolean isRaw, int requestCode) {
			HttpRequest req = new HttpRequest(c, url, REQ_GET, isRaw,
					requestCode);
			return req;
		}

		public static HttpRequest createPutRequest(Context c, String url,
                                                   boolean isRaw, int requestCode) {
			HttpRequest req = new HttpRequest(c, url, REQ_PUT, isRaw,
					requestCode);
			return req;
		}

		public static HttpRequest createDeleteRequest(Context c, String url,
                                                      boolean isRaw, int requestCode) {
			HttpRequest req = new HttpRequest(c, url, REQ_DEL, isRaw,
					requestCode);
			return req;
		}

		public static HttpRequest createHeadRequest(Context c, String url,
                                                    boolean isRaw, int requestCode) {
			HttpRequest req = new HttpRequest(c, url, REQ_HEAD, isRaw,
					requestCode);
			return req;
		}

		public static HttpRequest createPostRequest(Context c, String url,
                                                    boolean isRaw, int requestCode) {
			HttpRequest req = new HttpRequest(c, url, REQ_POST, isRaw,
					requestCode);
			return req;
		}

		public static HttpRequest createMultipartPostRequest(Context c,
                                                             String url, boolean isRaw, int requestCode) {
			HttpRequest req = new HttpRequest(c, url, REQ_POST_MULTIPART,
					isRaw, requestCode);
			req.addHeader(HTTP.CONTENT_TYPE, VAL_POST_MULTIPART_CONTENT_TYPE);
			req.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
			return req;
		}

		public static HttpRequest createPostStringEntityRequest(Context c,
                                                                String url, boolean isRaw, int requestCode, String entity,
                                                                String entityType, String entityEncoding) {
			HttpRequest req = new HttpRequest(c, url, REQ_POST_STRING_ENT,
					isRaw, requestCode);
			req.setStringEntity(entity, entityType, entityEncoding);
			return req;
		}
	}

	private static final String TMP_FILE_PREFIX = "ez_http_response";

	private Context mContext;
	private String mUrl;
	private int mReqType;
	private int mTimeoutSecs;
	private boolean mIsRaw;

	private String mStringEntity;
	private String mStringEntityType;
	private String mStringEntityEncoding;

	private int mRequestCode;
	private Object mTag;

	private ArrayList<NameValuePair> mParams;
	private ArrayList<HttpPostUploadEntity> mPostFiles;
	private File downloadfile;
	private HashMap<String, String> mHeaders;

	private HttpRequestListener mFinishedListener;
	private HttpRequestProgressListener mProgressListener;

	private Handler mResponseHandler;

	private long mTotalBytes;
	private int mCurrentFile;
	private boolean mUploadingFiles;

	protected HttpRequest(Context c, String url, int reqType, boolean isRaw,
                          int requestCode) {
		mUrl = url;
		mReqType = reqType;
		mIsRaw = isRaw;
		mTimeoutSecs = HttpConfigureSchema.DEFAULT_TIMEOUT_SECS;
		mContext = c;

		mStringEntity = null;
		mStringEntityType = null;
		mStringEntityEncoding = null;

		mRequestCode = requestCode;
		mTag = null;

		mHeaders = null;
		mParams = null;
		mPostFiles = null;

		mFinishedListener = null;
		mProgressListener = null;

		// mResponseProcessor = null;
		mResponseHandler = null;

		mTotalBytes = 1;
		mCurrentFile = 0;
		mUploadingFiles = false;
	}

	public void setContext(Context c) {
		mContext = c;
	}

	public void setUrl(String url) {
		mUrl = url;
	}

	public void setRequestType(int requestType) {
		mReqType = requestType;
	}

	public void setTimeoutSecs(int timeoutSecs) {
		mTimeoutSecs = timeoutSecs;
	}

	public void setIsRaw(boolean isRaw) {
		mIsRaw = isRaw;
	}

	public Context getContext() {
		return mContext;
	}

	public String getUrl() {
		return mUrl;
	}

	public int getRequestType() {
		return mReqType;
	}

	public int getTimeoutSecs() {
		return mTimeoutSecs;
	}

	public boolean isRaw() {
		return mIsRaw;
	}

	public void setStringEntity(String entity, String type, String encoding) {
		mStringEntity = entity;
		mStringEntityType = type;
		mStringEntityEncoding = encoding;
	}

	public String getStringEntity() {
		return mStringEntity;
	}

	public String getStringEntityType() {
		return mStringEntityType;
	}

	public String getStringEntityEncoding() {
		return mStringEntityEncoding;
	}

	// public void setResponseProcessor(HttpResponseProcessor processor) {
	// mResponseProcessor = processor;
	// }
	public void setHandler(Handler handler) {
		mResponseHandler = handler;
	}

	public void setHeaders(HashMap<String, String> headers) {
		mHeaders = headers;
	}

	public void setParams(ArrayList<NameValuePair> postParams) {
		mParams = postParams;
	}

	public void setPostFiles(ArrayList<HttpPostUploadEntity> postFiles) {
		mPostFiles = postFiles;
	}

	public HashMap<String, String> getHeaders() {
		return mHeaders;
	}

	public ArrayList<NameValuePair> getParams() {
		return mParams;
	}

	public ArrayList<HttpPostUploadEntity> getPostFiles() {
		return mPostFiles;
	}

	public void addHeader(String name, String value) {
		if (mHeaders == null) {
			mHeaders = new HashMap<String, String>();
		}
		mHeaders.put(name, value);
	}

	public void addParam(String name, String value) {
		if (mParams == null) {
			mParams = new ArrayList<NameValuePair>();
		}
		mParams.add(new BasicNameValuePair(name, value));
	}

	public void addPostFile(HttpPostUploadEntity postFile) {
		if (mPostFiles == null) {
			mPostFiles = new ArrayList<HttpPostUploadEntity>();
		}
		mPostFiles.add(postFile);
	}

	public void addBasicAuth(String username, String password) {
		String auth = Base64.encodeString(username + ":" + password);
		addHeader(VAL_AUTHORIZATION_HEADER, VAL_BASIC + auth);
	}

	public void addUserAgent(String userAgent) {
		addHeader(VAL_USER_AGENT_HEADER, userAgent);
	}

	public void setDownloadFile(File file) {
		downloadfile = file;
	}

	public void setRequestCode(int requestCode) {
		mRequestCode = requestCode;
	}

	public void setTag(Object tag) {
		mTag = tag;
	}

	public int getRequestCode() {
		return mRequestCode;
	}

	public Object getTag() {
		return mTag;
	}

	public HttpRequestListener getRequestFinishedListener() {
		return mFinishedListener;
	}

	public void setFinishedListener(HttpRequestListener listener) {
		mFinishedListener = listener;
	}

	public void setResponseHandler(Handler responseHandler) {
		mResponseHandler = responseHandler;
	}

	public void setProgressListener(HttpRequestProgressListener listener) {
		mProgressListener = listener;
	}

	protected void processMessage(HttpUriRequest message) throws Exception {
		// overridable method added for OAuth support
	}

	public HHttpResponse generateExceptionResponse(Throwable e) {
		HHttpResponse response = new HHttpResponse(this);
		response.mResponseCode = -1;
		response.mSuccess = false;
		response.mResponseReasonPhrase = e.getMessage();
		response.mResponseText = ExceptionTricks.getThrowableTraceAsString(e);
		return response;
	}

	public void executeAsync(HttpRequestListener listener,
			Handler responseHandler) {
		setFinishedListener(listener);
		setResponseHandler(responseHandler);
		executeAsync();
	}

	public void executeAsync(HttpRequestListener listener) {
		setFinishedListener(listener);
		executeAsync();
	}

	public void executeAsync(Handler responseHandler) {
		setResponseHandler(responseHandler);
		executeAsync();
	}

	// public void executeAsync() {
	// new AsyncTask<Void, Void, Void>() {
	//
	// @Override
	// protected Void doInBackground(Void... params) {
	// executeInSync();
	// return null;
	// }
	// }.execute((Void)null);
	// }
	public void executeAsync() {
//		Thread t = new Thread(new Runnable(){
//			@Override
//			public void run() {
//				executeInSync();
//			}});
//		t.start();
		HttpConnectionManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				executeInSync();
			}
		});
	}

//	public void executeAsyncMsgFileDown() {
//		HttpConnectionManager.getInstance().executeMsgFileDown(new Runnable() {
//			@Override
//			public void run() {
//				executeInSync();
//			}
//		});
//	}

	public HHttpResponse executeInSync(HttpRequestListener listener,
			Handler responseHandler) {
		setFinishedListener(listener);
		setResponseHandler(responseHandler);
		return executeInSync();
	}

	public HHttpResponse executeInSync(HttpRequestListener listener) {
		setFinishedListener(listener);
		return executeInSync();
	}

	public HHttpResponse executeInSync(Handler responseHandler) {
		setResponseHandler(responseHandler);
		return executeInSync();
	}

	public HHttpResponse executeInSync() {
		HHttpResponse response = null;
		long time = System.currentTimeMillis();
		try {
			response = execute();
		} catch (Throwable t) {
			t.printStackTrace();
			response = generateExceptionResponse(t);
		}
		response.mRequestTime = System.currentTimeMillis() - time;
		response.onExecuteComplete();
		return response;
	}

	private HHttpResponse execute() {
		if (mReqType == REQ_POST_MULTIPART)
			return executeMultipartPostRequest();

		HHttpResponse hResponse = new HHttpResponse(this);

		HttpParams connParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(connParams,
				mTimeoutSecs * 1000);
		HttpConnectionParams.setSoTimeout(connParams, mTimeoutSecs * 1000);
		HttpUriRequest message = null;

		String url = mUrl;

		if (mReqType != REQ_POST && mReqType != REQ_PUT && mParams != null
				&& mParams.size() > 0) {
			for (NameValuePair param : mParams) {
				if (url.contains("?")) {
					url += "&";
				} else {
					url += "?";
				}
				url += param.getName() + "=" + param.getValue();
			}
		}

		// Log.d("HttpReq", "performing http request - " + url);
		DefaultHttpClient client = null;
		try {
			switch (mReqType) {
			case REQ_GET: {
				message = new HttpGet(url);
				break;
			}
			case REQ_PUT: {
				message = new HttpPut(url);
				if (mParams != null)
					((HttpPut) message).setEntity(new UrlEncodedFormEntity(
							mParams, HTTP.UTF_8));
				break;
			}
			case REQ_DEL: {
				message = new HttpDelete(url);
				break;
			}
			case REQ_HEAD: {
				message = new HttpHead(url);
				break;
			}
			case REQ_POST: {
				message = new HttpPost(url);
				if (mParams != null)
					((HttpPost) message).setEntity(new UrlEncodedFormEntity(
							mParams, HTTP.UTF_8));
				break;
			}
			case REQ_POST_STRING_ENT: {
				message = new HttpPost(url);
				StringEntity ent = new StringEntity(mStringEntity,
						mStringEntityEncoding);
				ent.setContentType(mStringEntityType);
				((HttpPost) message).setEntity(ent);
				break;
			}
			}

			if (HttpConfigureSchema.isEncodingGzip) {
				addHeader("Accept-Encoding", "gzip");
			}

			if (mHeaders != null) {
				for (String headerName : mHeaders.keySet()) {
					if (HttpConfigureSchema.isDebug) {
						Log.e("heads",
								headerName + " = " + mHeaders.get(headerName));
					}
					message.addHeader(headerName, mHeaders.get(headerName));
				}
			}

			processMessage(message);

			client = new DefaultHttpClient(connParams);
//					cHttpClient.getInstance().getThreadHttpClient(
//					Thread.currentThread(), connParams);

			client.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy() {
				@Override
				public long getKeepAliveDuration(HttpResponse response,
						HttpContext context) {
					long keepAlive = super.getKeepAliveDuration(response,
							context);
					if (keepAlive < 0) {
						// 如果keep-alive值没有由服务器明确设置，那么保持连接持续{HttpConfigureSchema.HTTP_KEEP_ALIVE}(默认为5)秒。
						keepAlive = HttpConfigureSchema.HTTP_KEEP_ALIVE * 1000;
					}
					Log.d("net", "the keepAlive is :" + keepAlive);
					return keepAlive;
				}
			});
			if (HttpConfigureSchema.isShowContent) {
				Log.e("net", url);
				if (mReqType == REQ_POST_STRING_ENT) {
					Log.e("net", this.getStringEntity());
				}
			}
			HttpResponse response = client.execute(message);
			if (HttpConfigureSchema.isDebug) {
				Log.e("net", "response code is:"
						+ response.getStatusLine().getStatusCode());
				for (Header h : response.getAllHeaders()) {
					Log.e("response-heads", h.getName() + " = " + h.getValue());
				}
			}
			hResponse
					.setResponseCode(response.getStatusLine().getStatusCode());
			hResponse.allheaders = response.getAllHeaders();
			getCookieByHeader(response, hResponse);
			// if(HttpConfigureSchema.isDebug){
			// Log.e("net", response.toString());
			// }
			HttpEntity entity = response.getEntity();
			// client.getCookieStore().getCookies();
			getCookie(client, hResponse.cookies);
			if (entity != null) {
				hResponse.mResponseReasonPhrase = response.getStatusLine()
						.getReasonPhrase();
				hResponse.mResponseContentLength = entity.getContentLength();
				if (entity.getContentEncoding() != null)
					hResponse.mResponseContentEncoding = entity
							.getContentEncoding().getValue();
				if (entity.getContentType() != null)
					hResponse.mResponseContentType = entity.getContentType()
							.getValue();

				Header lastMod = response.getFirstHeader(VAL_LAST_MOD_HEADER);
				if (lastMod != null) {
					try {
						hResponse.mResponseLastModTime = DateUtils.parseDate(
								lastMod.getValue()).getTime();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				// Log.e("test", ezResponse.toString());

				InputStream is = entity.getContent();

				if (entity.getContentEncoding() != null
						&& entity.getContentEncoding().getValue()
								.equalsIgnoreCase("gzip")) {
					is = new GZIPInputStream(is);
				} else {
					Log.d("net",
							"the encode is not used."
									+ entity.getContentEncoding());
				}
				handleResponseInputStream(hResponse, is);

				if (!isRaw())
					if (HttpConfigureSchema.isShowContent) {
						Log.d("net",
								"server response = "
										+ hResponse.getResponseText());
					}
			} else {
				// handleResponseInputStream(ezResponse, entity.getContent());
				Log.e("net", "server response body is null");
			}

		} catch (Exception e) {
			// Log.e(TAG, "Error performing requst to url: " + getUrl());
			e.printStackTrace();
			hResponse.mSuccess = false;
			hResponse.mResponseCode = -1;
			hResponse.mResponseReasonPhrase = e.getMessage();
			hResponse.mResponseText = ExceptionTricks
					.getThrowableTraceAsString(e);
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			 if (client != null && client.getConnectionManager() != null) {
			 client.getConnectionManager().shutdown();
			 }
		}

		return hResponse;
	}

	private void getCookieByHeader(HttpResponse response,
			HHttpResponse ezResponse) {
		if (response.getHeaders("Set-Cookie") != null) {
			for (Header h : response.getHeaders("Set-Cookie")) {
				String[] cookies = h.getValue().split(";");
				if (cookies == null) {
					return;
				}
				for (String cookie : cookies) {
					if (cookie.startsWith("uss=")) {
						ezResponse.setToken(cookie.substring(4));
					}
				}
			}
		}
		Header[] hs = response.getHeaders("Date");
		if (hs != null) {
			for (Header h : hs) {
				ezResponse.setDate(h.getValue());
			}
		}
		Header[] lmhs = response.getHeaders("Last-Modified");
		if (hs != null) {
			for (Header h : lmhs) {
				ezResponse.setMotifyDate(h.getValue());
			}
		}
	}
	
	public static final String DEFAULT_CHARSET_NAME = "UTF-8";
	
	private String charset_name = DEFAULT_CHARSET_NAME;
	
	public String getCharset_name() {
		return charset_name;
	}

	public void setCharset_name(String charset_name) {
		this.charset_name = charset_name;
	}

	private void handleResponseInputStream(HHttpResponse ezResponse,
			InputStream httpInputStream) throws IOException {
		if ((!ezResponse.wasSuccess()) || (!mIsRaw)) {
			ezResponse.mResponseText = IOTricks.getTextFromStream(
					httpInputStream,charset_name, true, null);
		} else {
			mUploadingFiles = false;
			mTotalBytes = ezResponse.getResponseContentLength();
			// Log.e(TAG, "Context is " + (mContext == null ? "null" :
			// "not null"));
			File dataFile;
			if (downloadfile != null) {
				dataFile = downloadfile;
			} else {
				dataFile = File.createTempFile(TMP_FILE_PREFIX, null,
						mContext.getCacheDir());
			}
			IOTricks.copyInputStreamToFile(httpInputStream, dataFile,
					IOTricks.DEFAULT_BUFFER_SIZE, true, true, this);
			ezResponse.mResponseFile = dataFile;
		}
	}

	// post file string constants
	private static final String VAL_HTTP_POST = "POST";
	private static final String VAL_LINE_END = "\r\n";
	private static final String VAL_TWO_HYPHENS = "--";
//	private static final String VAL_BOUNDRY = "***MIMEFileUpload***";
	private static final String WK_BOUNDRY = "----WebKitFormBoundaryAufhJk4Uv581wgtz";
	private static final String VAL_POST_MULTIPART_CONTENT_TYPE = "multipart/form-data;boundary="
			+ WK_BOUNDRY;
	private static final String VAL_FILE_CONTENT_DISPOSITION_FORMATER = "Content-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\n";
	private static final String VAL_FILE_CONTENT_TYPE_FORMATTER = "Content-Type: %s\r\n";
	private static final String VAL_FILE_CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding: binary\r\n\r\n";
	private static final String VAL_PARAM_CONTENT_DISPOSITION_FORMATER = "Content-Disposition: form-data; name=\"%s\"\r\n\r\n";
	private static final String VAL_POST_SEPERATOR = VAL_TWO_HYPHENS
			+ WK_BOUNDRY + VAL_LINE_END;
	private static final String VAL_POST_CLOSE = VAL_TWO_HYPHENS + WK_BOUNDRY
			+ VAL_TWO_HYPHENS + VAL_LINE_END;
//	private static final String TAG = HttpRequest.class.getSimpleName();

	private HHttpResponse executeMultipartPostRequest() {
		HHttpResponse ezResponse = new HHttpResponse(this);
		mUploadingFiles = true;
		HttpURLConnection conn = null;
		try {
			disableConnectionReuseIfNecessary();
			// setup connection
			if (HttpConfigureSchema.isShowContent) {
				Log.e("net", mUrl);
				if (mReqType == REQ_POST_STRING_ENT) {
					Log.e("net", this.getStringEntity());
				}
			}
			DataOutputStream outputStream = null;

			for (int ij = 0; ij < 3; ij++) {
				try {
					conn = (HttpURLConnection) (new URL(mUrl).openConnection());
					conn.setDoInput(true);
					conn.setDoOutput(true);
					conn.setUseCaches(false);
					conn.setConnectTimeout(mTimeoutSecs * 1000);
					conn.setReadTimeout(8 * 60 * 1000);
					conn.setRequestMethod(VAL_HTTP_POST);
					
					
					if (mHeaders != null) {
//						mHeaders.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//						mHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36");
//						mHeaders.put("Referer", " http://localhost:8080/game_app/upload/upload.jsp");
						for (String headerName : mHeaders.keySet()) {
							conn.setRequestProperty(headerName,
									mHeaders.get(headerName));
							if (HttpConfigureSchema.isShowContent) {
								Log.e("net", "headerName:"+headerName+":"+mHeaders.get(headerName));
							}
						}
					}
					
					boolean hasFiles = mPostFiles != null && mPostFiles.size() > 0;
					
					// START OUTPUT
					outputStream = new DataOutputStream(conn.getOutputStream());
					outputStream.writeBytes(VAL_POST_SEPERATOR);
					
//					mParams.add();

					if (mParams != null) {
						for (int i = 0; i < mParams.size(); i++) {
							NameValuePair param = mParams.get(i);
							outputStream.writeBytes(String.format(
									VAL_PARAM_CONTENT_DISPOSITION_FORMATER,
									param.getName()));
							outputStream.writeBytes(param.getValue());
							outputStream.writeBytes(VAL_LINE_END);

							if (hasFiles || i < mParams.size() - 1) {
								outputStream.writeBytes(VAL_POST_SEPERATOR);
							}
						}
					}

					if (hasFiles) {
						for (int i = 0; i < mPostFiles.size(); i++) {
							HttpPostUploadEntity uploadFile = mPostFiles.get(i);
							mCurrentFile = i;
							mTotalBytes = uploadFile.getSize();

							outputStream.writeBytes(String.format(
									VAL_FILE_CONTENT_DISPOSITION_FORMATER,
									uploadFile.getParamName(),
									uploadFile.getPostFileName()));

							outputStream.writeBytes(String.format(
									VAL_FILE_CONTENT_TYPE_FORMATTER,
									uploadFile.getContentType()));
							if (HttpConfigureSchema.isDebug) {
								Log.e("net", String.format(
										VAL_FILE_CONTENT_DISPOSITION_FORMATER,
										uploadFile.getParamName(),
										uploadFile.getPostFileName()));
								Log.e("net", String.format(
										VAL_FILE_CONTENT_TYPE_FORMATTER,
										uploadFile.getContentType()));

							}
							;
							outputStream
									.writeBytes(VAL_FILE_CONTENT_TRANSFER_ENCODING);
							IOTricks.copyInputStreamToOutputStream(
									uploadFile.getInputStream(), outputStream,
									IOTricks.DEFAULT_BUFFER_SIZE, true, false,
									this);
							outputStream.writeBytes(VAL_LINE_END);

							if (i < mPostFiles.size() - 1) {
								outputStream.writeBytes(VAL_POST_SEPERATOR);
							}
						}
					}

				} catch (IOException e) {
					
					
					Log.e("===========", "ij: " + ij + " " + e.getMessage(), e);
					Thread.sleep(500);
					continue;

				}

				break;
			}
			if (outputStream != null) {
				outputStream.writeBytes(VAL_POST_CLOSE);

				outputStream.flush();
				outputStream.close();
			}

			

			// HANDLE RESPONSE
			ezResponse.setResponseCode(conn.getResponseCode());
			if (HttpConfigureSchema.isDebug) {
				Log.e("net",
						"the response code is:" + ezResponse.getResponseCode());
				Map<String, List<String>> hs = conn.getHeaderFields();
				for (String k : hs.keySet()) {
					Log.e("response-heads", k + " = " + hs.get(k));
				}
				;
			}

			ezResponse.mResponseReasonPhrase = conn.getResponseMessage();
			ezResponse.mResponseContentLength = conn.getContentLength();
			if (HttpConfigureSchema.isDebug) {
				Log.e("net",
						"the response msg is:"
								+ ezResponse.getResponseContentLength());
			}
			//
			ezResponse.mResponseContentEncoding = conn.getContentEncoding();
			ezResponse.mResponseContentType = conn.getContentType();
			ezResponse.mResponseLastModTime = conn.getLastModified();

			handleResponseInputStream(ezResponse, conn.getInputStream());

		} catch (Exception e) {
			e.printStackTrace();
			ezResponse.mSuccess = false;
			ezResponse.mResponseCode = -1;
			ezResponse.mResponseReasonPhrase = e.getMessage();
			ezResponse.mResponseText = ExceptionTricks
					.getThrowableTraceAsString(e);
		} finally {
			// if(conn!=null){
			// conn.disconnect();
			// }
		}

		return ezResponse;
	}
	
	

	static long size = 0;
	
	private void disableConnectionReuseIfNecessary() {
		// HTTP connection reuse which was buggy pre-froyo
		if (Build.VERSION.SDK_INT< Build.VERSION_CODES.FROYO) {
			System.setProperty("http.keepAlive", "false");
		} else {
			System.setProperty("http.keepAlive", "true");
		}
	}

	@Override
	public void onProgressUpdate(int totalBytesRead) {
		if (mProgressListener != null) {
			if (mUploadingFiles)
				mProgressListener.onHttpUploadProgressUpdated(this,
						(int) (((float) totalBytesRead / Math.max(
								(float) mTotalBytes, 1f)) * 100f),
						mCurrentFile, mPostFiles.size());
			else
				mProgressListener.onHttpDownloadProgressUpdated(this,
						(int) (((float) totalBytesRead / Math.max(
								(float) mTotalBytes, 1f)) * 100f));
		}
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("HttpRequest: " + mUrl + "\n");
		b.append("RequestCode: " + mRequestCode + "\n");
		b.append("Request Type: ");
		switch (mReqType) {
		case REQ_GET: {
			b.append("GET");
			break;
		}
		case REQ_PUT: {
			b.append("PUT");
			break;
		}
		case REQ_DEL: {
			b.append("DELETE");
			break;
		}
		case REQ_HEAD: {
			b.append("HEAD");
			break;
		}
		case REQ_POST: {
			b.append("POST");
			break;
		}
		case REQ_POST_MULTIPART: {
			b.append("POST-MULTIPART");
			break;
		}
		case REQ_POST_STRING_ENT: {
			b.append("POST-STRING-ENTITY");
			break;
		}
		}
		b.append("\nIs Raw: " + (mIsRaw ? "Yes" : "No") + "\n");

		if (mParams != null) {
			b.append("\nParameters: \n");
			for (NameValuePair param : mParams) {
				b.append("\t" + param.getName() + " = " + param.getValue()
						+ "\n");
			}
			b.append("\n");
		}
		return b.toString();
	}

	private void getCookie(DefaultHttpClient httpclient, Bundle bundle) {
		List<Cookie> cookies = httpclient.getCookieStore().getCookies();
		if (cookies.isEmpty()) {
			Log.i("net", "-------Cookie NONE---------");
		} else {
			for (Cookie cookie : cookies) {
				bundle.putString(cookie.getName(), cookie.getValue());
			}
		}
	}

	@SuppressWarnings("unused")
	private void addCookie(DefaultHttpClient httpclient) {
		// Create a local instance of cookie store
		CookieStore cookieStore = httpclient.getCookieStore();
		// Populate cookies if needed
		BasicClientCookie cookie = new BasicClientCookie("name", "value");
		// cookie.setVersion(0);
		// cookie.setDomain(".mycompany.com");
		// cookie.setPath("/");
		cookieStore.addCookie(cookie);
		// Set the store
		httpclient.setCookieStore(cookieStore);

		// -------------------------
		// Cookie ckie=new BasicClientCookie("","");
		CookieSpecBase cookieSpecBase = new BrowserCompatSpec();
		List<Cookie> cookies = new ArrayList<Cookie>();
		cookies.add(cookie);
		cookieSpecBase.formatCookies(cookies);
		// post.setHeader(cookieSpecBase.formatCookies(cookies).get(0));
	}

	public static class HHttpResponse {
		private HttpRequest mRequest;
		private boolean mSuccess;
		private int mResponseCode;
		private String mResponseReasonPhrase;
		private String mResponseContentType;
		private String mResponseContentEncoding;
		private long mResponseLastModTime;
		private long mResponseContentLength;
		private String mResponseText;
		private File mResponseFile;
		private long mRequestTime;
		private Bundle cookies;
		private String token;
		private String date;
		private String motifyDate;
		private Header[] allheaders;
		private Object obj;

		public String getMotifyDate() {
			return motifyDate;
		}

		public void setMotifyDate(String motifyDate) {
			this.motifyDate = motifyDate;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public Object getObj() {
			return obj;
		}

		public void setObj(Object obj) {
			this.obj = obj;
		}

		public Header[] getAllheaders() {
			return allheaders;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		private HHttpResponse(HttpRequest request) {
			mRequest = request;
			mSuccess = false;
			mResponseCode = 0;
			mResponseReasonPhrase = null;
			mResponseContentType = null;
			mResponseContentEncoding = null;
			mResponseLastModTime = 0;
			mResponseContentLength = 0;
			mResponseText = null;
			mResponseFile = null;
			if (cookies == null) {
				cookies = new Bundle();
			}
		}

		public HttpRequest getRequest() {
			return mRequest;
		}

		public boolean wasSuccess() {
			return mSuccess;
		}

		private void setResponseCode(int responseCode) {
			mResponseCode = responseCode;
			mSuccess = (mResponseCode == 200 || mResponseCode == 206);
		}

		public int getResponseCode() {
			return mResponseCode;
		}

		public String getResponseReasonPhrase() {
			return mResponseReasonPhrase;
		}

		public String getResponseContentType() {
			return mResponseContentType;
		}

		public String getResponseContentEncoding() {
			return mResponseContentEncoding;
		}

		public long getResponseLastModTime() {
			return mResponseLastModTime;
		}

		public long getResponseContentLength() {
			return mResponseContentLength;
		}

		public String getResponseText() {
			return mResponseText;
		}

		public long getRequestTime() {
			return mRequestTime;
		}

		public File getResponseFile() {
			return mResponseFile;
		}

		public boolean isRaw() {
			return mRequest.isRaw();
		}

		public void deleteRawFile() {
			if (wasSuccess() && isRaw() && mResponseFile != null) {
				mResponseFile.delete();
			}
		}

		public int getRequestCode() {
			return mRequest.getRequestCode();
		}

		public Object getTag() {
			return mRequest.getTag();
		}

		public void setTag(Object tag) {
			mRequest.setTag(tag);
		}

		public Bundle getCookies() {
			return cookies;
		}

		public void setCookies(Bundle cookies) {
			this.cookies = cookies;
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder(mRequest.toString());
			b.append("\n\nHttpResponse: "
					+ (mSuccess ? "Success: " : "Failure: ") + mResponseCode
					+ "\n");
			b.append("ReasonPhrase: " + cnull(mResponseReasonPhrase) + "\n");
			b.append("ContentType: " + cnull(mResponseContentType) + "\n");
			b.append("ContentEncoding: " + cnull(mResponseContentEncoding)
					+ "\n");
			b.append("LastModTime: " + mResponseLastModTime + "\n");
			b.append("Response: \n");
			if (!mSuccess || !isRaw()) {
				try {
					b.append(new JSONObject(mResponseText).toString(4));
				} catch (Exception e) {
					try {
						b.append(new JSONArray(mResponseText).toString(4));
					} catch (Exception e2) {
						b.append(cnull(mResponseText));
					}
				}
			} else {
				b.append("BINARY FILE");
			}
			return b.toString();
		}

		private String cnull(String str) {
			if (str == null)
				return "";
			return str;
		}

		protected void onExecuteComplete() {
			if (mRequest.getRequestFinishedListener() != null) {
				respondInBackground();
				respondInForeground();
			}
		}

		protected void respondInBackground() {
			if (wasSuccess()) {
				try {
					mRequest.getRequestFinishedListener()
							.onHttpRequestSucceededInBackground(this);
				} catch (Throwable t) {
					t.printStackTrace();
					mSuccess = false;
					mRequest.getRequestFinishedListener()
							.onHttpRequestFailedInBackground(this);
				}
			} else {
				mRequest.getRequestFinishedListener()
						.onHttpRequestFailedInBackground(this);
			}
		}

		protected void respondInForeground() {
			Runnable response = new Runnable() {

				@Override
				public void run() {
					if (wasSuccess()) {
						mRequest.getRequestFinishedListener()
								.onHttpRequestSucceeded(HHttpResponse.this);
					} else {
						mRequest.getRequestFinishedListener()
								.onHttpRequestFailed(HHttpResponse.this);
					}
				}
			};

			if (mRequest.mResponseHandler == null) {
				response.run();
			} else {
				mRequest.mResponseHandler.post(response);
			}
		}
	}

}
