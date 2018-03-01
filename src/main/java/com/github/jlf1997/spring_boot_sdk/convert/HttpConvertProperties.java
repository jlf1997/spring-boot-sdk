package com.github.jlf1997.spring_boot_sdk.convert;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("convert.gson")
public class HttpConvertProperties {

	  /**
	   * 配置路径
	   */
	  private String path = "org.springboot.sdk.jpa.convert.gson.DefaultGsonConvert";
	  
	  
	  
}
