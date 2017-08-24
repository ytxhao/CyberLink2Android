package com.scorpio.framework.business.protocol;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.scorpio.framework.core.UICenterDef;
import com.scorpio.framework.core.UIEvent;
import com.scorpio.framework.utils.ScoLog;

import java.io.Serializable;


/**
 * 简单事件模型对象
 *
 * 
 */
public class SimpleEvent extends UIEvent {


	private static final String TAG = SimpleEvent.class.getSimpleName();
	public static final int ERR_TIME_OUT = -18;

	public static final int ERR_DOWNLOAD_CENTER = -10;

	public static final int ERR_DOWNLOAD_IN_GET_INFO = -11;

	public static final int ERR_DOWNLOAD_IN_NO_OVER = -12;

	public static final int ERR_DOWNLOAD_IN_THREAD_NETWORK = -13;

	public static final int ERR_DOWNLOAD_NO_SHOW_UI = -777;

	public void fireEvent(int messageid, String msgcode) {
		// TODO Auto-generated method stub

		synchronized (this) {
			if (eventhandlers.size() == 0) {
				return;
			}
			for (Handler h : eventhandlers) {
				Message msg = new Message();
				msg.what = messageid;
				Bundle b = new Bundle();
				b.putString("msg", msgcode);
				msg.setData(b);
				if (h != null) {
					h.sendMessage(msg);
				}

			}
		}
	}

	public void fireEventByPacketId(int messageid, String packetId) {
		// TODO Auto-generated method stub

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

	public void fireEvent(int messageid, String msgcode, Object obj) {
		// TODO Auto-generated method stub
		synchronized (this) {
			if (eventhandlers.size() == 0) {
				return;
			}
			for (Handler h : eventhandlers) {
				Message msg = new Message();
				msg.what = messageid;
				msg.obj = obj;
				Bundle b = new Bundle();
				b.putString("msg", msgcode);
				msg.setData(b);
				if (h != null) {
					h.sendMessage(msg);
				}
			}
		}
	}
	
	public void fireEvent(int messageid, int eventCode, String msgcode, Object obj) {
		// TODO Auto-generated method stub
		synchronized (this) {
			if (eventhandlers.size() == 0) {
				return;
			}
			for (Handler h : eventhandlers) {
				Message msg = new Message();
				msg.what = messageid;
				msg.obj = obj;
				Bundle b = new Bundle();
				b.putString(UICenterDef.KEYWORK_CODE_MESSAGE, msgcode);
				b.putInt(UICenterDef.KEYWORK_CODE_EVENT, eventCode);
				msg.setData(b);
				if (h != null) {
					h.sendMessage(msg);
				}
			}
		}
	}
	
	public void fireEvent(int messageid, int eventCode, Serializable fromObj, Object obj) {
		// TODO Auto-generated method stub
		synchronized (this) {
			if (eventhandlers.size() == 0) {
				return;
			}
			for (Handler h : eventhandlers) {
				Message msg = new Message();
				msg.what = messageid;
				msg.obj = obj;
				Bundle b = new Bundle();
				b.putSerializable(UICenterDef.KEYWORK_CODE_OBJECT, fromObj);
				b.putInt(UICenterDef.KEYWORK_CODE_EVENT, eventCode);
				msg.setData(b);
				if (h != null) {
					h.sendMessage(msg);
				}
			}
		}
	}

	public void fireEvent(int messageid, int id) {
		fireEvent(messageid, id, 0,null, null,0);
	}
//
//	public void fireEvent(int messageid, int id, String packageId,
//			String filePath) {
//		fireEvent(messageid, id, 0, packageId, filePath);
//	}
//
//	public void fireEvent(int messageid, int id, int fileLen, String packageId,
//			String filePath) {
//		fireEvent(messageid, id, 0, packageId, filePath, 0);
//	}
//
	public void fireEvent(int messageid, int id, int fileLen, String packageId,
                          String filePath, int speed) {
		// TODO Auto-generated method stub

		synchronized (this) {
			if (eventhandlers.size() == 0) {
				return;
			}
			for (Handler h : eventhandlers) {
				Message msg = new Message();
				msg.what = messageid;
				Bundle b = new Bundle();
				b.putString("packageId", packageId);
				b.putString("filePath", filePath);
				b.putInt("speed", speed);
				b.putInt("size", fileLen);
				b.putInt("code", id);
				msg.setData(b);
				if (msg != null && h != null) {
					h.sendMessage(msg);
				} else {
					ScoLog.E(TAG,eventhandlers.toString());
				}

			}
		}
	}
}
