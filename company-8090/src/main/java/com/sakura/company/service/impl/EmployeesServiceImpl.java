package com.sakura.company.service.impl;

import com.sakura.company.dao.EmployeesMapper;
import com.sakura.company.model.Employees;
import com.sakura.company.service.EmployeesService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Mr.Zhou
 */
@Service
public class EmployeesServiceImpl implements EmployeesService {

    @Resource
    private EmployeesMapper employeesMapper;

    @Override
    public List<Employees> listEmployees() {
        List<Employees> employees = employeesMapper.selectByRowBounds(new Employees(), new RowBounds(0, 10));
        return employees;
    }
}
