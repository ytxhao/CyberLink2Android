package com.scorpio.framework.net.http.protocol;

import com.scorpio.framework.business.model.BusinessContent;
import com.scorpio.framework.business.protocol.ResultModel;
import com.scorpio.framework.config.CoreSchema;
import com.scorpio.framework.net.http.HPackage;
import com.scorpio.framework.net.http.HPackageFactory;
import com.scorpio.framework.net.http.interfaces.HResponse;
import com.scorpio.framework.net.http.interfaces.IHttpProtocol;
import com.scorpio.framework.utils.ScoLog;

import java.io.Serializable;


public class SimpleHttpHandle implements IHttpProtocol<Serializable,Object> {

	private static final String TAG = SimpleHttpHandle.class.getSimpleName();
	@Override
	public ResultModel HandleResponse(HResponse htm) {
		// TODO Auto-generated method stub
		ResultModel rm = new ResultModel(htm);
		if(CoreSchema.IS_SHOW_CONTACT){
			ScoLog.E(TAG, "the response is:"+htm);
		}		
		switch(htm.getResultId()){
		case HResponse.TAG_MESSAGE:
			Object s = htm.getMessage();
			if(s==null){
				rm.setResultId(ResultModel.TAG_FAIL);
				if(CoreSchema.IS_SHOW_CONTACT){
					ScoLog.E(TAG, "the response is null");
				}
			}else if(s instanceof IErrno){
				int errno = ((IErrno) s).getErrno();
				rm.setErrno(errno);
				if(errno==0){
					if(CoreSchema.IS_SHOW_CONTACT){
						ScoLog.E(TAG, "the s is:"+s);
					}					
					rm.setResultId(ResultModel.TAG_OK);
				}else{
					if(CoreSchema.IS_SHOW_CONTACT){
						ScoLog.E(TAG, "the s is:"+s);
					}					
					rm.setResultId(ResultModel.TAG_FAIL);
				}
			}else{
				rm.setResultId(ResultModel.TAG_OK);
			}
			break;
			
		case HResponse.TAG_NETWORK_FAIL:
			if(rm.getResponseId()==NOT_MODIFIED){
				rm.getDate();
				rm.setResponseId(ResultModel.TAG_OK);				
			}else{
				rm.setResponseId(ResultModel.TAG_NETWORK_FAIL);
			}			
			break;
		}
//		rm.setTagKey("CreateGroupHandle");
		return rm;
	}
	private static final int NOT_MODIFIED = 304;
	@Override
	public int getProtocolId() {
		// TODO Auto-generated method stub
		return -1234;
	}

	@Override
	public Class getReponseClass() {
		// TODO Auto-generated method stub
		return null;
	}

	

	private static SimpleHttpHandle _inst;

	public static SimpleHttpHandle getInstance() {
		if (_inst != null) {
			return _inst;
		} else {
			_inst = new SimpleHttpHandle();
			return _inst;
		}
	}

	

	@Override
	public HPackage sendPackage(Serializable obj) {
				HPackage f;
				if(obj!=null&&obj instanceof BusinessContent){
					BusinessContent bc = (BusinessContent)obj;
					Serializable pobj = bc.getPostValue();
					int netmode = bc.getNetMode();
					
					if(netmode == BusinessContent.NET_MODE_HTTP_GET){
						f = HPackageFactory.get(bc.getUrl(), bc.getGetValue(), null);
					}else if(netmode == BusinessContent.NET_MODE_HTTP_POST){
						f = HPackageFactory.post(bc.getUrl(), obj, null);
					}else{
						f = null;
					}
				}else{
//					f = HttpProtocolEngine.getInstance().sendPackage(protocolId, obj)
					f = null;
				}		
				
				return f;
	}
}
