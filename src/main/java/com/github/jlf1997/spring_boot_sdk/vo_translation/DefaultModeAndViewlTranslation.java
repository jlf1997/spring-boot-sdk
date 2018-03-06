package com.github.jlf1997.spring_boot_sdk.vo_translation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;


/**
 * 默认vo po转换类
 * @author Administrator
 *
 * @param <M>
 * @param <V>
 */
public  class DefaultModeAndViewlTranslation<M,V> extends ModeAndViewTranslation<M,V> {

	
	
	private List<String> ignoreProperties;
	
	public DefaultModeAndViewlTranslation() {
		
	}
	
	
	
	public DefaultModeAndViewlTranslation(String... ignoreProperties ) {
		this.ignoreProperties = Arrays.asList(ignoreProperties);
	}

	@Override
	 public M viewToModel(V view, Class<M> classM) throws InstantiationException, IllegalAccessException {
				
			M model = BeanUtils.instantiateClass(classM);			
			
		if(ignoreProperties==null) {
			ignoreProperties = new ArrayList<>();
		}
		
		BeanUtils.copyProperties(view, model, ignoreProperties.toArray(new String[ignoreProperties.size()]));
		return model;
		
		
		
	}

	@Override
	 public V modelToView(M model, Class<V> classV) throws InstantiationException, IllegalAccessException {
					
		V view = BeanUtils.instantiateClass(classV);			
		
		if(ignoreProperties==null) {
			ignoreProperties = new ArrayList<>();
		}
		BeanUtils.copyProperties(model, view, ignoreProperties.toArray(new String[ignoreProperties.size()]));
		
		return view;
	}

	

}
