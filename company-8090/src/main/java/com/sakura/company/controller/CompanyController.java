package com.sakura.company.controller;

import com.sakura.company.model.Employees;
import com.sakura.company.service.EmployeesService;
import com.sakura.company.service.impl.EmployeesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mr.Zhou
 */
@RestController
public class CompanyController {

    @Autowired
    private EmployeesService employeesService;

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        return "Hello Nacos Discovery " + string;
    }

    @GetMapping("/employees")
    public List<Employees> employeesList(){
        List<Employees> employees=employeesService.listEmployees();
        return employees;
    }

}
