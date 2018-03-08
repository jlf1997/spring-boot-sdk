package com.github.jlf1997.spring_boot_sdk.oper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * spring data jpa 操作辅助类
 * @author yangxiang
 *
 * @param <T>
 */
public class SpringDateJpaOper<T> {
	private Root<T> root;
	private CriteriaQuery<?> query;
	private CriteriaBuilder cb;
	
	
	/**
	 * 构造函数
	 * @param root
	 * @param query
	 * @param cb
	 */
	public SpringDateJpaOper(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb){
		this.cb = cb;
		this.query = query;
		this.root = root;
	}
	
	/**
	 * 等于
	 * @param attributeName 对应实体类中字段
	 * @param value 比较值
	 * @return
	 */
	public  Predicate eq(String attributeName,Object value) {
		return cb.equal(root.get(attributeName),  value);
		
	}

	/**
	 * 模糊查询
	 * @param attributeName 对应实体类中字段
	 * @param value 比较值
	 * @return
	 */
	public Predicate like(String attributeName, Object value) {
		if(value instanceof String) {
			String str = (String)value;
			return cb.like(root.get(attributeName)
					,cb.literal("%"+str+"%"));
		}
		return null;
	}

	/**
	 * 大于等于
	 * @param attributeName 对应实体类中字段
	 * @param value 比较值
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Predicate ge(String attributeName, Object value) {
		if(value instanceof Comparable) {
			Comparable comparable = (Comparable) value;
			return cb.greaterThanOrEqualTo(root.get(attributeName), comparable);
		}		
		return null;
	}

	/**
	 * 小于等于
	 * @param attributeName 对应实体类中字段
	 * @param value 比较值
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Predicate le(String attributeName, Object value) {
		if(value instanceof Comparable) {
			Comparable comparable = (Comparable) value;
			return cb.lessThanOrEqualTo(root.get(attributeName), comparable);
		}	
		return null;
	}

	/**
	 * 大于
	 * @param attributeName 对应实体类中字段
	 * @param value 比较值
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Predicate gt(String attributeName, Object value) {
		if(value instanceof Comparable) {
			Comparable comparable = (Comparable) value;
			return cb.greaterThan(root.get(attributeName), comparable);
		}
		return null;
	}

	/**
	 * 小于
	 * @param attributeName
	 * @param value 比较值
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Predicate lt(String attributeName, Object value) {
		if(value instanceof Comparable) {
			Comparable comparable = (Comparable) value;
			return cb.lessThan(root.get(attributeName), comparable);
		}		
		return null;
	}


	/**
	 * 按位与	    
	 * 二进制位 代表状态 存在某一个
	 * @param attributeName
	 * @param value
	 * @return
	 */
	public Predicate bitExistAny(String attributeName, Object value) {
		if(isBitFunctionValut(value)) {
			Number number = (Number) value;
			Expression<Number> ex = bitAnd(attributeName,number);
			return cb.gt(ex,  cb.literal(0));
		}		
		return null;
	}
	
	
	/**
	 *  使用按位与
	 *  判断 存在   4 8 均存在 可传入4+8
	 * @param attributeName
	 * @param value
	 * @return
	 */
	public Predicate bitExistALL(String attributeName, Object value) {
		if(isBitFunctionValut(value)) {
			Number number = (Number) value;
			Expression<Number> ex = bitAnd(attributeName,number);
			return cb.gt(ex,  cb.literal(0));
		}		
		return null;
	}
	
	/**
	 * 使用 按位与
	 * 判断 4,8 均不存在 可传入4+8
	 * @param attributeName
	 * @param value
	 * @return
	 */
	public Predicate bitNotExistALL(String attributeName, Object value) {
		if(isBitFunctionValut(value)) {
			Number number = (Number) value;
			Expression<Number> ex = bitAnd(attributeName,number);
			return cb.notEqual(ex,  cb.literal(0));
		}	
		return null;
	}
	
	
	/**
	 * 获取按位与 表达式
	 * @param val1 数据库中字段
	 * @param val2 运算值
	 * @return
	 */
	private Expression<Number> bitAnd(String attributeName,Number val2) {
		Expression<Number> ex = cb.function("bitand", Number.class, root.get(attributeName),cb.literal(val2));
		return ex;
	}
	
	/**
	 * 获取按位或 表达式
	 * @param val1 数据库中字段
	 * @param val2 运算值
	 * @return
	 */
	private Expression<Number> bitOr(String attributeName,Number val2) {
		Expression<Number> ex = cb.function("bitor", Number.class, root.get(attributeName),cb.literal(val2));
		return ex;
	}
	
	/**
	 * 判断对象是否可以进行位运算
	 * @param obj
	 * @return
	 */
	private boolean isBitFunctionValut(Object obj) {		
		if(obj instanceof Long ||
		   obj instanceof Integer ||
		   obj instanceof Short ||
		   obj instanceof Byte) {
			return true;
		}
		return false;
	}

	
	
}
