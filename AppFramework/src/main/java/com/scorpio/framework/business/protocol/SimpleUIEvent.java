package com.scorpio.framework.business.protocol;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.Serializable;

public class SimpleUIEvent<T> extends SimpleEvent
{
	public void fireEvent(int messageid,T msgcode) {
		// TODO Auto-generated method stub		
		
	
		synchronized (this) {
		     if(eventhandlers.size()==0)
		     {
		    	 return;
		     }	     
		     for (Handler h : eventhandlers) {
		    	Message msg = new Message();
		 		msg.what = messageid;
		 		Bundle b=new Bundle();
		 		if(msgcode instanceof String){
		 			b.putString("msg", (String)msgcode);
		 		}else{
		 			b.putSerializable("msg", (Serializable)msgcode);
		 		}
                
		 		msg.setData(b);
		 		h.sendMessage(msg);
			}
		}
	}
}

