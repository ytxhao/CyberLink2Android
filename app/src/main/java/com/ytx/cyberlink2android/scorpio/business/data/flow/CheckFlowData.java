package com.ytx.cyberlink2android.scorpio.business.data.flow;



import com.google.gson.annotations.Expose;

import java.io.Serializable;


public class CheckFlowData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7281018494197208716L;
	@Expose
	private String detailenddate;
	@Expose
	private String detailstartdate;
	@Expose
	private String flowpackagename;
	@Expose
	private String nettype;
	@Expose
	private String productname;
	@Expose
	private String totalflow;
	@Expose
	private String usedflow;
	public String getDetailenddate() {
		return detailenddate;
	}
	public void setDetailenddate(String detailenddate) {
		this.detailenddate = detailenddate;
	}
	public String getDetailstartdate() {
		return detailstartdate;
	}
	public void setDetailstartdate(String detailstartdate) {
		this.detailstartdate = detailstartdate;
	}
	public String getFlowpackagename() {
		return flowpackagename;
	}
	public void setFlowpackagename(String flowpackagename) {
		this.flowpackagename = flowpackagename;
	}
	public String getNettype() {
		return nettype;
	}
	public void setNettype(String nettype) {
		this.nettype = nettype;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getTotalflow() {
		return totalflow;
	}
	public void setTotalflow(String totalflow) {
		this.totalflow = totalflow;
	}
	public String getUsedflow() {
		return usedflow;
	}
	public void setUsedflow(String usedflow) {
		this.usedflow = usedflow;
	}
	@Override
	public String toString() {
		return "CheckFlowData [detailenddate=" + detailenddate
				+ ", detailstartdate=" + detailstartdate + ", flowpackagename="
				+ flowpackagename + ", nettype=" + nettype + ", productname="
				+ productname + ", totalflow=" + totalflow + ", usedflow="
				+ usedflow + "]";
	}
	
	
}
