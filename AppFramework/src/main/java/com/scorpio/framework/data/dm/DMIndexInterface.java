package com.scorpio.framework.data.dm;


import java.util.Set;

/**
 * 数据管理器索引通用接口
 *
 *
 * @param <K>
 * @param <T>
 */
public interface DMIndexInterface<K,T> {
	
	public T getIndex(K key);	
	
	public T updateIndex(K key, T value);
	
	public T addIndex(K k, T t);
	
	public T delIndex(K key);
	
	public int getIndexSize();
	
	public Set<K> getIndexKeySet();

}

