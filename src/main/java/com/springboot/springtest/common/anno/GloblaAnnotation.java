package com.springboot.springtest.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author tuojinshi
 * @ClassName: GlobalAnnotation
 * @Description： 注解实现根据不同参数调用不同的方法,不同系统调用不同的方法
 * @Date 2019/11/8 10:54
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GloblaAnnotation {
    String SystemCode();
}
