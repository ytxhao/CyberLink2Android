package com.scorpio.framework.business.protocol;

import android.content.Context;

/**
 * 内容扩展模块的服务类型定义及处理类
 *
 * 
 */
public class ContentExpandServiceType {

	static public final int BASIC_GRID_PAGE = 1;

	static public final int BASIC_LIST_PAGE = 2;

	static public final int DEFINE_FLOW_PAGE = 101;

	static public final int DEFINE_COMMUNICATION_LIFE = 102;

	static public final int WEB_BASIC_WEBVIEW = 201;

	/**
	 * 根据服务类型触发动作
	 * 
	 * @param ctx
	 * @param serviceType
	 * @param Uri
	 */
	public void fireAcitonByServiceType(Context ctx, int serviceType, String Uri) {
		switch (serviceType) {
		case BASIC_GRID_PAGE:
			
			break;
		case BASIC_LIST_PAGE:
			
			break;
		case DEFINE_FLOW_PAGE:
			
			break;
		case DEFINE_COMMUNICATION_LIFE:
			
			break;
		case WEB_BASIC_WEBVIEW:
			
			break;
		default:
			
			break;
		}
	}
}
