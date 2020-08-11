package com.sakura.consumer.controller;

import com.sakura.consumer.feign.CompanyFeign;
import com.sakura.consumer.vo.EmployeesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Mr.Zhou
 */
@RestController
public class ConsumerController {

    @Autowired
    CompanyFeign companyFeign;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/consumer/employees")
    public List<EmployeesVo> list(){
        return companyFeign.employeesList();
    }

    @PostMapping("redis/post/{value}")
    public void postRedis(@PathVariable(name = "value") String value){
        redisTemplate.opsForValue().set("test", value);
    }

    @PostMapping("redis/get/{value}")
    public String getRedis(@PathVariable(name = "value") String value){
        String s = redisTemplate.opsForValue().get("test");
        return s;
    }


}
