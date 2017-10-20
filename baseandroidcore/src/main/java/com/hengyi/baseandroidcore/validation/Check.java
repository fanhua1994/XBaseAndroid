package com.hengyi.baseandroidcore.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)  
@Retention(RetentionPolicy.RUNTIME)

/**
 * @author 繁华
 */
public @interface Check {
	int method();
    String name();  //变量名
    int minlength() default 0;   //最小长度
    int maxlength() default 0;   //最大长度
    int minvalue() default 0;   //最小值
    int maxvalue() default 0;   //最大值
    int type() default 0;        //属性
    String[] param() default {};  //参数
    String regex() default "";//正则匹配
}