package com.scorpio.framework.business.protocol.http;

import com.scorpio.framework.net.http.HPackage;

import java.util.HashSet;
import java.util.Set;

/**
 * http协议封装引擎
 *
 *
 */

public class HttpProtocolEngine implements IHttpProtocolEngine{
	protected static HttpProtocolEngine _inst;

	public static HttpProtocolEngine getInstance() {
		if (_inst != null) {
			return _inst;
		} else {
			_inst = new HttpProtocolEngine();
			return _inst;
		}
	}
	
	public void onDestroy(){
		
	}
	
	static private Set<IHttpProtocolEngine> httpProtocols;
	
	public void registerHttpProtocolHandler(IHttpProtocolEngine handler){
		if(httpProtocols == null){
			httpProtocols = new HashSet<IHttpProtocolEngine>();
		}
		httpProtocols.add(handler);
	}

	public HPackage sendHPackage(int protocolId, Object obj) {
		if(httpProtocols!=null&&httpProtocols.size()>0){
			HPackage hp = null;
			for(IHttpProtocolEngine handler:httpProtocols){
				hp = handler.sendPackage(protocolId, obj);
				if(hp!=null){
					return hp;
				}
			}
		}
		return null;
	}

	@Override
	public HPackage sendPackage(int protocolId, Object obj) {
		return null;
	}
	
}
