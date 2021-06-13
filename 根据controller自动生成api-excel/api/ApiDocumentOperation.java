package com.yinse.datacenter.serviceutils.documentUtils.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiDocumentOperation {
    //接口名称
    String apiName() default "";

}
