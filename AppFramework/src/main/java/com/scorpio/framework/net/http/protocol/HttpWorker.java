package com.scorpio.framework.net.http.protocol;


import android.util.Log;

import com.scorpio.framework.config.CoreSchema;
import com.scorpio.framework.core.CoreApplication;
import com.scorpio.framework.net.http.HPackage;
import com.scorpio.framework.net.http.HttpManagerAdapter;
import com.scorpio.framework.net.http.interfaces.IHttpOperation;

public class HttpWorker implements IHttpOperation {

	@SuppressWarnings("unchecked")
	@Override
	public Object exec(HPackage fpackage) {
		// TODO Auto-generated method stub
		Class c = fpackage.getClassofT();
		if(c==null){
			c = HttpProtocolUtils.getClassByProtocol(fpackage.getpId());
		}	
		HttpManagerAdapter.getInstance().setContext(CoreApplication.getInstance());
		
		HttpManagerAdapter.getInstance().request
		(
				fpackage.getUrl(), 
				fpackage.getPostObject(), 
				HttpProtocolUtils.getMessageListenerByProtocol(fpackage.getpId(),fpackage.getPostObject()),
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
		if(CoreSchema.IS_SHOW_CONTACT){
			Log.e("http", "the class is :"+HttpProtocolUtils.getClassByProtocol(fpackage.getpId()));
		}
		
		return fpackage;
	}
	
	
	
	private static HttpWorker _inst;

	public static HttpWorker getInstance() {
		if (_inst != null) {
			return _inst;
		} else {
			_inst = new HttpWorker();
			return _inst;
		}
	}
	
}
