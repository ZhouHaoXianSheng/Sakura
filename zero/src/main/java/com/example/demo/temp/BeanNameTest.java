package com.example.demo.temp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Zhou
 */
@Component
public class BeanNameTest implements BeanNameAware {

    private String beanName;

    @Override
    public void setBeanName(String name) {
        this.beanName=name;
    }

}
