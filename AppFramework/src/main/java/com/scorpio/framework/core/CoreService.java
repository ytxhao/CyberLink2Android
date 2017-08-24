package com.scorpio.framework.core;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;

import com.scorpio.framework.utils.ScoLog;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 核心后台服务
 *
 */
public class CoreService extends Service {
	
	static private boolean isStarted = false;
	

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		CoreEngine.getInstance();
		isStarted = true;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		super.onRebind(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@SuppressLint("NewApi")
	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
		super.onTrimMemory(level);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

	/**
	 * 是否已启动后台
	 */
	public static boolean isStarted() {
		return isStarted;
	}



	public static void exec(final Runnable command) {
		Runnable runnable = new Runnable() {
			public void run() {
				try {
					command.run();
				} catch (Throwable t) {
					ScoLog.e("core", "command", t);
				}
			}
		};
		sExecutor.execute(runnable);
	}

//	public static void execComing(final Runnable command, boolean isIn) {
//		Runnable runnable = new Runnable() {
//			public void run() {
//				try {
//					command.run();
//				} catch (Throwable t) {
//					Log.e("core", "command", t);
//				}
//			}
//		};
//		if (isIn) {
//			sIncomingExecutor.execute(runnable);
//		} else {
//			sOutcomingExecutor.execute(runnable);
//		}
//
//	}

	public static void CExec(Runnable command) {
		// if(connectHandler!=null){
		// connectHandler.post(command);
		// }else{
		// Log.e("core", "the looperConnect handler is null!!!!!!!!!!!!!!!!!");
		// }
		exec(command);
	}


	private static final int S_CORE_POOL_SIZE = 2;
	// private static final int CORE_POOL_SIZE = 2;
	private static final int MAXIMUM_POOL_SIZE = 3;
	private static final int KEEP_ALIVE = 1;

	private static final BlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue<Runnable>(
			128);
	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		private final AtomicInteger mCount = new AtomicInteger(1);

		public Thread newThread(final Runnable r) {
			return new Thread(r, "CoreTask #" + mCount.getAndIncrement());
		}
	};

	private static final ThreadPoolExecutor sExecutor = new ThreadPoolExecutor(
			S_CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
			sWorkQueue, sThreadFactory);

}
