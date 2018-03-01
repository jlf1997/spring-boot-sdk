package com.github.jlf1997.spring_boot_sdk.convert;

import org.springframework.http.converter.HttpMessageConverter;


public interface CustomConvert {

	public HttpMessageConverter<?> setHttpMessageConverter();
}
