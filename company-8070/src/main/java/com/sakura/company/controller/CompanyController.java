package com.sakura.company.controller;

import com.sakura.company.model.Employees;
import com.sakura.company.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mr.Zhou
 */
@RestController
public class CompanyController {

    @Autowired
    private EmployeesService employeesService;


    @GetMapping("/list/employees")
    public List<Employees> employeesListOps() {
        List<Employees> aLong = employeesService.employeesListOps();
        return aLong;
    }

    @GetMapping("/hash/employees/{key}")
    public Employees mapping(@PathVariable(name = "key") String key) {
        Employees employees = new Employees();
        employees.setEmpNo(1);
        employees.setFirstName("zhou");
        employees.setLastName("hao");
        employeesService.writeHash(key,employees);
        return employeesService.loadHash(key);
    }

    @GetMapping("/cacheAble/employees/{key}")
    public List<Employees> cacheAble(@PathVariable(name = "key") String key) {
        List<Employees> employees = employeesService.listEmployees(key);
        return employees;
    }

    @GetMapping("/hyperloglog")
    public void hyperloglog() {
        employeesService.hyperloglog();
    }
}
