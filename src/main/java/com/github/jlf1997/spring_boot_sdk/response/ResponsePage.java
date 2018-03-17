package com.github.jlf1997.spring_boot_sdk.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.github.jlf1997.spring_boot_sdk.vo_translation.ModeAndViewTranslation;



public class ResponsePage<V> {
	
	public ResponsePage() {
		
	}
	
	


	private boolean hasNext;
	
	private boolean hasPre;
	
	private int pageNow;
	
	private int pageSize;
	
	private long startPos;
	
	private long totalCount;
	
	private int totalPageCount;
	

	
	private List<V> list;

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean isHasPre() {
		return hasPre;
	}

	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getStartPos() {
		return startPos;
	}

	public void setStartPos(long startPos) {
		this.startPos = startPos;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public List<V> getList() {
		return list;
	}

	public void setList(List<V> list) {
		this.list = list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponsePage(ModeAndViewTranslation change,Page pages,Integer pageNow,Class<V> classV) {
		init(pages,pageNow);
		if(classV!=null) {
			if(change==null) {
				change = new ModeAndViewTranslation();
			}
			list =  change.modelListToViewList(pages.getContent(), classV);
		}else {
			list = pages.getContent();
		}
		
		
		
	}
	

	
	@SuppressWarnings({ "rawtypes"})
	public ResponsePage(Page pages,Integer pageNow,Class<V> classV) {
		this((ModeAndViewTranslation)null, pages, pageNow, classV);
		
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponsePage(Page pages,Integer pageNow) {
		this( pages, pageNow,(Class<V>) null);
		
		
	}
	

	
	@SuppressWarnings("rawtypes")
	private void init(Page pages,Integer pageNow) {
		totalCount = pages.getTotalElements();
		pageSize = pages.getSize();		
		totalPageCount = pages.getTotalPages();
		this.pageNow = pageNow;
		hasPre = pages.hasPrevious();
		hasNext = pages.hasNext();
	}
	
	

}
