package com.scorpio.framework.business.protocol;

import android.content.Context;

/**
 * 内容扩展模块管理器
 *
 * 
 */
public class ContentExpandManager {

	private static ContentExpandManager _inst;

	private static final String TAG = ContentExpandManager.class.getSimpleName();

	public static ContentExpandManager getInstance() {
		if (_inst != null) {
			return _inst;
		} else {
			_inst = new ContentExpandManager();
			return _inst;
		}
	}

	private ContentExpandManager() {
		init();
		return;
	}

	private void init() {
		
	}
	/**
	 * 根据模块名，触发获取网络端相应模块内容。
	 * 
	 */
	public void fireActionContentData(String modelName){
		
	}
	/**
	 * 检查传入数据
	 */
	public void checkDate(){
		
	}
	/**
	 * 处理传入数据
	 */
	public void actionCheckDate(){
		
	}
	/**
	 * 接受返回数据
	 */
	public void receiveContentData(){
		
	}
	/**
	 * 根据服务类型触发动作
	 * 
	 * @param ctx
	 * @param serviceType
	 * @param Uri
	 */
	public void fireAcitonByServiceType(Context ctx, int serviceType, String Uri){
		
	}
}
