package com.github.jlf1997.spring_boot_sdk.convert.gson;

import java.lang.reflect.Type;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import com.github.jlf1997.spring_boot_sdk.convert.CustomConvert;
import com.github.jlf1997.tools.gson.annotation.GsonExclueDeserialize;
import com.github.jlf1997.tools.gson.annotation.GsonExclueSerialize;
import com.github.jlf1997.tools.gson.builder.GsonDateBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import springfox.documentation.spring.web.json.Json;

public class DefaultGsonConvert implements CustomConvert{

	
	protected GsonBuilder getDefaultGsonBuilder() {
		GsonBuilder build = GsonDateBuilder.getBuilder(null);
		 build.registerTypeAdapter(Json.class, new JsonSerializer<Json>() {
	 			
				@Override
				public JsonElement serialize(Json src, Type typeOfSrc, JsonSerializationContext context) {
					JsonParser jsonParser = new JsonParser();										
					return jsonParser.parse(src.value());
				}			
			});
		 
		 build
			.addSerializationExclusionStrategy(new GsonExclueSerialize())
			.addDeserializationExclusionStrategy(new GsonExclueDeserialize());
		 return build;
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	public HttpMessageConverter setHttpMessageConverter() {
		
		GsonBuilder build = getDefaultGsonBuilder();
		Gson gson =  build.create();
		
	   
	
	    GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
	    gsonConverter.setGson(gson);
		return gsonConverter;
	}

}
