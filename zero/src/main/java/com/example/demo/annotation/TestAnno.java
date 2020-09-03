package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * @author Mr.Zhou
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestAnno {

    String value();
}
