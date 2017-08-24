package com.scorpio.framework.business.protocol;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.scorpio.framework.net.http.HttpBoss;

import java.io.Serializable;


public class HttpCenter {
	public  static final String TAG = HttpCenter.class.getSimpleName();
	private static HttpCenter _ins;	
	public Handler mHandler;
	
	public HttpCenter()
	{
		 mHandler = new Handler() {
             public void handleMessage(Message msg) {
               // process incoming messages here 
            	Bundle r=msg.getData();
				Serializable s =r.getSerializable(MessageDataKey.SIMPLE_TASK);
				HttpBoss.handle(msg.what, s);
             }
         };
	}
	
	public Handler getHandler()
	{
		return mHandler;
	}
}

