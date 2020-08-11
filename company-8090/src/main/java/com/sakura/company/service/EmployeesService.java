package com.sakura.company.service;

import com.sakura.company.model.Employees;

import java.util.List;

/**
 * @author Mr.Zhou
 */
public interface EmployeesService{


    /**
     * 查询十条员工记录
     * @return
     */
    List<Employees> listEmployees();

}
