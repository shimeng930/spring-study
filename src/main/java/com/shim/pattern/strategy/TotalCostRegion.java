package com.shim.pattern.strategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 金额区间注解
 * Created by xn064961 on 2017/2/15.
 */
// 给类添加注解
@Target(ElementType.TYPE)
// 注解保留在运行时
@Retention(RetentionPolicy.RUNTIME)
public @interface TotalCostRegion {

    int max() default Integer.MAX_VALUE;
    int min() default Integer.MIN_VALUE;
}
