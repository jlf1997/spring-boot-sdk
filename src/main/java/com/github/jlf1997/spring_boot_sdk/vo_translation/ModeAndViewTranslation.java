package com.github.jlf1997.spring_boot_sdk.vo_translation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;



public  class ModeAndViewTranslation<M,V> {
	
	private Translate<M,V> translate;
	
	public ModeAndViewTranslation() {
		this.translate = null;
	}
	
	public ModeAndViewTranslation(Translate<M,V> translate) {
		this.translate = translate;
	}
	
	public void setTranslate(Translate<M,V> translate) {
		this.translate = translate;
	}
	
	public  List<M> viewListToModelList(List<V> viewlList,Class<M> classM)  {
		List<M> list = new ArrayList<>();	
		
		for(V view : viewlList) {	
			list.add(viewToModel(view,classM));
		}
		return list;
	}
	
	public M viewToModel(V view, Class<M> classM) {	
		
		M model = BeanUtils.instantiateClass(classM);			
		BeanUtils.copyProperties(view, model);
		if(translate!=null) {
			translate.viewToModel(view, model);
		}
		return model;
	}

	public  List<V> modelListToViewList(List<M> modelList,Class<V> classV)  {
		List<V> list = new ArrayList<>();
		for(M model : modelList) {		
			list.add(modelToView(model,classV));
		}
		return list;
	}

	public V modelToView(M model, Class<V> classV) {
		V view = BeanUtils.instantiateClass(classV);		
		BeanUtils.copyProperties(model, view);
		if(translate!=null) {
			translate.modelToView(model,view);
		}
		return view;
		
	}
	
	
	
	
}
