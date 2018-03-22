package com.github.jlf1997.spring_boot_sdk.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.github.jlf1997.spring_boot_sdk.vo_translation.ModeAndViewTranslation;



public class ResponseObject<V> {

	private static final String OK = "ok";
	private static final String ERROR = "error";

	private boolean success ;
	
	private String msg;
	

	private V data; // 响应内容
	
	//分页对象

	private ResponsePage<V> page;
	
	
	private List<V> list;

	public ResponseObject() {
		super();
		this.success= false;
		this.msg = "";
	}
	
	

	public ResponseObject<V> success() {
		this.success = true;
		this.msg = OK;
		return this;
	}

	public ResponseObject<V> success(V data) {
		this.success = true;
		this.data = data;
		if(data instanceof String) {
			this.msg = data.toString();
		}
		return this;
	}
	
	public ResponseObject<V> success(ResponsePage<V> page) {
		this.success = true;
		this.page = page;
		return this;
	}
	
	public ResponseObject<V> success(List<V> list) {
		this.success = true;
		this.list = list;
		return this;
	}
			

	public ResponseObject<V> failure() {
		this.success = false;
		this.msg = ERROR;
		return this;
	}

	public ResponseObject<V> failure(String msg) {
		this.success = false;
		this.msg = msg;
		return this;
	}
	
	public ResponseObject<V> failure(String msg,V data) {
		this.success = false;
		this.msg = msg;
		this.data = data;
		return this;
	}
	
	

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public Object getData() {
		return data;
	}

	public ResponsePage<V> getPage() {
		return page;
	}

	public void setPage(ResponsePage<V> page) {
		this.page = page;
	}

	public List<V> getList() {
		return list;
	}

	public void setList(List<V> list) {
		this.list = list;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setData(V data) {
		this.data = data;
	}

	
}
