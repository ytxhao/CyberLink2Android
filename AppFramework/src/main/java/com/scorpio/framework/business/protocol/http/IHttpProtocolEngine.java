package com.scorpio.framework.business.protocol.http;


import com.scorpio.framework.net.http.HPackage;

public interface IHttpProtocolEngine {
	public HPackage sendPackage(int protocolId, Object obj);
}
