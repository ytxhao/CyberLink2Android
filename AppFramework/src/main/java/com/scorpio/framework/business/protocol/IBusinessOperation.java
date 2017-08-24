package com.scorpio.framework.business.protocol;


import java.io.Serializable;


/**
 * 引擎处理网络回调接口
 *
 *
 */
public interface IBusinessOperation<T> {
	
	/**
	 * 业务处理前的预处理
	 * 
	 * @param obj
	 * @return
	 */
	Serializable preHandle(Serializable obj);
	
	/**
	 * 根据传回的数据进行业务层的相应处理,并通知UI,触发UI相应事件
	 * 
	 * @param rsultModel
	 */
	void postHandle(ResultModel rsultModel);
	
	/**
	 * 本业务的ID标示，请统一在MessageTypeID中指定。
	 * 
	 * @return
	 */
	int getBusinessId();

}

