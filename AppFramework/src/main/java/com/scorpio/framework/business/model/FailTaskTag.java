package com.scorpio.framework.business.model;


import java.io.Serializable;

/**
 * 该实例用于返回preHandler后中断后台该业务中转.
 *
 */
public class FailTaskTag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7257699956046354456L;
	private static FailTaskTag _inst;

	public static FailTaskTag getInstance() {
		if (_inst != null) {
			return _inst;
		} else {
			_inst = new FailTaskTag();
			return _inst;
		}
	}
}

