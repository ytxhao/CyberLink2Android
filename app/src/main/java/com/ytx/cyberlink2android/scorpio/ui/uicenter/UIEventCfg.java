package com.ytx.cyberlink2android.scorpio.ui.uicenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.scorpio.framework.core.UIEvent;
import com.scorpio.framework.utils.ScoLog;


/**
 * 简单事件模型对象
 * 
 * 
 */
public class UIEventCfg extends UIEvent {

	public static final int ERR_TIME_OUT = -18;

	public static final int ERR_DOWNLOAD_CENTER = -10;

	public static final int ERR_DOWNLOAD_IN_GET_INFO = -11;

	public static final int ERR_DOWNLOAD_IN_NO_OVER = -12;

	public static final int ERR_DOWNLOAD_IN_THREAD_NETWORK = -13;

	public static final int ERR_DOWNLOAD_NO_SHOW_UI = -777;

	public void fireEvent(int messageid, String msgcode) {
		synchronized (this) {
			if (eventhandlers.size() == 0) {
				return;
			}
			for (Handler h : eventhandlers) {
				Message msg = new Message();
				msg.what = messageid;
				Bundle b = new Bundle();
				b.putString(UICenter.KEYWORK_CODE_MESSAGE, msgcode);
				msg.setData(b);
				if (h != null) {
					h.sendMessage(msg);
				}

			}
		}
	}
	
	public void fireEvent(int messageid, String[] msgs) {
		synchronized (this) {
			if (eventhandlers.size() == 0) {
				return;
			}
			for (Handler h : eventhandlers) {
				Message msg = new Message();
				msg.what = messageid;
				Bundle b = new Bundle();
				int i = 0;
				for(String msgvalue:msgs){
					if(i==0){
						b.putString(UICenter.KEYWORK_CODE_MESSAGE, msgvalue);
					}else{
						b.putString(UICenter.KEYWORK_CODE_MESSAGE+i, msgvalue);
					}
					i++;
				}
				msg.setData(b);
				if (h != null) {
					h.sendMessage(msg);
				}
			}
		}
	}

	public void fireEventByPacketId(int messageid, String packetId) {
		synchronized (this) {
			if (eventhandlers.size() == 0) {
				return;
			}
			for (Handler h : eventhandlers) {
				Message msg = new Message();
				msg.what = messageid;
				Bundle b = new Bundle();
				b.putString("packageId", packetId);
				msg.setData(b);
				if (h != null) {
					h.sendMessage(msg);
				}

			}
		}
	}

	public void fireEvent(int messageid, int id, Object obj) {
		synchronized (this) {
			if (eventhandlers.size() == 0) {
				return;
			}
			for (Handler h : eventhandlers) {
				Message msg = new Message();
				msg.what = messageid;
				msg.obj = obj;
				Bundle b = new Bundle();
				b.putInt(UICenter.KEYWORK_CODE_CODE, id);
				msg.setData(b);
				if (h != null) {
					h.sendMessage(msg);
				}
			}
		}
	}
	
	public Message getMessage(int messageid, int id, Object obj){
		Message msg = new Message();
		msg.what = messageid;
		msg.obj = obj;
		Bundle b = new Bundle();
		b.putInt(UICenter.KEYWORK_CODE_CODE, id);
		msg.setData(b);
		return msg;
	}
	
	public Message getMessageException(int messageid, String msgcode, Object obj){
		Message msg = new Message();
		msg.what = messageid;
		msg.obj = obj;
		Bundle b = new Bundle();
		b.putString(UICenter.KEYWORK_CODE_MESSAGE, msgcode);
		msg.setData(b);
		return msg;
	}
	
	public void fireEvent(int messageid, String msgcode, Object obj) {
		synchronized (this) {
			if (eventhandlers.size() == 0) {
				return;
			}
			for (Handler h : eventhandlers) {
				Message msg = new Message();
				msg.what = messageid;
				msg.obj = obj;
				Bundle b = new Bundle();
				b.putString(UICenter.KEYWORK_CODE_MESSAGE, msgcode);
				msg.setData(b);
				if (h != null) {
					h.sendMessage(msg);
				}
			}
		}
	}

	public void fireEvent(int messageId, int id) {
		fireEvent(messageId, id, null, null);
	}

	public void fireEvent(int messageId, int id, String packageId,
			String filePath) {
		fireEvent(messageId, id, 0, packageId, filePath);
	}

	public void fireEvent(int messageId, int id, int fileLen, String packageId,
			String filePath) {
		fireEvent(messageId, id, 0, packageId, filePath, 0);
	}

	public void fireEvent(int messageId, int id, int fileLen, String packageId,
                          String filePath, int speed) {
		synchronized (this) {
			if (eventhandlers.size() == 0) {
				return;
			}
			for (Handler h : eventhandlers) {
				Message msg = new Message();
				msg.what = messageId;
				Bundle b = new Bundle();
				b.putString("packageId", packageId);
				b.putString("filePath", filePath);
				b.putInt("speed", speed);
				b.putInt("size", fileLen);
				b.putInt(UICenter.KEYWORK_CODE_CODE, id);
				msg.setData(b);
				if (msg != null && h != null) {
					h.sendMessage(msg);
				} else {
					ScoLog.E("core", eventhandlers.toString());
				}

			}
		}
	}

	public void fireEvent(int messageId,
			Object obj) {
		synchronized (this) {
			if (eventhandlers.size() == 0) {
				return;
			}
			for (Handler h : eventhandlers) {
				Message msg = new Message();
				msg.what = messageId;
				msg.obj = obj;
				if (h != null) {
					h.sendMessage(msg);
				}
			}
		}
	}
	
	
}
