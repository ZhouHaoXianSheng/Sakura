package com.sakura.company.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.sakura.company.dao.DepartmentsMapper;
import com.sakura.company.service.DepartmentsService;
@Service
public class DepartmentsServiceImpl implements DepartmentsService{

    @Resource
    private DepartmentsMapper departmentsMapper;

}
