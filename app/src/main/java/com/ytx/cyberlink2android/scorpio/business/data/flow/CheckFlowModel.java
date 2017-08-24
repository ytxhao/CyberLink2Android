package com.ytx.cyberlink2android.scorpio.business.data.flow;


import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 服务器返回简单应答
 *
 *
 */
public class CheckFlowModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1321238206773406292L;
	@Expose
	private String res_code;
	@Expose
	private CheckFlowData res_desc;

	public String getRes_code() {
		return res_code;
	}

	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}

	public CheckFlowData getRes_desc() {
		return res_desc;
	}

	public void setRes_desc(CheckFlowData res_desc) {
		this.res_desc = res_desc;
	}

	@Override
	public String toString() {
		return "CheckFlowModel [res_code=" + res_code + ", res_desc=" + res_desc
				+ "]";
	}
	
	
}
