package com.github.jlf1997.spring_boot_sdk.util;

public class SpringDataJpaUtils {
	
	/**
	 * 判断对象 某些字段是否有数据库中存在，高并发情形下不能通过此方法判断，建议使用联合唯一索引限制
	 * @return
	 */
	public static <T> boolean isDuplicate(T t) {
		return false;
		
	}
	
	
	public static <T> boolean isDuplicate(T t,String...strings) {
		
		
		
		return false;
		
	}
}
