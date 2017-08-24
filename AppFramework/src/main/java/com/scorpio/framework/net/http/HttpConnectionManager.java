package com.scorpio.framework.net.http;

import com.scorpio.framework.data.dm.DataManagerInterface;
import com.scorpio.framework.net.http.interfaces.IHttpOperation;
import com.scorpio.framework.net.http.interfaces.IHttpProtocol;
import com.scorpio.framework.net.http.protocol.HttpWorker;
import com.scorpio.framework.utils.ScoLog;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 网络连接并发及事务管理
 *
 *
 */
public class HttpConnectionManager {

	
	private static final String TAG = HttpConnectionManager.class.getSimpleName();


	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		private final AtomicInteger mCount = new AtomicInteger(1);

		public Thread newThread(final Runnable r) {
			return new Thread(r, "coreHttpTask #" + mCount.getAndIncrement());
		}
	};

	private static final BlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue<Runnable>(
			128);

	private static final ThreadPoolExecutor sExecutor = new ThreadPoolExecutor(
			HttpConfigureSchema.CORE_POOL_SIZE, HttpConfigureSchema.MAXIMUM_POOL_SIZE, HttpConfigureSchema.KEEP_ALIVE, TimeUnit.SECONDS,
			sWorkQueue, sThreadFactory);
//	private static final ThreadPoolExecutor fExecutor = new ThreadPoolExecutor(
//			1, 1, HttpConfigureSchema.KEEP_ALIVE, TimeUnit.SECONDS,
//			sWorkQueue, sThreadFactory);
	public void execute(final Runnable command) {
		Runnable runnable = new Runnable() {
			public void run() {
				try {
					android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_LOWEST);
					command.run();
				} catch (Throwable t) {
					ScoLog.E(TAG,"coreHttpTask command", t);

				}
			}
		};
		sExecutor.execute(runnable);
	}
//	public void executeMsgFileDown(final Runnable command) {
//		Runnable runnable = new Runnable() {
//			public void run() {
//				try {
//					command.run();
//				} catch (Throwable t) {
//					Log.e("coreDownloadTask", "command", t);
//				}
//			}
//		};
//		sSeqExecutor.execute(runnable);
//	}
//	private static final SerialExecutor sSeqExecutor = new SerialExecutor(fExecutor,"seqExcutor");

	public static final Object HDM_RES = null;

	public static final Object HDM_OPT = null;
	private static HttpConnectionManager _inst;

	public static HashMap<String,DataManagerInterface> hdms;

	public static HttpConnectionManager getInstance() {
		if (_inst != null) {
			return _inst;
		} else {
			_inst = new HttpConnectionManager();
			return _inst;
		}
	}
	public void onDestroy(){
//		cHttpClient.getInstance().shutdown();
	}
	
	
	

	public long getNowTaskNumber(){
		return sExecutor.getTaskCount();
	}

	public void setThreadPoolParam(int core_pool_size,int maximum_pool_size,int keep_alive,TimeUnit time_unit){
	
		sExecutor.setCorePoolSize(core_pool_size);
		sExecutor.setMaximumPoolSize(maximum_pool_size);
		sExecutor.setKeepAliveTime(keep_alive, time_unit);

	}

	/**
	 * 带索引的消息队列，key为expectedReplyId，value为EzHttpRequest
	 */
	private Map<Integer, HttpRequest> messages = Collections
			.synchronizedMap(new LinkedHashMap<Integer, HttpRequest>());

//	public Map<Integer, EzHttpRequest> getMessages() {
//		return messages;
//	}

	/**
	 * 目前正在等待应答消息的请求数量
	 */
	public long numberOfRequestsWaitingForReply() {
		return messages.size();
	}

	public void registerConnection(int id,HttpRequest h)
	{
		this.messages.put(id, h);
	}

	public HttpRequest unRegisterConnection(int id)
	{
		return messages.remove(id);
	}

	public HttpRequest findConnectionBySeq(int id){
		return messages.get(id);
	}

	private int seq;
	private final Object seqLock = new Object();

	/**
	 * 供外部构造request用
	 *
	 * @return
	 */
	public int getNextSeq() {
		synchronized (seqLock) {
			return seq++;
		}
	}
	public IHttpProtocol getHttpProtocolById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	public IHttpOperation getHttpOperation() {
		// TODO Auto-generated method stub
		return HttpWorker.getInstance();
	}

	
}