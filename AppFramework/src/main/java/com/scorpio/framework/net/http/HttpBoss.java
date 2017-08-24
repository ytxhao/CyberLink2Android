package com.scorpio.framework.net.http;

import com.scorpio.framework.business.model.BusinessContent;
import com.scorpio.framework.business.protocol.http.HttpProtocolEngine;
import com.scorpio.framework.net.http.interfaces.IHttpOperation;
import com.scorpio.framework.net.http.interfaces.IHttpProtocol;
import com.scorpio.framework.net.http.protocol.SimpleHttpHandle;
import com.scorpio.framework.utils.ScoLog;

import java.io.Serializable;



public class HttpBoss {

	private static final String TAG = HttpBoss.class.getSimpleName();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public void handle(int id, Serializable obj, BusinessContent bct){
		IHttpProtocol fhp = HttpConnectionManager.getInstance().getHttpProtocolById(id);
		if(fhp==null){
			fhp = SimpleHttpHandle.getInstance();
		}
		if(bct!=null){
			bct.setPostValue(obj);
			obj = bct;
		}
		HPackage fpackage ;
		if(fhp instanceof SimpleHttpHandle){
			fpackage = HttpProtocolEngine.getInstance().sendHPackage(id, obj);
		}else{
			fpackage = fhp.sendPackage(obj);
		}
		if(fpackage==null){
			ScoLog.E(TAG, "businessId:"+id+";the fpackage is null.....");
			return;
		}
		fpackage.setpId(id);
		if(bct!=null){
			fpackage.setClassofT(bct.getClassofT());
			fpackage.setBct(bct);
		}
		
		IHttpOperation ho = HttpConnectionManager.getInstance().getHttpOperation();
		ho.exec(fpackage);
	}
	
	static public void handle(int id,Serializable obj){
		handle(id,obj,null);
	}
	
}
