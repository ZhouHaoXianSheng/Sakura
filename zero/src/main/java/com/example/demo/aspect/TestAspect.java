package com.example.demo.aspect;

import com.example.demo.annotation.TestAnno;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Zhou
 */
@Aspect
public class TestAspect {

    @SneakyThrows
    @Around("@annotation(testAnno)")
    public void doMyWork(ProceedingJoinPoint point, TestAnno testAnno){
        System.out.println("Before: "+testAnno.value());
        point.proceed();
        System.out.println("After: "+testAnno.value());
    }
}
