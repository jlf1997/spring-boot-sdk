package com.github.jlf1997.spring_boot_sdk.vo_translation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;



public abstract class ModeAndViewTranslation<M,V> {
	
	

	public abstract  M viewToModel(V view,M model,Class<M> classM) throws InstantiationException, IllegalAccessException;
	
	public abstract  V modelToView(M model,V view,Class<V> classV) throws InstantiationException, IllegalAccessException;
	


	

	
	public  List<M> viewListToModelList(List<V> viewlList,Class<M> classM) throws InstantiationException, IllegalAccessException {
		List<M> list = new ArrayList<>();
		M model ;
		
		for(V view : viewlList) {
			model = BeanUtils.instantiateClass(classM);
			list.add(viewToModel(view,model,classM));
		}
		return list;
	}
	
	public  List<V> modelListToViewList(List<M> modelList,Class<V> classV) throws InstantiationException, IllegalAccessException {
		List<V> list = new ArrayList<>();
		V view ;
		for(M model : modelList) {			
			view = BeanUtils.instantiateClass(classV);
			list.add(modelToView(model,view,classV));
		}
		return list;
	}
	
	
}
