package com.example.demo.temp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Mr.Zhou
 */
@RestController
public class TempController {

    @Resource(name = "my")
    private Injetction injetction;

    @GetMapping("/test")
    public String test(){
        return injetction.getName();
    }
}
