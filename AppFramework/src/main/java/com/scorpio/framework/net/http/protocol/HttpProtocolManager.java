package com.scorpio.framework.net.http.protocol;


import com.scorpio.framework.net.http.interfaces.IHttpProtocol;

import java.util.ArrayList;
import java.util.List;

public class HttpProtocolManager {
	
	static public List<IHttpProtocol> wops;
	static {			
//			addOperation(CreateGroupHandle.getInstance());
		}
	
	public static void addOperation(IHttpProtocol operation) {
		if(wops ==null){
			wops = new ArrayList<IHttpProtocol>();
		}
		wops.add(operation);
	}
}
