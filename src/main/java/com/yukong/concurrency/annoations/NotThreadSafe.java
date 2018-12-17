package com.yukong.concurrency.annoations;

import java.lang.annotation.*;

/**
 * @author yukong
 * @date 2018/8/29
 * @description 线程安全
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface NotThreadSafe {
    String value() default "";
}
