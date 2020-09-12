package com.project.annotation;

import java.lang.annotation.*;

/**
 * 利用切面函数来实现返回响应体，主要用于方法上，方法直接返回实体类，，，，返回是Object，
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD})
@Documented
public @interface ResAspectAnno {
}
