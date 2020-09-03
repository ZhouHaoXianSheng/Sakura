package com.sakura.company.service;

import com.sakura.company.model.Employees;

import java.util.List;

/**
 * @author Mr.Zhou
 */
public interface EmployeesService{


    /**
     * 查询十条员工记录
     * @param key
     * @return
     */
    List<Employees> listEmployees(String key);

    /**
     * List Ops
     * @return
     */
    List<Employees> employeesListOps();

    /**
     * 写入hash测试
     * @param key
     * @param employees
     */
    void writeHash(String key, Employees employees);

    /**
     * 读取hash测试
     * @param key
     * @return
     */
    Employees loadHash(String key);

    void hyperloglog();
}
