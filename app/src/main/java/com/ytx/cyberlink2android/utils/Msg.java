package com.ytx.cyberlink2android.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.ytx.cyberlink2android.scorpio.business.cfg.AppConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 简单的对话框提示类
 * 
 *
 * 
 */

public class Msg {
	private static final String TAG = Msg.class.getSimpleName();

	private static String title = "提示";

	public static int LENGTH_LONG = Toast.LENGTH_LONG;
	public static int LENGTH_SHORT = Toast.LENGTH_SHORT;

	static private Context ctx;

	public static void init(Context mctx) {
		setContext(mctx);
	}

	private static void setContext(Context mctx) {
		if (ctx == null) {
			ctx = mctx;
		}
	}

	private static Msg instance;

	public static Msg getInstance() {
		if (instance == null) {
			instance = new Msg();
		}

		return instance;
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

;
	private static WDialog wd;

	/**
	 * 加载对话框
	 *
	 * 
	 */
	private class PDialog extends AlertDialog {
		private int layout;

		// private Context context;

		public PDialog(Context context) {
			super(context);
			// this.context = context;
		}

		public PDialog(Context context, int theme, int layout) {
			super(context, theme);
			this.layout = layout;
			// this.context = context;
		}

		public PDialog(Context context, int theme) {
			super(context, theme);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(layout);// R.layout.progress_bar
		}

	}

	/**
	 * 加载对话框
	 *
	 * 
	 */
	private class WDialog extends Dialog {
		private int layout;
		private Context context;

		public WDialog(Context context) {
			super(context);
			this.context = context;
		}

		public WDialog(Context context, int theme, int layout) {
			super(context, theme);
			this.layout = layout;
			this.context = context;
		}

		public WDialog(Context context, int theme) {
			super(context, theme);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.setContentView(inflater.inflate(layout, null),
					new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT));
		}

	}





	private void endWShow() {
		if (wd != null) {
			wd.dismiss();
			wd = null;
		}
	}

	/**
	 * 输入回调
	 * 
	 */
	public interface OnEditListener {
		public void onEditFinish(String str);
	}

	/**
	 * 对话框初始化调用
	 *
	 * 
	 */
	public interface OnDialogInit {
		public void onDialogInit(WDialog wd);
	}

	/**
	 * 提醒对话框初始化调用
	 *
	 * 
	 */
	public interface OnAlertDialogInit {
		public void onAlertDialogInit(PDialog pd);
	}


	
	/**
	 * 是否显示软键盘
	 * 
	 * @param isShow
	 * @param view
	 */
	static public void changeSoftInput(final boolean isShow,final View view){
		(new Handler()).postDelayed(new Runnable() {
			public void run() {
				InputMethodManager imm = (InputMethodManager) view
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				if (isShow) {
					imm.showSoftInput(view,0);
				} else {
					Context context = view.getContext();
					imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
							.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}, 100);
	}
	


	/**
	 * 弹出对话框
	 * 
	 * @param c
	 * @param msg
	 */
	public static void w(Context c, String msg) {
		w(c, msg, null, null);
		return;
	}
	
	/**
	 * 弹出对话框
	 * 
	 * @param c
	 * @param resId
	 */
	public static void w(Context c, int resId) {
		w(c,c.getString(resId));
	}

	/**
	 * 弹出对话框
	 * 
	 * @param c
	 * @param msg
	 * @param title
	 */
	public static void w(Context c, String msg, String title) {
		w(c, msg, title, null);
		return;
	}

	/**
	 * 弹出对话框
	 * 
	 * @param c
	 * @param msg
	 * @param title
	 * @param onclick yesOnclick
	 */
	public static void w(Context c, String msg, String title,
                         DialogInterface.OnClickListener onclick) {
		w(c, msg, title, onclick, null, "确定", null, false);
		return;
	}

	/**
	 * 弹出对话框
	 * 
	 * @param c
	 * @param msg
	 * @param title
	 * @param onclick
	 */
	public static void w(Context c, String msg, String title, String keyName,
                         DialogInterface.OnClickListener onclick) {
		w(c, msg, title, onclick, null, keyName, null, false);
		return;
	}

	/**
	 * 弹出对话框并退出
	 * 
	 * @param c
	 * @param msg
	 */
	public static void w_exit(final Context c, String msg) {
		w(c, msg, title, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (c instanceof Activity) {
					((Activity) c).finish();
				} else if (c instanceof Service) {
					((Service) c).stopSelf();
				}
			}
		});
		return;
	}

	// public static void p() {
	//
	// }

	/**
	 * 弹出对话框
	 * 
	 * @param c
	 * @param msg
	 * @param title
	 * @param yesOnclick
	 */
	public static void w(Context c, String msg, String title,
                         DialogInterface.OnClickListener yesOnclick,
                         DialogInterface.OnClickListener noOnclick) {
		w(c, msg, title, yesOnclick, noOnclick, "确定", "取消", false);
		return;
	}

	/**
	 * 弹出对话框
	 * 
	 * @param c
	 * @param msg
	 * @param title
	 * @param yesOnclick
	 */
	public static void w(Context c, String msg, String title,
                         DialogInterface.OnClickListener yesOnclick,
                         DialogInterface.OnClickListener noOnclick, boolean isCancelable) {
		w(c, msg, title, yesOnclick, noOnclick, "确定", "取消", isCancelable);
		return;
	}

	/**
	 * 弹出对话框
	 * 
	 * @param c
	 * @param msg
	 * @param title
	 * @param yesOnclick
	 */
	public static void w(Context c, String msg, String title,
                         DialogInterface.OnClickListener yesOnclick,
                         DialogInterface.OnClickListener noOnclick, String yesName,
                         String noName, boolean isCancelable) {
		w(c, msg, title, yesOnclick, noOnclick, yesName, noName, isCancelable,
				false);
	}

	public static void w() {

	};

	/**
	 * 弹出对话框
	 * 
	 * @param c
	 * @param msg
	 * @param title
	 * @param yesOnclick
	 */
	public static void w(Context c, String msg, String title,
                         DialogInterface.OnClickListener yesOnclick,
                         DialogInterface.OnClickListener noOnclick, String yesName,
                         String noName, boolean isCancelable, boolean isSystemMsg) {
		Builder alertDialog = new AlertDialog.Builder(c);
		if (!TextUtils.isEmpty(title)) {
			alertDialog.setTitle(title);
		}

		alertDialog.setMessage(msg);
		if (yesOnclick != null && (!TextUtils.isEmpty(yesName))) {
			alertDialog.setPositiveButton(yesName, yesOnclick);
		} else {
			alertDialog.setPositiveButton("确定", yesOnclick);
		}
		if (!TextUtils.isEmpty(noName)) {
			alertDialog.setNegativeButton(noName, noOnclick);
		}
		AlertDialog alertDialogWin = alertDialog.create();
		if (isSystemMsg) {
			alertDialogWin.getWindow().setType(
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		}
		alertDialogWin.setCancelable(isCancelable);
		alertDialogWin.show(); // 显示对话框
		return;
	}

	private static AlertDialog alertDialogWin;
	/**
	 * 设置全局变量AlertDialog使dialog只能显示一次
	 * @param c
	 * @param msg
	 * @param title
	 * @param yesOnclick
	 * @param noOnclick
	 * @param yesName
	 * @param noName
	 * @param isCancelable
	 * @param isSystemMsg
	 */
	public static void showOneTime(Context c, String msg, String title,
                                   DialogInterface.OnClickListener yesOnclick,
                                   DialogInterface.OnClickListener noOnclick, String yesName,
                                   String noName, boolean isCancelable, boolean isSystemMsg) {
		if(alertDialogWin != null){
			if (alertDialogWin.isShowing()) {
				Log.d("xxx","not show");
				alertDialogWin.dismiss();
			}
			alertDialogWin = null;
		}
		Builder alertDialog = new AlertDialog.Builder(c);
		if (!TextUtils.isEmpty(title)) {
			alertDialog.setTitle(title);
		}
		alertDialog.setMessage(msg);
		if (yesOnclick != null && (!TextUtils.isEmpty(yesName))) {
			alertDialog.setPositiveButton(yesName, yesOnclick);
		} else {
			alertDialog.setPositiveButton("确定", yesOnclick);
		}
		if (!TextUtils.isEmpty(noName)) {
			alertDialog.setNegativeButton(noName, noOnclick);
		}
		alertDialogWin = alertDialog.create();
		if (isSystemMsg) {
			alertDialogWin.getWindow().setType(
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		}
		alertDialogWin.setCancelable(isCancelable);
		alertDialogWin.show();
	}

	/**
	 * 弹出对话框
	 *
	 * @param c
	 * @param msg
	 * @param title
	 * @param yesOnclick
	 */
	public static void ws(Context c, String msg, String title, String yesName,
                          String noName, DialogInterface.OnClickListener yesOnclick,
                          DialogInterface.OnClickListener noOnclick, boolean isCancelable) {
		w(c, msg, title, yesOnclick, noOnclick, yesName, noName, isCancelable,
				true);
		return;
	}

	/**
	 * 后台弹窗
	 *
	 * @param c
	 * @param msg
	 * @param title
	 * @param yesOnclick
	 * @param noOnclick
	 */
	public static void ws(Context c, String msg, String title,
                          DialogInterface.OnClickListener yesOnclick,
                          DialogInterface.OnClickListener noOnclick) {
		ws(c, msg, title, "确定", "取消", yesOnclick, noOnclick, false);
	}

	/**
	 * 弹出对话框
	 *
	 * @param c
	 * @param msg
	 * @param title
	 * @param yesOnclick
	 */
	public static void wsAlarmForText(Context c, String msg, String title,
                                      DialogInterface.OnClickListener yesOnclick,
                                      DialogInterface.OnClickListener noOnclick) {
		ws(c, msg, title, "去回复Ta", "消除", yesOnclick, noOnclick, false);
		return;
	}

	public static void wsAlarmForPet(Context c, String msg, String title,
                                     DialogInterface.OnClickListener yesOnclick,
                                     DialogInterface.OnClickListener noOnclick) {
		ws(c, msg, title, "去听听", "关闭", yesOnclick, noOnclick, false);
		return;
	}

	/**
	 * 弹出对话框
	 *
	 * @param c
	 * @param msg
	 * @param title
	 * @param yesOnclick
	 */
	public static void wsUpdate(Context c, String msg, String title,
                                DialogInterface.OnClickListener yesOnclick,
                                final DialogInterface.OnClickListener noOnclick) {
		final AlertDialog alertDialog = new AlertDialog.Builder(c)
				.setTitle(title).setMessage(msg)
				.setPositiveButton("立刻升级", yesOnclick)
				.setNegativeButton("稍后再说", noOnclick).create(); // 创建对话框
		alertDialog.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		alertDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				if (arg1 == KeyEvent.KEYCODE_BACK) {
					noOnclick.onClick(null, 1);
					if (alertDialog.isShowing())
						alertDialog.dismiss();
				}
				return false;
			}
		});
		alertDialog.show(); // 显示对话框
		return;
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 弹出吐司（t）
	 * 
	 * @param msg 土司消息String内容
	 */
	public static void t(String msg) {
		try {
			if (ctx == null) {
				Log.e(TAG, "the context is null");
				return;
			}
			if (checkUI(ctx)) {
				Log.e(TAG, "the context is outside");
				return;
			}

			Toast.makeText(ctx, msg, Msg.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 弹出吐司（t）
	 * 
	 * @param msgId 资源ID值
	 */
	public static void t(int msgId) {
		try {
			if (ctx == null) {
				Log.e(TAG, "the context is null");
				return;
			}
			if (checkUI(ctx)) {
				Log.e(TAG, "the context is outside");
				return;
			}
			;
			String msg = ctx.getString(msgId);
			if(TextUtils.isEmpty(msg)){
				Log.e(TAG, "the resId is null:"+msgId);
				return;
			}
			Toast.makeText(ctx, msg, Msg.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检测当前页面是否跳出应用
	 * 
	 * @param currContext
	 * @return
	 */
	private static boolean checkUI(Context currContext) {
		boolean exitThis = false;

		try {
			@SuppressWarnings("static-access")
            ActivityManager am = (ActivityManager) currContext

			.getSystemService(currContext.ACTIVITY_SERVICE);

			ComponentName aName = am.getRunningTasks(1).get(0).topActivity;

			if (aName != null) {
				exitThis = !aName.getPackageName().equals(ctx.getPackageName());
				Log.d("system", "the am package:" + aName.getPackageName());
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return exitThis;
	}

	/**
	 * 弹出吐司
	 * 
	 * @param c
	 * @param msg
	 */
	public static void t(Context c, String msg) {
		Toast.makeText(c, msg, Msg.LENGTH_SHORT).show();
	}

	/**
	 * 弹出吐司(自定义布局)
	 * 
	 * @param c
	 * @param resid
	 */
	public static void t(Context c, int resid) {
		Toast.makeText(c, resid, Msg.LENGTH_SHORT).show();
	}

	/**
	 * 弹出吐司(停留长时间)
	 * 
	 * @param c
	 * @param msg
	 */
	public static void tl(Context c, String msg) {
		Toast.makeText(c, msg, Msg.LENGTH_LONG).show();
	}

	/**
	 * 弹出吐司(设置停留时间)
	 * 
	 * @param c
	 * @param msg
	 */
	public static void tt(Context c, String msg, int time) {
		Toast.makeText(c, msg, time).show();
	}

	/**
	 * 退出标识
	 */
	static boolean exitThis = false;

	/**
	 * 吐司退出提示
	 * 
	 * @param c
	 * @param msg
	 * @param isWaitClose
	 * @param handler
	 */
	public static void t_exit(final Context c, String msg, boolean isWaitClose,
                              Handler handler) {

		// 退出本界面的提示

		if (isWaitClose && !exitThis) {
			Msg.t(c, msg);
			exitThis = true;
			handler.postDelayed(new Runnable() {
				public void run() {
					exitThis = false;
				}
			}, 3000);
		} else {
			if (c instanceof Activity) {
				((Activity) c).finish();
			} else if (c instanceof Service) {
				((Service) c).stopSelf();
			}
			exitThis = false;
		}
	}

	/**
	 * 聊天界面吐司退出提示
	 * 
	 * @param c
	 * @param msgEv
	 * @param handler
	 */
	public static void t_msg_exit(final Context c, EditText msgEv,
                                  Handler handler) {
		t_exit(c, "您正在输入信息，请再次点击返回退出，" + "您正在编写的消息将不会保存~",
				(msgEv.getText().toString().length() > 0 || msgEv
						.getCompoundDrawables()[1] != null), handler);
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 打印日志
	 * 
	 * @param msg
	 */
	public static void l(String msg) {
		l("msg", msg);
	}
	
	/**
	 * 调试日志
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void ld(String tag, String msg) {
		l(tag, msg);
	}
	
	/**
	 * 调试日志
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void ll(String tag, String msg) {
		l(tag, msg);
	}

	/**
	 * 自定义日志
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void l(String tag, String msg) {
		Log.d(tag, msg);
	}
	
	/**
	 * 错误日志
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void le(String tag, String msg) {
		Log.e(tag, msg);
	}
	
	/**
	 * 信息日志
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void li(String tag, String msg) {
		Log.i(tag, msg);
	}
	
	

	

	// /**
	// * 打印系统日志
	// *
	// * @param string
	// */
	// public static void s(String string) {
	// CoreService.writeSystemInfoAsyn(string);
	// }
	
	// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private static final boolean isUseFileLog = AppConfig.IS_USE_FILE_LOG;
	/**
	 * 记录日志文件
	 * 
	 */
	public static void f(String msg){
		if(isUseFileLog){
			saveInfo2File("MyInfo","info",msg,true);
		}
		Msg.l(msg);
	}
	
	/**
	 * 记录调试日志
	 * 
	 * @param debugInfo
	 */
	public static void fDebugCrash(String debugInfo){
		if(isUseFileLog){
			saveInfo2File("MyDebug","debug",debugInfo,true);
		}
	}
	
	/**
	 * 实时时间生成
	 * 
	 */
	@SuppressLint("SimpleDateFormat")
	private static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	
	/**
	 * 按天时间生成
	 * 
	 */
	@SuppressLint("SimpleDateFormat")
	private static DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 记录到指定日志文件
	 * 
	 * @SuppressLint("SdCardPath")
	 * @param dirName 日志目录
	 * @param tag	日志类别
	 * @param info 日志内容
	 * @param isDay 是否按天生成日志，否则每条生成独立日志
	 * @return
	 */
	static public String saveInfo2File(String dirName, String tag, String info, boolean isDay){
		if(!isUseFileLog){
			return null;
		}
		try {
			String time = isDay?formatter2.format(new Date()):formatter.format(new Date());
			String fileName = "my-"+tag+"-" + time  + ".log";
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String path = "/sdcard/"+dirName+"/";
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path + fileName,true);
				info = info+"\r\n";
				fos.write(info.toString().getBytes());
				fos.close();
			}
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "saveInfo2File:an error occured while writing file...", e);
		}
		return null;
	}

}
