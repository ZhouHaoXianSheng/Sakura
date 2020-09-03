package com.example.demo.temp;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Zhou
 */
@Component
public class InjectBean {

    @Bean(name = "my")
//    @Scope("prototype")
    public Injetction prototypeInstance(InjectionPoint injectionPoint) {
        return new Injetction("prototypeInstance for " + injectionPoint.getMember());
    }
}
