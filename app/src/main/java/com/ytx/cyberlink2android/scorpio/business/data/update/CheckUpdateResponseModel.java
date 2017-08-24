package com.ytx.cyberlink2android.scorpio.business.data.update;



import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;


public class CheckUpdateResponseModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9154993894107483664L;

	@Expose
	private List<ListVersionInfo> listVersionInfo;

	@Expose
	private String res_code;
	@Expose
	private String res_desc;
	
	
	public List<ListVersionInfo> getListVersionInfo() {
		return listVersionInfo;
	}

	public void setListVersionInfo(List<ListVersionInfo> listVersionInfo) {
		this.listVersionInfo = listVersionInfo;
	}

	public String getRes_code() {
		return res_code;
	}

	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}

	public String getRes_desc() {
		return res_desc;
	}

	public void setRes_desc(String res_desc) {
		this.res_desc = res_desc;
	}
	
	public static class ListVersionInfo implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1680005627729588172L;
		
		@Expose
		public String current_update_type;
		@Expose
		public String info_connect_downloadurl;
		@Expose
		public String update_channel;
		@Expose
		public String version_info_description;
		@Expose
		public String version_info_title;
		@Expose
		public String version_info_type;
		@Expose
		public String version_info_update_type;
		@Expose
		public String version_number;
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return current_update_type+":"+info_connect_downloadurl+":"+
			update_channel+":"+
			version_info_description+":"+
			version_info_title+":"+
			version_info_type+":"+
					version_info_update_type +":"+
			version_number;
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return listVersionInfo+":"+res_code+":"+res_desc;
	}
}
