package com.scorpio.framework.core;

import android.os.Environment;

import com.scorpio.framework.utils.IOTricks;
import com.scorpio.framework.utils.SystemUtil;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 文件管理
 *
 * 
 */
public class FileManager {

	public static String TAG = FileManager.class.getSimpleName();
	
	public final static String PREFIX = "wo_";
	private static FileManager ins;

	private static String dirBase = "womail/";

	public static FileManager getInstance() {
		if (ins != null) {
			return ins;
		} else {
			ins = new FileManager();
			return ins;
		}
	}

	private FileManager() {
	}

	public void onDestroy() {
	}
	
	/**
	 * 设置全局文件夹根目录。
	 * 
	 * eg： FileManager.getInstance().setBaseDirName("womail");
	 * 
	 * @param baseDirName
	 */
	public void setBaseDirName(String baseDirName){
		dirBase = baseDirName+"/";
	}
	
	/**
	 * 生成指定后缀名的随机文件
	 * 
	 * @param suffix
	 * @param dir
	 * @return
	 */
	private static File getFile(String suffix, File dir) {
		return new File(dir, suffix);
	}

	/**
	 * 生成指定目录
	 * 
	 * @param baseDirKey
	 * @return
	 */
	private static File getDirByType(String baseDirKey) {

		File dir;
		if (!SystemUtil.isSdCardAvailable()) {
			dir = CoreApplication.getInstance().getCacheDir();
		} else {
			File sdir = Environment.getExternalStorageDirectory();
			 dir = new File(sdir+"/"+baseDirKey+"/");
		}
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
	
	public static String getImgDir(){
		return getDirByType(dirBase+"img").getAbsolutePath();
	}

	public static String getDateDir() {
		return getDirByType(dirBase+"cache").getAbsolutePath();
	}

	public static String getZipDir() {
		return getDirByType(dirBase+"zip").getAbsolutePath();
	}

	public static String getLogDir() {
		return getDirByType(dirBase+"log").getAbsolutePath();
	}

	public static String getCrashLogDir() {
		return getDirByType(dirBase+"crash").getAbsolutePath();
	}
	
	public static String getEventDir() {
		return getDirByType(dirBase+"event").getAbsolutePath();
	}

	static private DateFormat formatter = new SimpleDateFormat("_yyyy_MM_dd");
	
//	public static File getZipFilePath(String filename) {
//		String suffix = ".zip";
//		File dir = getDirByType(dirBase+"zip");
//		File file = getFile(filename + suffix, dir);
//		return file;
//	}

//	/**
//	 * 获取日志记录文件地址
//	 * 
//	 * @param logName 文件名
//	 * @return
//	 */
//	static public File getLogFilePath(String logName) {
//		String time = formatter.format(new Date());
//		String suffix = PREFIX + logName + time + "_info.log";
//		File dir = getDirByType(dirBase+"log");
//		File file = getFile(suffix, dir);
//		return file;
//	}
//
	/**
	 * 获取date记录文件地址
	 * 
	 * @param tag 文件名
	 * @return
	 */
	static public File getDateFilePath(String tag) {
		return getFilePathFromType(tag,"date","cache");
	}
	
	/**
	 * 获取attach文件地址
	 * 
	 * @param tag 文件名
	 * @return
	 */
	static public File getAttachFilePath(String tag) {
		return getFilePathFromType(tag,"date","attach");
	}
//	
//	/**
//	 * 获取日志记录文件地址
//	 * 
//	 * @param logName 文件名
//	 * @return
//	 */
//	static public File getEventFilePath(String logName) {
//		String suffix = PREFIX + logName + ".date";
//		File dir = getDirByType(dirBase+"event");
//		File file = getFile(suffix, dir);
//		return file;
//	}
	
	/**
	 * 获取图片文件名
	 * 
	 * @param tag
	 * @return
	 */
	static public File getImgFilePath(String tag){
		return getFilePathFromType(tag,"jpg","img");
	}

	
	/**
	 * 根据指定后缀的获取文件地址
	 * 
	 * eg:FileManager.getFilePathFromType(tag,"jpg","img"); //在sd卡可用下，储存地址为："/sdcard/womail/img/wo_[tag].jpg"
	 * 
	 * 
	 * @param logName 文件名
	 * @param fileSuffix 文件后缀名（不包括点）
	 * @param dirType 文件夹名（在womail子文件夹内）
	 * @return
	 */
	static public File getFilePathFromType(String logName, String fileSuffix, String dirType) {
		String suffix = PREFIX + logName + "."+fileSuffix;
		File dir = getDirByType(dirBase+dirType);
		File file = getFile(suffix, dir);
		return file;
	}
	
	
	/**
	 * 根据url获取文件路径
	 * 
	 * @param url
	 * @return
	 */
	private static String getFilepath(String url) {
		String filename = PREFIX + IOTricks.sanitizeFileName(url);
		return getFile(filename).getAbsolutePath();
	}
	
	/**
	 * 根据文件路径获取文件名
	 * 
	 * @param filename
	 * @return
	 */
	private static File getFile(String filename) {
		File dir = getDirByType(dirBase+"attach");
		return new File(dir, filename);
	}

	/**
	 * 根据url获取文件路径
	 * 
	 * @param url
	 * @return
	 */
	static public File getAttachFilePathFromUrl(String url) {
		if (url == null) {
			return null;
		}
		File file = new File(getFilepath(url));
		return file;
	}
	
	/**
	 * 根据时间戳，随机获取文件名
	 * 
	 * @return
	 */
	static public File getFile(){
		String fileName = System.currentTimeMillis()+"";
		return getFile(fileName);
	}
}
