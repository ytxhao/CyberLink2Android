package com.ytx.cyberlink2android.scorpio.business.opt.update;

import android.util.Log;


import com.scorpio.framework.business.handler.DefaultBusinessOpt;
import com.scorpio.framework.business.protocol.ResultModel;
import com.scorpio.framework.utils.ScoLog;
import com.ytx.cyberlink2android.application.LinkApplication;
import com.ytx.cyberlink2android.scorpio.business.cfg.MessageTypeID;
import com.ytx.cyberlink2android.scorpio.business.data.update.CheckUpdateResponseModel;
import com.ytx.cyberlink2android.scorpio.ui.uicenter.UICenter;
import com.ytx.cyberlink2android.utils.WifiAdmin;

import java.io.Serializable;



public class VersionUpgradeForceBusinessOpt extends DefaultBusinessOpt {

	public final static String TAG = "VersionUpgradeForceBusinessOpt";

	private static VersionUpgradeForceBusinessOpt _inst=null;
	
	private VersionUpgradeForceBusinessOpt() {
		// TODO Auto-generated constructor stub
	}
	
	public static VersionUpgradeForceBusinessOpt getInstance(){
		if(_inst==null){
			_inst = new VersionUpgradeForceBusinessOpt();
			return _inst;
		}else{
			return _inst;
		}
	}
	
	
	@Override
	public int getBusinessId() {
		// TODO Auto-generated method stub
		return MessageTypeID.CHECK_FORCE_UPDATE;
	}

	@Override
	public Serializable doTask(Serializable obj) {
		// TODO Auto-generated method stub
		ScoLog.d(TAG, "VersionUpgradeForceBusinessOpt doTask");
		return obj;
	}

	@Override
	public void handleSucceedResult(Object obj, ResultModel resultModel) {
		// TODO Auto-generated method stub
		ScoLog.d(TAG, "VersionUpgradeForceBusinessOpt handleSucceedResult");
		if (obj instanceof CheckUpdateResponseModel) {
			CheckUpdateResponseModel curm = (CheckUpdateResponseModel) obj;

			ScoLog.d(TAG, "VersionUpgradeForceBusinessOpt curm="+curm);

			if(curm.getListVersionInfo()!=null && !curm.getListVersionInfo().toString().equals("[]")){
				ScoLog.d(TAG, "VersionUpgradeForceBusinessOpt version_info_update_type="+curm.getListVersionInfo().get(0).version_info_update_type);

				ScoLog.d(TAG, "版本号：" + curm.getListVersionInfo().get(0).version_number);
				if (curm.getListVersionInfo().get(0).version_number != null) {
					checkVersion(LinkApplication.getInstance().getVersionName(),
							curm);
				}
			}else{
				UICenter.event_setting.fireEvent(MessageTypeID.CHECK_FORCE_UPDATE, 2);
			}
			

		}
	}

	@Override
	public void handleFailResult(String failMsg, boolean isNetworkFail, ResultModel resultModel) {
		// TODO Auto-generated method stub
		ScoLog.d(TAG,  "VersionUpgradeForceBusinessOpt handleFailResult");
		super.handleFailResult(failMsg, isNetworkFail, resultModel);
	}
	
	private void checkVersion(String localVersion, CheckUpdateResponseModel version) {
		String[] strs = { localVersion, version.getListVersionInfo().get(0).version_number };
		String maxstr = "";
		if (!localVersion.equals(version.getListVersionInfo().get(0).version_number)) {
			maxstr = getMaxVersion(strs);
		} else {
			
		}
		if ((version.getListVersionInfo().get(0).version_number != null && localVersion != null && 
				version.getListVersionInfo().get(0).version_number.equals(maxstr))) {
			// 更新界面
			ScoLog.d(TAG, "VersionUpgradeForceBusinessOpt 发现新版本，提醒用户更新");
			if (WifiAdmin.getNetworkType(LinkApplication.getInstance()
					.getApplicationContext()) == WifiAdmin.NETWORK_WIFI) {
				ScoLog.d(TAG, "VersionUpgradeForceBusinessOpt checkVersion 5");
				UICenter.event_setting.fireEvent(MessageTypeID.VERSION_UPGRADE,
						0, version);
			} else {
				ScoLog.d(TAG, "VersionUpgradeForceBusinessOpt checkVersion 6");
				UICenter.event_setting.fireEvent(MessageTypeID.VERSION_UPGRADE,
						1, version);
			}
		} else {
			ScoLog.d(TAG, "VersionUpgradeForceBusinessOpt checkVersion 7");
			// showDialog(null, "已是最新版本","确定","取消");
			Log.d("UPDATE", "VersionUpgradeForceBusinessOpt 超前版本");
			UICenter.event_setting.fireEvent(MessageTypeID.VERSION_UPGRADE, 2);
		}
	}

	/**
	 * @param strs
	 *            接收待比较的版本号 数组
	 * @return 返回最大的版本号字符串
	 */
	static String getMaxVersion(String[] strs) {
		String maxStr = "s";
		for (int i = 0; i < strs.length - 1; i++) {
			if (compare(strs[i], strs[i + 1], 0) == 1) {
				maxStr = strs[i];
			} else if (compare(strs[i], strs[i + 1], 0) == -1) {
				maxStr = strs[i + 1];
			}
		}
		return maxStr;
	}

	/**
	 * @param s1
	 *            待比较的版本号1
	 * @param s2
	 *            待比较的版本号2
	 * @param start
	 *            开始比较的起始位置
	 * @return 返回- 1:s1<s2; 1:s1>s2; 0:s1==s2
	 */
	static int compare(String s1, String s2, int start) { // 当出现类似s1="1.1",s2="1.1.2"的情况,返回-1
		if (s1.length() <= start && s2.length() > start) {
			return -1;
		}
		// 当出现类似s1="1.1.2",s2="1.1"的情况,返回-1
		if (s1.length() > start && s2.length() <= start) {
			return 1;
		}

		else {
			// 若出现当前比较位所在字符相等，则采用递归，比较下一个出现的字符
			if (s1.charAt(start) == s2.charAt(start)) {
				return compare(s1, s2, start + 2);
			}
			// 当出现类似s1="1.1",s2="1.2"的情况
			else if (s1.charAt(start) < s2.charAt(start)) {
				return -1;
			}
			// 当出现类似s1="1.2",s2="1.1"的情况
			else if (s1.charAt(start) > s2.charAt(start)) {
				return 1;
			}
			return 0;
		}
	}
	
}
