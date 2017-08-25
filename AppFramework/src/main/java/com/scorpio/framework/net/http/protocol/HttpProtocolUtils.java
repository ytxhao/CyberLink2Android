package com.scorpio.framework.net.http.protocol;

import android.os.Bundle;
import android.util.Log;


import com.scorpio.framework.data.dm.DataManagerInterface;
import com.scorpio.framework.net.http.HttpConnectionManager;
import com.scorpio.framework.net.http.interfaces.HandleMessageListener;
import com.scorpio.framework.net.http.interfaces.IHttpProtocol;
import com.scorpio.framework.net.http.interfaces.MessageListener;
import com.scorpio.framework.utils.ScoLog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpProtocolUtils {    

    private static final String TAG = HttpProtocolUtils.class.getSimpleName();
    public static String read(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);

        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }

        in.close();
        return sb.toString();
    }

    public static String encodeParams(Bundle params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String key : params.keySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }

            try {
                sb.append(URLEncoder.encode(key, "utf-8")).append("=").append(URLEncoder.encode(params.getString(key), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }

        return sb.toString();
    }

    public static Bundle decodeParams(String query) {
        Bundle params = new Bundle();

        if (query != null) {
            String[] parts = query.split("&");

            for (String param : parts) {
                String[] item = param.split("=");
                params.putString(URLDecoder.decode(item[0]), URLDecoder.decode(item[1]));
            }
        }

        return params;
    }

    public static List<NameValuePair> bundleToList(Bundle params) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        for (String key : params.keySet()) {
            pairs.add(new BasicNameValuePair(key, params.getString(key)));
        }

        return pairs;
    }    
    
	public static void addOperation(String model, DataManagerInterface operation, HashMap<String,DataManagerInterface> hdms) {
    	
		if(hdms ==null){
			hdms = new HashMap<String,DataManagerInterface>();
		}
		hdms.put(model,operation);
	}
	
	 public static void ProtocolDataInit(){
		 HashMap<String,DataManagerInterface> hdms = HttpConnectionManager.hdms;
		 if(hdms !=null){			
			 for(String dmi:hdms.keySet()){
				 hdms.get(dmi).init(null);
			 }
		 }		 
	 }
    
    public static MessageListener getMessageListenerByProtocol(int key, Object object){
//    	IHttpProtocol fhp = ConnectionManager.getInstance().getHttpProtocolById(key);
    	MessageListener ml = new HandleMessageListener(key,object);
    		
//    		new MessageListener(){
//			@Override
//			public void onFailed(HttpResponseException err) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onMessage(Object message, EzHttpResponse response) {
//				// TODO Auto-generated method stub
//				
//			}};
    	return ml;
    }
    
    public static Class getClassByProtocol(int key){
    	if(HttpConnectionManager.hdms==null){
            ScoLog.d(TAG, "the HttpConnectionManager.hdms==null");
    		return null;
    	}
    	DataManagerInterface<Integer,Class> classes = HttpConnectionManager.hdms.get(HttpConnectionManager.HDM_RES);
    	if(classes!=null){
    		ScoLog.d(TAG, "the HttpConnectionManager.hdms 's len "+classes.getSize()+";the id is:"+key);
    		Class obj = classes.get(key);
    		if(obj==null){
                ScoLog.d(TAG, "the HttpConnectionManager.hdms 's obj ==null");
        		return null;
        	}
        	return obj;
    	}
    	
    	return null;
    }
    
    public static IHttpProtocol getIHttpProtocolByProtocol(int key){
    	if(HttpConnectionManager.hdms==null){
    		return null;
    	}
    	Object obj = HttpConnectionManager.hdms.get(HttpConnectionManager.HDM_OPT).get(key);
    	if(obj==null){
    		return null;
    	}
    	return (IHttpProtocol)obj;
    }
    
   
    
    
}
