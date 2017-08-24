package com.ytx.cyberlink2android.scorpio.business.data.update;



import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class CheckUpdateRequestModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3002410616984385279L;
	
	@Expose
	private String channelName;
	@Expose
	private String versionNum;
	@Expose
	private String machineType;

	
	
	public CheckUpdateRequestModel(String channelName, String versionName, int i) {
		// TODO Auto-generated constructor stub
		this.channelName = channelName;
		versionNum = versionName;
		machineType = i+"";
				
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "versionNum:"+versionNum+",machineType:"+machineType+",channelName:"+channelName;
	}
}
