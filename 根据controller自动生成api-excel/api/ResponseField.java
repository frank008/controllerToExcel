package com.yinse.datacenter.serviceutils.documentUtils.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseField {
    //字段名称
    String name() default "";
    //字段描述
    String  description() default "";
    //引用 将字段放入到同一对象里面
    String  referenceName() default "";
    //是否必输
    boolean required() default false;
    //字段类型 数字 字符串 时间
    Class  typeClass()  default  String.class;
}
