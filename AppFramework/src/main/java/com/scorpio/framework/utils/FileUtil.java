package com.scorpio.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore.Video.Thumbnails;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;


public class FileUtil {

	private static final String TAG = FileUtil.class.getSimpleName();
	private static File fpath;

	public static boolean IsFileExist(String path) {
		if (TextUtils.isEmpty(path)) {
			return false;
		}
		File file = new File(path);
		if (file != null && !file.exists()) {
			return false;
		}
		return true;
	}

	public static int getIdByReflection(Context c, String className,
                                        String fieldName) {
		try {
			// @SuppressWarnings("rawtypes")
			// Class idClass = Class.forName(c.getPackageName() + ".R$" +
			// className);
			// Field idField = idClass.getField(fieldName);
			// int id = idField.getInt(fieldName);
			//
			// return id;
			Resources res = c.getResources();
			return res.getIdentifier(fieldName, className, c.getPackageName());
		} catch (Exception e) {
			ScoLog.E(TAG,  e.getMessage());
			Toast toast = Toast
					.makeText(c, "resource not found, " + e.getMessage(),
							Toast.LENGTH_SHORT);
			toast.setGravity(17, 0, 150);
			toast.show();
		}
		return 0;
	}

	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		return bd;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (null == drawable) {
			return null;
		}
		BitmapDrawable bd = (BitmapDrawable) drawable;
		return bd.getBitmap();
	}

	/**
	 * 写入软件配置信息
	 * 
	 * @param ctx
	 *            当前上下文
	 * @param fileName
	 *            文件名
	 * @param key
	 *            信息键
	 * @param value
	 *            信息值
	 */
	public static void write(Context ctx, String fileName, String key,
                             String value) {
		SharedPreferences pref = ctx.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor er = pref.edit();
		er.putString(key, value);
		er.commit();
	}

	/**
	 * 读软件配置信息
	 * 
	 * @param ctx
	 *            当前上下文
	 * @param fileName
	 *            文件名
	 * @param key
	 *            信息键
	 * @param def
	 *            信息值
	 */
	public static String read(Context ctx, String fileName, String key,
                              String def) {
		SharedPreferences pref = ctx.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return pref.getString(key, def);
	}

	/**
	 * 获取音频文件的时长
	 * 
	 * @param audioRecordedFile
	 *            音频文件
	 * @return
	 */
	public static int getDuration(File audioRecordedFile) {
		int nPlayerDuration = 0;
		MediaPlayer mp = new MediaPlayer();
		if (!audioRecordedFile.exists()) {
			// mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			// try {
			// AssetFileDescriptor afd =
			// context.getAssets().openFd(audioRecordedFile.getAbsolutePath());
			// mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
			// afd.getLength());
			// mp.prepareAsync();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
		} else {
			try {
				mp.setDataSource(audioRecordedFile.getAbsolutePath());
				mp.prepare();

				nPlayerDuration = mp.getDuration();
			} catch (Exception e) {
			} finally {
				mp.release();
				mp = null;
			}
		}

		return nPlayerDuration;
	}

	/**
	 * 判断音频是否能正常播放
	 * 
	 * @param voicPath
	 *            音频地址
	 * @return true能正常播放;否则不可正常播放
	 */
	public static boolean isPlayable(String voicPath) {
		// if (StringUtil.isBlank(voicPath)) {
		// return false;
		// }
		File audioRecordedFile = new File(voicPath);
		// if (!audioRecordedFile.exists()) {
		// return false;
		// }
		MediaPlayer mp = new MediaPlayer();
		try {
			mp.setDataSource(audioRecordedFile.getAbsolutePath());
			mp.prepare();
		} catch (Exception e) {
			ScoLog.E(TAG,  e.getMessage(), e);
			return false;
		} finally {
			mp.release();
			mp = null;
		}
		return true;
	}

	public static String getFileSize(long size) {
		double result = size / 1024;
		DecimalFormat decimalFormat = new DecimalFormat("#.0");
		if (result < 1) {
			return size + "KB";
		}
		return decimalFormat.format(result) + "M";
	}

	public static int getAssertDuration(File audioRecordedFile, Context context) {
		int nPlayerDuration = 0;
		MediaPlayer mp = new MediaPlayer();
		mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			AssetFileDescriptor afd = context.getAssets().openFd(
					audioRecordedFile.getPath());
			mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			mp.prepare();
			nPlayerDuration = mp.getDuration();
		} catch (IOException e) {
		} finally {
			mp.release();
			mp = null;
		}

		return nPlayerDuration;
	}

	/**
	 * 获取视频文件的宽高(此函数为测试，未实现)
	 * 
	 * @param videoRecordedFile
	 *            视频文件
	 * @return key为width;value为height
	 */
	public static int[] getDimension(File videoRecordedFile) {
		// Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		int[] result = new int[] { 0, 0 };
		if (!videoRecordedFile.exists()) {
			return result;
		}
		MediaPlayer mp = new MediaPlayer();
		try {
			mp.setDataSource(videoRecordedFile.getAbsolutePath());
			mp.prepare();
			result[0] = mp.getVideoWidth();
			result[1] = mp.getVideoHeight();
			// result.put(mp.getVideoWidth(), mp.getVideoHeight());
		} catch (Exception e) {
		} finally {
			mp.release();
			mp = null;
		}
		// result.put(100, 100);
		return result;
	}

	public static String createThumbnai(String videoPath) {
		if (TextUtils.isEmpty(videoPath))
			return null;
		File file = new File(videoPath);
		if (!file.exists() && !file.isFile()) {
			return null;
		}
		File videoThumbnailFile = null;
		try {
			// 将视频缩略图输出到本地
			videoThumbnailFile = File.createTempFile("video_thumbnail", ".jpg",
					getMediaPath());
			ThumbnailUtils
					.createVideoThumbnail(videoPath, Thumbnails.MINI_KIND)
					.compress(CompressFormat.JPEG, 80,
							new FileOutputStream(videoThumbnailFile));
		} catch (Exception e) {

		}
		String path = null;
		if (videoThumbnailFile != null) {
			path = videoThumbnailFile.getAbsolutePath();
		}
		return path;
	}

	public static File getMediaPath() {
		if (fpath == null) {
			synchronized (FileUtil.class) {
				fpath = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/shuangshuang/audios/");
			}
		}
		return fpath;
	}
}
