package com.github.jlf1997.spring_boot_sdk.convert;

import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.lang.annotation.*;

/**
 * @author yx
 * 
 * 是否使用gson作为解析器
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableJpaAuditing
@Import({HttpConvertConfiguration.class})
public @interface EnableHttpConvert {


}
