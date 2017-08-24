package com.scorpio.framework.data.dm;


import java.util.Set;

/**
 * 数据管理器通用接口
 *
 *
 * @param <K>
 * @param <T>
 */
public interface DataManagerInterface<K,T> {
	
	public T get(K key);
	
	public void init(Object obj);
	
	public void clear();
	
	public T put(K key, T value);
	
	public T del(K key);
	
	public int getSize();
	
	public Set<K> getKeySet();

}

