package com.example.demo.temp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class protopeTest {
    private String name;
}
