package com.github.jlf1997.spring_boot_sdk.vo_translation;

public interface Translate<M,V> {
	public   void viewToModel(V view,M model) ;
	
	public   void modelToView(M model,V view) ;
}
