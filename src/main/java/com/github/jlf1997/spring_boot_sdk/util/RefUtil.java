package com.github.jlf1997.spring_boot_sdk.util;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;



public class RefUtil {

	
	public static Method getWriteMethod(PropertyDescriptor property) {
		Method writeMethod = property.getWriteMethod();
		if (writeMethod != null) {					
			if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
				writeMethod.setAccessible(true);
			}								
		}
		return writeMethod;
	}
	
	public static Method getReadMethod(PropertyDescriptor property) {
		Method readMethod = property.getReadMethod();
		if (readMethod != null) {					
			if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
				readMethod.setAccessible(true);
			}								
		}
		return readMethod;
	} 
	
	public  static <T extends Annotation> T  getAnnotation(Field field,Class<T> annotationClass) {
		boolean fieldHasAnno = field.isAnnotationPresent(annotationClass);  
		 if(fieldHasAnno){  
			 T fieldAnno = field.getAnnotation(annotationClass);
			 return fieldAnno;
		 }
		 return null;
		
		
	}
	
	
	
	
}
