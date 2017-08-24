package com.ytx.cyberlink2android.scorpio.business.opt.update;



import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class VersionResponseEntity implements Serializable {

	private static final long serialVersionUID = -6697509877202514168L;
	@Expose
	public String versionNum;
	@Expose
	public String versionDes;
	@Expose
	public String appurl;
	@Expose
	public String isUpdate;
	
	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public String getVersionDes() {
		return versionDes;
	}

	public void setVersionDes(String versionDes) {
		this.versionDes = versionDes;
	}

	public String getAppurl() {
		return appurl;
	}

	public void setAppurl(String appurl) {
		this.appurl = appurl;
	}

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	

}
