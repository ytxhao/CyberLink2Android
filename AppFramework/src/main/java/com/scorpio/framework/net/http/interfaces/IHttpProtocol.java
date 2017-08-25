package com.scorpio.framework.net.http.interfaces;


import com.scorpio.framework.business.protocol.ResultModel;
import com.scorpio.framework.net.http.HPackage;

public interface IHttpProtocol<T,C> {
	
	/**
	 * 获取业务引擎传入的业务数据，并根据网络交互文档，封装待发送的网络包。
	 * 
	 * @param obj
	 * @return
	 */
	HPackage sendPackage(T obj);
	
	/**
	 * 提供网络应答中json结构待匹配的数据模型
	 * 
	 * @return
	 */
	Class<C> getResponseClass();
	
	/**
	 * 获取业务类型，请统一在MessageTypeID中指定。
	 * 
	 * @return
	 */
	int getProtocolId();
	
	/**
	 * 对网络应答包的响应处理。返回的数据结构供业务引擎处理。(请注意填写返回ResultModel对象的tag值,该值为业务码,请统一在MessageTypeID中指定).
	 * 
	 * @param htm
	 * @return
	 */
	ResultModel HandleResponse(HResponse htm);

}
