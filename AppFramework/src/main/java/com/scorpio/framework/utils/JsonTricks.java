package com.scorpio.framework.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * json util
 *
 * 
 */
public class JsonTricks {
	public static JSONObject getJSONObject(JSONObject json, String... path) {
		return getJSONObject(json, path, 0, path.length - 1);
	}

	protected static JSONObject getJSONObject(JSONObject json, String[] path,
                                              int start, int length) {
		if (start >= length)
			return json;

		while (start < length) {
			if (json == null)
				break;
			else
				json = json.optJSONObject(path[start]);
			start++;
		}
		return json;
	}

	public static String getString(JSONObject json, String... path) {
		json = getJSONObject(json, path, 0, path.length - 1);
		if (json != null) {
			return json.optString(path[path.length - 1]);
		}
		return null;
	}

	/**
	 * 新加方法转换json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String getString(Object object) {
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		String jsonString = gson.toJson(object);
		return jsonString;
	}

	@SuppressWarnings("hiding")
	public static <Object> Object getObject(String jsonString,
			Class<Object> classOfT) {
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		Object object = gson.fromJson(jsonString, classOfT);
		return object;
	}
	
	/**
	 * java对象转换为json字符串
	 * 
	 * @param bean
	 * @return
	 */
	public static String getSimpleString(Object bean) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(bean);
	}

	/**
	 * json字符串转换为java对象
	 * 
	 * @param jsonString
	 * @param classOfT
	 * @return
	 */
	@SuppressWarnings("hiding")
	public static <Object> Object getSimpleObject(String jsonString,
			Class<Object> classOfT) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		Object object = gson.fromJson(jsonString, classOfT);
		return object;
	}

	@SuppressWarnings("hiding")
	public static <Object> Object getObjectFromJsonObject(JSONObject jsonObj,
			Class<Object> classOfT) {
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		Object object = gson.fromJson(jsonObj.toString(), classOfT);
		return object;
	}
	
	

	// @Expose@SerializedName("wowowo")

	public static void loadJsonObj(Object obj, File file) {
		String value = getString(obj);
		IOTricks.copyTextToFile(value, file, true);
	}

	@SuppressWarnings("hiding")
	public static <Object> Object getJsonObject(File file,
			Class<Object> classOfT) throws FileNotFoundException {
		String value = IOTricks.getTextFromFile(file);
		return getObject(value, classOfT);
	}

}
