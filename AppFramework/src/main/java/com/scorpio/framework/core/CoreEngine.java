package com.scorpio.framework.core;

import android.os.Handler;
import android.os.Looper;

import com.scorpio.framework.business.protocol.BusinessCenter;
import com.scorpio.framework.business.protocol.BusinessManager;
import com.scorpio.framework.business.protocol.HttpCenter;
import com.scorpio.framework.config.CoreSchema;
import com.scorpio.framework.net.ConnectManager;
import com.scorpio.framework.utils.CrashHandler;
import com.scorpio.framework.utils.ScoLog;


/**
 * 后台核心引擎
 *
 *
 */
public class CoreEngine {
	private static final String TAG = CoreEngine.class.getSimpleName();

	//private static CoreEngine _inst;

	private static class SingletonHolder {
		private static final CoreEngine INSTANCE = new CoreEngine();
	}

	/**
	 * 获取核心引擎实例
	 * 
	 * @return
	 */
	public static CoreEngine getInstance() {

		if(SingletonHolder.INSTANCE != null){
			if(!isEngineRunning()){
				SingletonHolder.INSTANCE.initEngine();
			}

		}

		return SingletonHolder.INSTANCE;
//		if (_inst != null) {
//			if(!isEngineRunning()){
//				_inst.initEngine();
//			}
//			return _inst;
//		} else {
//			_inst = new CoreEngine();
//			_inst.initEngine();
//			return _inst;
//		}
	}
	LooperThread mWorkThread;
	/**
	 * 引擎初始化
	 * 
	 */
	private void initEngine() {
		// TODO Auto-generated method stub
		if(mWorkThread!=null&&mWorkThread.isAlive()){
			return;
		}
		mWorkThread = new LooperThread();
		mWorkThread.setName("coreEngine(Scorpio)");
		mWorkThread.start();
		ScoLog.D(TAG, "CoreEngine is init ................................................");
	}
	
	/**
	 * 核心引擎事件处理队列
	 *
	 *
	 */
	class LooperThread extends Thread {
		public Handler mHandler;

		public void run() {

			try {
				Looper.prepare();
				ScoLog.D(TAG, "CoreEngine is running ...................................");
				setHttpCenter(new HttpCenter());
				setBusinessCenter(new BusinessCenter());
				setUICenter(new UICenterDef());
				setEngineRunning(true);
				CrashHandler.getInstance().init(CoreApplication.getInstance());
				Looper.loop();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
			} finally {
				setEngineRunning(false);
			}
		}
	}

	private CoreEngine() {
		init();
		return;
	}
	
	/**
	 * 是否开启网络监听
	 * 
	 */
	private static boolean IS_USE_CONNECTIVITY_CHANGE_ACTION = CoreSchema.IS_USE_CONNECTIVITY_CHANGE_ACTION;
	
	
	
	/**
	 * 默认初始化
	 * 
	 */
	private void init() {
		if(IS_USE_CONNECTIVITY_CHANGE_ACTION){
			ConnectManager.getInstance().registerDateTransReceiver(CoreApplication.getInstance());
		}
	}
	
	private static boolean isEngineRunning = false;
	
	/**
	 * 引擎是否已经开启并允许
	 * 
	 * @return
	 */
	public static synchronized boolean isEngineRunning() {
		return isEngineRunning;
	}

	public static synchronized void setEngineRunning(boolean isEngineRunning) {
		CoreEngine.isEngineRunning = isEngineRunning;
	}
	
	/**
	 * 获取通知中心服务
	 * 
	 * @return
	 */
	public UICenterDef getUiCenter() {
		if(!isEngineRunning()){
			initEngine();
		}
		return uiCenter;
	}
	
	public void setUICenter(UICenterDef ui){
		uiCenter = ui;
	}
	
	public BusinessCenter getBusinessCenter() {
		if(!isEngineRunning()){
			initEngine();
		}
		return businessCenter;
	}

	public void setBusinessCenter(BusinessCenter businessCenter) {
		this.businessCenter = businessCenter;
	}
	
	public HttpCenter getHttpCenter() {
		if(!isEngineRunning()){
			initEngine();
		}
		return httpCenter;
	}

	public void setHttpCenter(HttpCenter httpCenter) {
		this.httpCenter = httpCenter;
	}
	
	/**
	 * 返回业务注册中心
	 * 
	 * @return
	 */
	public BusinessManager getBusinessManager(){
		return BusinessManager.getInstance();
	}
	
	private HttpCenter httpCenter ;
	
	private UICenterDef uiCenter = new UICenterDef();
	
	private BusinessCenter businessCenter = new BusinessCenter();
	
	public static enum MobileNetType {
		MOBILE_2G, MOBILE_3G, MOBILE_4G, WIFI, NO_NETWORK
	}
	/**
	 *  获取网络状态
	 * @return
	 */
	public MobileNetType getMobileNetworkTypeFromMNetStatus(){
		return ConnectManager.getMobileNetworkTypeFromMNetStatus(CoreApplication.getInstance());
	}

}
