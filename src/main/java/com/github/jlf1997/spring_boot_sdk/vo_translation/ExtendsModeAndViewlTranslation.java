package com.github.jlf1997.spring_boot_sdk.vo_translation;

import org.springframework.beans.BeanUtils;


/**
 * 默认vo po转换类 ，view继承自model
 * @author Administrator
 *
 * @param <M>
 * @param <V>
 */
public class ExtendsModeAndViewlTranslation<M,V extends M> extends ModeAndViewTranslation<M,V>{


	private String[] ignoreProperties;
	
	public ExtendsModeAndViewlTranslation() {
		
	}
	
	public ExtendsModeAndViewlTranslation(String... ignoreProperties ) {
		this.ignoreProperties = ignoreProperties;
	}

	@Override
	public M viewToModel(V view, M model, Class<M> classM) throws InstantiationException, IllegalAccessException {
		if(model==null) {			
			model = BeanUtils.instantiateClass(classM);			
		}
		BeanUtils.copyProperties(view, model, ignoreProperties);		
		return model;
	}

	@Override
	public V modelToView(M model, V view, Class<V> classV) throws InstantiationException, IllegalAccessException {
		if(view==null) {			
			view = BeanUtils.instantiateClass(classV);			
		}
		BeanUtils.copyProperties(model, view,ignoreProperties);
		return view;
	}

	

}
