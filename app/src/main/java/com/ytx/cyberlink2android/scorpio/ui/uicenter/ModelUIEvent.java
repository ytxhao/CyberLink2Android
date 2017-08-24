package com.ytx.cyberlink2android.scorpio.ui.uicenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.scorpio.framework.core.UIEvent;

import java.io.Serializable;


public class ModelUIEvent<T> extends UIEvent {
	public void fireEvent(int messageid, T msgcode) {
		// TODO Auto-generated method stub

		synchronized (this) {
			if (eventhandlers.size() == 0) {
				return;
			}
			for (Handler h : eventhandlers) {
				Message msg = new Message();
				msg.what = messageid;
				Bundle b = new Bundle();
				if (msgcode instanceof String) {
					b.putString("msg", (String) msgcode);
				} else {
					b.putSerializable("obj", (Serializable) msgcode);
				}

				msg.setData(b);
				h.sendMessage(msg);
			}
		}
	}

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
}
