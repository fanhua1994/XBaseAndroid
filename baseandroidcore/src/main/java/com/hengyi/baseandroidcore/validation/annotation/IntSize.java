package com.hengyi.baseandroidcore.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)  
@Retention(RetentionPolicy.RUNTIME)
public @interface IntSize {
	String message();
	int minvalue() default 0;   //最小长度
    int maxvalue() default 0;   //最大长度
}
