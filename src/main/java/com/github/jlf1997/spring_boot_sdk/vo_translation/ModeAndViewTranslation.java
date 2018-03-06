package com.github.jlf1997.spring_boot_sdk.vo_translation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;



public abstract class ModeAndViewTranslation<M,V>{
	
	

	public abstract  M viewToModel(V view,Class<M> classM) throws InstantiationException, IllegalAccessException;
	
	public abstract  V modelToView(M model,Class<V> classV) throws InstantiationException, IllegalAccessException;
	


	

	
	public  List<M> viewListToModelList(List<V> viewlList,Class<M> classM) throws InstantiationException, IllegalAccessException {
		List<M> list = new ArrayList<>();	
		
		for(V view : viewlList) {		
			list.add(viewToModel(view,classM));
		}
		return list;
	}
	
	public  List<V> modelListToViewList(List<M> modelList,Class<V> classV) throws InstantiationException, IllegalAccessException {
		List<V> list = new ArrayList<>();
		for(M model : modelList) {		
			list.add(modelToView(model,classV));
		}
		return list;
	}
	
	
}
