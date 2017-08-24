package com.scorpio.framework.core;

import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * 消息通知
 *
 * 
 */
public class UIEvent {
	public List<Handler> eventhandlers = new ArrayList<Handler>();
	
	public HashMap<String,Handler> channelHandlers = new HashMap<String,Handler>();

	public void registerHandler(Handler h) {
		synchronized (this) {
			if (eventhandlers.contains(h)) {
				return;
			} else {
				eventhandlers.add(h);
			}
		}
	}

	public void unRegisterHandler(Handler h) {
		synchronized (this) {
			if (!eventhandlers.contains(h)) {
				return;
			} else {
				eventhandlers.remove(h);
			}
		}
	}
	
	public void registerHandler(Handler h, String channelID) {
		synchronized (this) {
			Handler oldh = channelHandlers.put(channelID, h);
			if(oldh!=null){
				eventhandlers.remove(oldh);
			}
			eventhandlers.add(h);
		}
	}

	public void unRegisterHandler(Handler h, String channelID) {
		synchronized (this) {
			if (!eventhandlers.contains(h)) {
				return;
			} else {
				eventhandlers.remove(h);
			}
		}
	}
}