package com.cloud.mt.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author simon
 * @Description Excel注解集
 * @Date 11:29 2020/8/3
 * @Param
 * @return
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excels {
	Excel[] value();
}