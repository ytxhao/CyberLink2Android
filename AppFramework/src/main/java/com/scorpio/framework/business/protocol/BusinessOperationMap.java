package com.scorpio.framework.business.protocol;


import com.scorpio.framework.data.dm.DataManagerInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Set;




/**
 * Business处理缓存
 *
 *
 */
public class BusinessOperationMap implements DataManagerInterface<Integer,IBusinessOperation> {
	
	private static BusinessOperationMap _inst;

	public static BusinessOperationMap getInstance() {
		if (_inst != null) {
			return _inst;
		} else {
			_inst = new BusinessOperationMap();
			return _inst;
		}
	}
	
	private HashMap<Integer,IBusinessOperation> operationMap;

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		if(operationMap!=null){
			operationMap.clear();
		}
	}

	@Override
	public IBusinessOperation del(Integer key) {
		// TODO Auto-generated method stub
		return operationMap.remove(key);
	}

	@Override
	public IBusinessOperation get(Integer key) {
		// TODO Auto-generated method stub
		return operationMap.get(key);
	}

	@Override
	public Set<Integer> getKeySet() {
		// TODO Auto-generated method stub
		return operationMap.keySet();
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return operationMap.size();
	}

	@Override
	public void init(Object obj) {
		// TODO Auto-generated method stub
		if(operationMap!=null){
			operationMap.clear();
		}else{
			operationMap = new HashMap<Integer,IBusinessOperation>();
		}
		List<IBusinessOperation> ps = BusinessManager.cps;
		if(ps!=null&&ps.size()>=0){
			for(IBusinessOperation p:ps){
				put(p.getBusinessId(),p);
			}
		}
	}

	@Override
	public IBusinessOperation put(Integer key, IBusinessOperation value) {
		// TODO Auto-generated method stub		
		return operationMap.put(key, value);
	}

}

