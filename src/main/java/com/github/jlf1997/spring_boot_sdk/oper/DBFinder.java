package com.github.jlf1997.spring_boot_sdk.oper;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;




/**
 * 配置字段查询条件
 * @author yangxiang
 * 
 */
@Documented  
@Inherited  
@Retention(RUNTIME)
@Target(FIELD)
public @interface DBFinder {
	public boolean added() default true;
	public Oper opType() default  Oper.EQ;	
}
