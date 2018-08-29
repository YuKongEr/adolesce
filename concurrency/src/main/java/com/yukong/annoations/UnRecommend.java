package com.yukong.annoations;

import java.lang.annotation.*;

/**
 * @author yukong
 * @date 2018/8/29
 * @description  不推荐写法
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface UnRecommend {
    String value() default "";
}
