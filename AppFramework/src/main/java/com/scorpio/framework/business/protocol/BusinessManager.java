package com.scorpio.framework.business.protocol;

import com.scorpio.framework.utils.ScoLog;

import java.util.ArrayList;
import java.util.List;


/**
 * 后台业务管理(注册中心)
 * 
 */
public class BusinessManager {

	private static final String TAG = BusinessManager.class.getSimpleName();
	protected static BusinessManager _inst;

	public static BusinessManager getInstance() {
		if (_inst != null) {
			return _inst;
		} else {
			_inst = new BusinessManager();
			return _inst;
		}
	}

	protected BusinessManager() {
		if (BusinessDispatcher.getInstance().optMap != null) {
			BusinessDispatcher.getInstance().optMap.init(null);
		} else {
			ScoLog.E(TAG, "the optMap is null");
		}
	}

	public void onDestroy() {

	}

	@SuppressWarnings("rawtypes")
	static public List<IBusinessOperation> cps;

	static {
		// acount
//		addOperation(UpdateProfileOpt.getInstance());
//		addOperation(PasswordChangeOpt.getInstance());
//		addOperation(ResetPasswordByPhoneOpt.getInstance());
//
//		// group
//		addOperation(CreateGroupOpt.getInstance());
//		addOperation(AddFavorGroupOpt.getInstance());
//		addOperation(AddGroupMemberOpt.getInstance());
//		addOperation(DeleteGroupFavorOpt.getInstance());
//		addOperation(DeleteGroupMemberOpt.getInstance());
//		addOperation(GetFavorGroupOpt.getInstance());
//		addOperation(GetGroupOpt.getInstance());
//		addOperation(QuitGroupOpt.getInstance());
//		addOperation(ChangeFavorNameOpt.getInstance());
//
//		// im
//		addOperation(ChatMsgRcvOpt.getInstance());
//		addOperation(ChatMsgSendOpt.getInstance());
//		addOperation(RichMsgSendOpt.getInstance());
//		addOperation(RichMsgRcvOpt.getInstance());
//
//		// profile
//		addOperation(GetMyProfileOpt.getInstance());
//		addOperation(GetMyRecentOpt.getInstance());
//		addOperation(GetUserProfileOpt.getInstance());
//		addOperation(GetUserRecentOpt.getInstance());
//		addOperation(HiddenBabyOpt.getInstance());
//		addOperation(UpdateRecentOpt.getInstance());
//		addOperation(UpdateRelationTimeOpt.getInstance());
//
//		// maintain
//		addOperation(AndroidUpdateOpt.getInstance());
	}

	@SuppressWarnings("rawtypes")
	protected static void addOperation(IBusinessOperation operation) {
		if (cps == null) {
			cps = new ArrayList<IBusinessOperation>();
		}
		cps.add(operation);
	}

	@SuppressWarnings("rawtypes")
	public IBusinessOperation searchIBusinessOperationByBusinessId(int bid) {
		return BusinessDispatcher.getInstance().optMap.get(bid);
	}

}